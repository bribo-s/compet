package ui;

import model.Event;
import model.EventLog;
import model.FocusSession;
import model.Interaction;
import model.Pet;
import model.PetManager;
import model.SessionLog;
import persistence.AppState;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Represents main graphical user interface for Compet productivity companion app.
// Handles pet management, focus sessions with timer, interactions, and data persistence. 
@ExcludeFromJacocoGeneratedReport
public class CompetGUI extends JFrame {
    private static final String JSON_STORE = "./data/competapp.json";
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final int TIMER_DELAY = 1000;

    private PetManager petManager;
    private SessionLog sessionLog;
    private Interaction interaction;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JLabel petImageLabel;
    private JLabel roomImageLabel;
    private JLabel petNameLabel;
    private JLabel fondnessLabel;
    private JLabel timerLabel;
    private JLabel statsLabel;
    private JTable sessionTable;
    private DefaultTableModel sessionTableModel;
    private JPanel petDisplayPanel;

    private Timer countdownTimer;
    private int remainingSeconds;
    private boolean sessionRunning;

    // EFFECTS: constructs Compet GUI window, initializes all components,
    // prompts to load saved data, and makes window visible.
    public CompetGUI() {
        super("compet - productivity companion");
        initComponents();
        setupLayout();
        setupWindowListener();
        setVisible(true);
        offerLoadOnStart();
    }

    // MODIFIES: this
    // EFFECTS: initializes core application components and models.
    private void initComponents() {
        petManager = new PetManager();
        sessionLog = new SessionLog();
        interaction = new Interaction();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        sessionRunning = false;

        String[] columns = { "Duration (min)", "Pet", "Interaction", "Fondness Gained" };
        sessionTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        sessionTable = new JTable(sessionTableModel);
    }

    // MODIFIES: this
    // EFFECTS: sets up main window layout with all panels.
    private void setupLayout() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        add(buildTopBar(), BorderLayout.NORTH);
        add(buildCenterPanel(), BorderLayout.CENTER);
        add(buildBottomBar(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: registers window closing listener to prompt save before exit.
    private void setupWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClose();
            }
        });
    }

    // EFFECTS: builds and returns top header bar panel.
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(255, 228, 225));
        bar.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel title = new JLabel("compet🐾");
        title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 28));
        title.setForeground(new Color(206, 96, 118));

        JLabel sub = new JLabel("your cozy productivity companion");
        sub.setFont(new Font("Arial", Font.ITALIC, 13));
        sub.setForeground(new Color(160, 100, 110));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(12));
        titlePanel.add(sub);
        bar.add(titlePanel, BorderLayout.WEST);

        JPanel saveLoad = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        saveLoad.setOpaque(false);
        saveLoad.add(makeButton("Save", new Color(130, 180, 130), e -> saveState()));
        saveLoad.add(makeButton("Load", new Color(130, 160, 200), e -> loadState()));
        bar.add(saveLoad, BorderLayout.EAST);

        return bar;
    }

    // EFFECTS: builds and returns center split panel with pet view and session log.
    private JSplitPane buildCenterPanel() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildPetPanel(), buildRightPanel());
        split.setDividerLocation(460);
        split.setResizeWeight(0.5);
        split.setBorder(null);
        split.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, e -> {
            if (petDisplayPanel != null) {
                petDisplayPanel.revalidate();
                petDisplayPanel.repaint();
            }
        });
        return split;
    }

    // EFFECTS: builds and returns left pet display and control panel.
    private JPanel buildPetPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBorder(new EmptyBorder(0, 0, 0, 5));

        petDisplayPanel = buildPetDisplayPanel();
        panel.add(petDisplayPanel, BorderLayout.CENTER);
        panel.add(buildPetInfoPanel(), BorderLayout.SOUTH);

        return panel;
    }

    // EFFECTS: builds and returns visual pet + room display panel.
    private JPanel buildPetDisplayPanel() {
        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRoomBackground(g);
                if (roomImageLabel.getIcon() != null) {
                    Image img = ((ImageIcon) roomImageLabel.getIcon()).getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                }
                if (petImageLabel.getIcon() != null) {
                    int petWidth = petImageLabel.getIcon().getIconWidth();
                    int petHeight = petImageLabel.getIcon().getIconHeight();
                    int x = (getWidth() - petWidth) / 2;
                    int y = getHeight() - petHeight - 10;
                    petImageLabel.getIcon().paintIcon(this, g, x, y);
                }
            }
        };
        panel.setPreferredSize(new Dimension(440, 300));
        panel.setBackground(new Color(245, 240, 255));

        roomImageLabel = new JLabel();
        petImageLabel = new JLabel();

        return panel;
    }

    // MODIFIES: g
    // EFFECTS: draws fallback gradient background on pet display panel.
    private void drawRoomBackground(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(0, 0, new Color(255, 230, 240),
                0, getHeight(), new Color(220, 210, 255));
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    // EFFECTS: builds and returns pet info labels, fondness, and action buttons
    // panel.
    private JPanel buildPetInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setBackground(new Color(255, 248, 250));
        panel.setBorder(new EmptyBorder(8, 10, 8, 10));

        petNameLabel = new JLabel("No pet selected");
        petNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        petNameLabel.setForeground(new Color(140, 60, 80));

        fondnessLabel = new JLabel("Fondness: -");
        fondnessLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        fondnessLabel.setForeground(new Color(180, 100, 120));

        JPanel labels = new JPanel(new GridLayout(2, 1, 0, 2));
        labels.setOpaque(false);
        labels.add(petNameLabel);
        labels.add(fondnessLabel);

        JPanel buttons = buildPetActionButtons();

        panel.add(labels, BorderLayout.NORTH);
        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    // EFFECTS: builds and returns panel with pet action buttons.
    private JPanel buildPetActionButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        panel.setOpaque(false);
        panel.add(makeButton("+ New Pet", new Color(200, 140, 170), e -> showCreatePetDialog()));
        panel.add(makeButton("Switch Pet", new Color(170, 150, 200), e -> showSwitchPetDialog()));
        panel.add(makeButton("View All Pets", new Color(150, 190, 200), e -> showAllPetsDialog()));
        return panel;
    }

    // EFFECTS: builds and returns right panel with timer and session log.
    private JPanel buildRightPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBorder(new EmptyBorder(0, 5, 0, 0));
        panel.add(buildTimerPanel(), BorderLayout.NORTH);
        panel.add(buildSessionLogPanel(), BorderLayout.CENTER);
        return panel;
    }

    // EFFECTS: builds and returns focus session timer panel.
    private JPanel buildTimerPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0), 1, true),
                new EmptyBorder(12, 14, 12, 14)));

        JLabel timerTitle = new JLabel("Focus Timer");
        timerTitle.setFont(new Font("Arial", Font.BOLD, 16));
        timerTitle.setForeground(new Color(0, 0, 0));

        timerLabel = new JLabel("00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 52));
        timerLabel.setForeground(new Color(0, 0, 0));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel timerControls = buildTimerControls();

        panel.add(timerTitle, BorderLayout.NORTH);
        panel.add(timerLabel, BorderLayout.CENTER);
        panel.add(timerControls, BorderLayout.SOUTH);

        return panel;
    }

    // EFFECTS: builds and returns timer control input and buttons panel.
    private JPanel buildTimerControls() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        panel.setOpaque(false);

        JTextField durationField = new JTextField("25", 5);
        durationField.setFont(new Font("Courier New", Font.PLAIN, 14));
        JLabel minLabel = new JLabel("min");
        minLabel.setFont(new Font("Arial", Font.ITALIC, 13));

        JButton startBtn = makeButton("Start Session", new Color(100, 180, 130),
                e -> startSession(durationField));
        JButton stopBtn = makeButton("Stop", new Color(220, 120, 100),
                e -> stopSession());

        loadTimerAsset(panel);

        panel.add(durationField);
        panel.add(minLabel);
        panel.add(startBtn);
        panel.add(stopBtn);
        return panel;
    }

    // MODIFIES: panel
    // EFFECTS: loads timer image asset if available and adds to timer controls
    // panel.
    private void loadTimerAsset(JPanel panel) {
        ImageIcon timerIcon = loadScaledIcon("./data/timer.png", 36, 36);
        if (timerIcon == null) {
            timerIcon = loadScaledIcon("./data/timer.gif", 36, 36);
        }
        if (timerIcon != null) {
            JLabel timerImg = new JLabel(timerIcon);
            panel.add(timerImg);
        }
    }

    // EFFECTS: builds and returns session history log panel with table.
    private JPanel buildSessionLogPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setBackground(new Color(252, 248, 255));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 180, 220), 1, true),
                new EmptyBorder(10, 12, 10, 12)));

        JLabel logTitle = new JLabel("Session History");
        logTitle.setFont(new Font("Arial", Font.BOLD, 15));
        logTitle.setForeground(new Color(110, 70, 130));

        styleSessionTable();
        JScrollPane scroll = new JScrollPane(sessionTable);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(210, 190, 230), 1));

        JLabel statsLabel = buildStatsLabel();

        panel.add(logTitle, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(statsLabel, BorderLayout.SOUTH);

        return panel;
    }

    // MODIFIES: sessionTable
    // EFFECTS: applies visual styling to session history table.
    private void styleSessionTable() {
        sessionTable.setFont(new Font("Arial", Font.PLAIN, 13));
        sessionTable.setRowHeight(24);
        sessionTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        sessionTable.getTableHeader().setBackground(new Color(220, 200, 240));
        sessionTable.setSelectionBackground(new Color(240, 220, 255));
        sessionTable.setGridColor(new Color(220, 210, 235));
    }

    // EFFECTS: builds and returns stats summary label below session table.
    private JLabel buildStatsLabel() {
        statsLabel = new JLabel("Total sessions: 0 | Total time: 0 min");
        statsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statsLabel.setForeground(new Color(130, 100, 150));
        return statsLabel;
    }

    // EFFECTS: builds and returns bottom status bar panel.
    private JPanel buildBottomBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(245, 240, 250));
        bar.setBorder(new EmptyBorder(5, 10, 3, 10));

        JLabel status = new JLabel("Welcome to compet! Create a pet to get started.");
        status.setFont(new Font("Arial", Font.ITALIC, 12));
        status.setForeground(new Color(140, 110, 150));
        status.setName("statusLabel");
        bar.add(status, BorderLayout.WEST);
        return bar;
    }

    // REQUIRES: durationField contains valid integer string > 0.
    // MODIFIES: this
    // EFFECTS: starts focus session countdown timer with specified duration.
    private void startSession(JTextField durationField) {
        if (petManager.getCurrentPet() == null) {
            showMessage("Please create or select a pet first!", "No Pet", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (sessionRunning) {
            showMessage("A session is already running!", "Session Active", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            int minutes = Integer.parseInt(durationField.getText().trim());
            if (minutes <= 0) {
                throw new NumberFormatException();
            }
            beginCountdown(minutes);
        } catch (NumberFormatException ex) {
            showMessage("Please enter a valid duration (positive number).", "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // REQUIRES: minutes > 0.
    // MODIFIES: this
    // EFFECTS: initializes and starts countdown timer for given minutes.
    private void beginCountdown(int minutes) {
        remainingSeconds = minutes * 60;
        sessionRunning = true;
        updateTimerDisplay();

        countdownTimer = new Timer(TIMER_DELAY, e -> tickTimer(minutes));
        countdownTimer.start();
        setStatus("Focus session started for " + minutes + " minutes. Stay focused!");
    }

    // REQUIRES: minutes > 0.
    // MODIFIES: this
    // EFFECTS: decrements timer each tick (completes session when time runs out).
    private void tickTimer(int minutes) {
        remainingSeconds--;
        updateTimerDisplay();
        if (remainingSeconds <= 0) {
            countdownTimer.stop();
            sessionRunning = false;
            completeSession(minutes);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates timer display label with current remaining time.
    private void updateTimerDisplay() {
        int mins = remainingSeconds / 60;
        int secs = remainingSeconds % 60;
        timerLabel.setText(String.format("%02d:%02d", mins, secs));
        Color col = remainingSeconds <= 60 ? new Color(200, 60, 60) : new Color(60, 80, 150);
        timerLabel.setForeground(col);
    }

    // REQUIRES: minutes > 0.
    // MODIFIES: this
    // EFFECTS: completes session, records it, updates pet fondness, shows
    // interaction.
    private void completeSession(int minutes) {
        Pet currentPet = petManager.getCurrentPet();
        String interactionType = interaction.getRandomInteraction();
        FocusSession session = new FocusSession(minutes, currentPet.getName(), interactionType);
        sessionLog.addSession(session);
        currentPet.increaseFondness(session.getFondnessGained());

        addSessionToTable(session);
        updatePetDisplay();
        updateSessionStats();
        timerLabel.setText("00:00");
        timerLabel.setForeground(new Color(0, 0, 0));

        showInteractionDialog(currentPet, interactionType, minutes);
        setStatus("Session complete! " + currentPet.getName() + " is happy!");
    }

    // MODIFIES: this
    // EFFECTS: stops currently running session timer without recording.
    private void stopSession() {
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }
        sessionRunning = false;
        timerLabel.setText("00:00");
        timerLabel.setForeground(new Color(0, 0, 0));
        setStatus("Session stopped.");
    }

    // REQUIRES: session is not null.
    // MODIFIES: this
    // EFFECTS: adds completed session row to session history table.
    private void addSessionToTable(FocusSession session) {
        sessionTableModel.addRow(new Object[] {
                session.getDurationMinutes(),
                session.getPetName(),
                session.getInteractionType(),
                session.getFondnessGained()
        });
    }

    // MODIFIES: this
    // EFFECTS: shows dialog to create new pet with name, type, and room inputs.
    private void showCreatePetDialog() {
        JTextField nameField = new JTextField(12);
        JComboBox<String> typeBox = new JComboBox<>(new String[] { "Cat", "Dog", "Bird", "Bunny" });
        String[] rooms = { "Modern Living Room", "Sunset Living Room", "Starry Bedroom", "Lush Bedroom" };
        JComboBox<String> roomBox = new JComboBox<>(rooms);

        JPanel form = buildCreatePetForm(nameField, typeBox, roomBox);

        int result = JOptionPane.showConfirmDialog(this, form,
                "Create New Pet", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                createPet(name, (String) typeBox.getSelectedItem(), (String) roomBox.getSelectedItem());
            }
        }
    }

    // REQUIRES: nameField, typeBox, roomBox are not null.
    // EFFECTS: builds and returns form panel for pet creation dialog.
    private JPanel buildCreatePetForm(JTextField nameField, JComboBox<String> typeBox,
            JComboBox<String> roomBox) {
        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        form.add(new JLabel("Pet Name:"));
        form.add(nameField);
        form.add(new JLabel("Type:"));
        form.add(typeBox);
        form.add(new JLabel("Room Theme:"));
        form.add(roomBox);
        return form;
    }

    // REQUIRES: name, type, room are not empty strings.
    // MODIFIES: this
    // EFFECTS: creates new pet and adds it to pet manager, updating display.
    private void createPet(String name, String type, String room) {
        Pet newPet = new Pet(name, type, room);
        petManager.addPet(newPet);
        updatePetDisplay();
        setStatus(name + " the " + type + " joined your team in their " + room + "!");
    }

    // MODIFIES: this
    // EFFECTS: shows dialog to switch to different existing pet.
    private void showSwitchPetDialog() {
        if (petManager.getPetCount() == 0) {
            showMessage("No pets to switch to!", "No Pets", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String[] names = petManager.getAllPets().stream()
                .map(Pet::getName).toArray(String[]::new);
        String chosen = (String) JOptionPane.showInputDialog(this,
                "Choose a pet:", "Switch Pet",
                JOptionPane.PLAIN_MESSAGE, null, names, names[0]);
        if (chosen != null) {
            petManager.switchPet(chosen);
            updatePetDisplay();
            setStatus("Switched to " + chosen + "!");
        }
    }

    // EFFECTS: shows dialog listing all pets and their fondness/room.
    private void showAllPetsDialog() {
        if (petManager.getPetCount() == 0) {
            showMessage("You have no pets yet!", "No Pets", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String[] cols = { "Name", "Type", "Room", "Fondness" };
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        for (Pet p : petManager.getAllPets()) {
            model.addRow(new Object[] { p.getName(), p.getType(), p.getRoom(), p.getFondnessLevel() });
        }
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(22);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(420, 160));
        JOptionPane.showMessageDialog(this, scroll, "All Pets", JOptionPane.PLAIN_MESSAGE);
    }

    // REQUIRES: currentPet and interactionType are not null, minutes > 0.
    // EFFECTS: shows dialog with interaction (image/gif) and description after
    // session.
    private void showInteractionDialog(Pet currentPet, String interactionType, int minutes) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(12, 16, 12, 16));

        ImageIcon icon = loadInteractionGif(interactionType);
        if (icon != null) {
            JLabel imgLabel = new JLabel(icon);
            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(imgLabel, BorderLayout.NORTH);
        }

        String msg = buildInteractionMessage(currentPet.getName(), interactionType, minutes);
        JLabel msgLabel = new JLabel("<html><center>" + msg + "</center></html>");
        msgLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        msgLabel.setForeground(new Color(120, 60, 90));
        panel.add(msgLabel, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel,
                "Session Complete! ✨", JOptionPane.PLAIN_MESSAGE);
    }

    // REQUIRES: petName and interactionType are not empty.
    // EFFECTS: returns descriptive interaction message string for given pet and
    // type.
    private String buildInteractionMessage(String petName, String interactionType, int minutes) {
        String base = "Great work! You focused for " + minutes + " minutes.<br><br>";
        if (interactionType.equals("petting")) {
            return base + "You gently pet " + petName + ".<br>They purr contentedly! 🐾";
        } else if (interactionType.equals("feeding")) {
            return base + "You feed " + petName + " a tasty snack!<br>Nom nom! 🍖";
        } else if (interactionType.equals("playing")) {
            return base + "You play with " + petName + "!<br>So much fun! 🎾";
        } else {
            return base + "You give " + petName + " a nice bath!<br>Sparkling clean! ✨";
        }
    }

    // REQUIRES: interactionType is not empty.
    // EFFECTS: loads and returns scaled gif/image icon for given interaction type;
    // returns null if no asset found.
    private ImageIcon loadInteractionGif(String interactionType) {
        ImageIcon icon = loadScaledGifIcon("./data/" + interactionType + ".gif", 280, 280);
        if (icon == null) {
            icon = loadScaledIcon("./data/" + interactionType + ".png", 280, 280);
        }
        return icon;
    }

    // MODIFIES: this
    // EFFECTS: updates pet display panel with current pet image, room image, and
    // info labels.
    private void updatePetDisplay() {
        Pet current = petManager.getCurrentPet();
        if (current == null) {
            petNameLabel.setText("No pet selected");
            fondnessLabel.setText("Fondness: -");
            petImageLabel.setIcon(null);
            roomImageLabel.setIcon(null);
            return;
        }
        petNameLabel.setText(current.getName() + " the " + current.getType());
        fondnessLabel.setText("Fondness: " + current.getFondnessLevel()
                + " | Room: " + current.getRoom());
        loadPetAsset(current);
        loadRoomAsset(current);
        petDisplayPanel.repaint();
    }

    // REQUIRES: path is not null, width > 0, height > 0.
    // EFFECTS: loads scaled GIF by URL to preserve animation (returns null if
    // not found).
    private ImageIcon loadScaledGifIcon(String path, int width, int height) {
        java.io.File file = new java.io.File(path);
        if (!file.exists()) {
            return null;
        }
        try {
            ImageIcon raw = new ImageIcon(file.toURI().toURL());
            Image scaled = raw.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
            return new ImageIcon(scaled);
        } catch (Exception ex) {
            return null;
        }
    }

    // REQUIRES: current is not null.
    // MODIFIES: this
    // EFFECTS: loads pet image asset for given pet type, tries gif then png.
    private void loadPetAsset(Pet current) {
        String typeLower = current.getType().toLowerCase();
        ImageIcon icon = loadScaledGifIcon("./data/" + typeLower + ".gif", 220, 220);
        if (icon == null) {
            icon = loadScaledIcon("./data/" + typeLower + ".png", 220, 220);
        }
        petImageLabel.setIcon(icon);
        petDisplayPanel.repaint();
    }

    // REQUIRES: current is not null.
    // MODIFIES: this
    // EFFECTS: loads room background image for current pet's room theme.
    private void loadRoomAsset(Pet current) {
        String roomKey = current.getRoom().toLowerCase().replace(" ", "_");
        ImageIcon roomIcon = loadScaledIcon("./data/" + roomKey + ".png", 440, 300);
        if (roomIcon == null) {
            roomIcon = loadScaledIcon("./data/" + roomKey + ".gif", 440, 300);
        }
        roomImageLabel.setIcon(roomIcon);
        petDisplayPanel.repaint();
    }

    // REQUIRES: width > 0 and height > 0.
    // EFFECTS: loads image from path, scales to given dimensions, and returns as
    // ImageIcon;
    // returns null if image file does not exist at path.
    private ImageIcon loadScaledIcon(String path, int width, int height) {
        java.io.File file = new java.io.File(path);
        if (!file.exists()) {
            return null;
        }
        ImageIcon raw = new ImageIcon(file.getAbsolutePath());
        Image scaled = raw.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    // MODIFIES: this
    // EFFECTS: updates session statistics summary label in session log panel.
    private void updateSessionStats() {
        int count = sessionLog.getSessionCount();
        int total = sessionLog.getTotalDuration();
        statsLabel.setText("Total sessions: " + count + " | Total time: " + total + " min");
    }

    // MODIFIES: this
    // EFFECTS: saves current application state to JSON file.
    private void saveState() {
        try {
            jsonWriter.open();
            jsonWriter.write(petManager, sessionLog);
            jsonWriter.close();
            showMessage("State saved to " + JSON_STORE, "Saved!", JOptionPane.INFORMATION_MESSAGE);
            setStatus("Application state saved.");
        } catch (FileNotFoundException ex) {
            showMessage("Unable to save to: " + JSON_STORE, "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads application state from JSON file and updates display.
    private void loadState() {
        try {
            AppState state = jsonReader.read();
            petManager = state.getPetManager();
            sessionLog = state.getSessionLog();
            refreshSessionTable();
            updatePetDisplay();
            updateSessionStats();
            setStatus("Loaded " + petManager.getPetCount() + " pet(s) and "
                    + sessionLog.getSessionCount() + " session(s).");
            showMessage("State loaded from " + JSON_STORE, "Loaded!", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            showMessage("Unable to load from: " + JSON_STORE, "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: clears and repopulates session table from current session log.
    private void refreshSessionTable() {
        sessionTableModel.setRowCount(0);
        for (FocusSession s : sessionLog.getSessions()) {
            addSessionToTable(s);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to load saved data when application starts.
    private void offerLoadOnStart() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Welcome to compet!\nWould you like to load your saved data?",
                "Load Saved Data", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            loadState();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to save before closing, prints event log, then exits.
    private void handleWindowClose() {
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }
        int choice = JOptionPane.showConfirmDialog(this,
                "Would you like to save your progress before quitting?",
                "Save Before Quit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            saveState();
        }
        if (choice != JOptionPane.CANCEL_OPTION) {
            printEventLog();
            System.exit(0);
        }
    }

    // EFFECTS: prints all logged events to console.
    private void printEventLog() {
        System.out.println("\n=== Compet Event Log ===");
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.toString() + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: updates status bar label text.
    private void setStatus(String msg) {
        for (Component c : ((JPanel) getContentPane()).getComponents()) {
            if (c instanceof JPanel) {
                searchAndSetStatus((JPanel) c, msg);
            }
        }
    }

    // REQUIRES: panel and msg are not null.
    // MODIFIES: panel
    // EFFECTS: recursively searches panel hierarchy for status label and updates
    // text.
    private void searchAndSetStatus(JPanel panel, String msg) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JLabel && "statusLabel".equals(c.getName())) {
                ((JLabel) c).setText(msg);
                return;
            }
            if (c instanceof JPanel) {
                searchAndSetStatus((JPanel) c, msg);
            }
        }
    }

    // REQUIRES: msg and title are not empty.
    // EFFECTS: shows message dialog with given message, title, and message type.
    private void showMessage(String msg, String title, int type) {
        JOptionPane.showMessageDialog(this, msg, title, type);
    }

    // REQUIRES: label and color are not null, listener is not null.
    // EFFECTS: creates and returns styled JButton with given label, color, and
    // action.
    private JButton makeButton(String label, Color color, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(6, 12, 6, 12));
        btn.addActionListener(listener);
        return btn;
    }
}