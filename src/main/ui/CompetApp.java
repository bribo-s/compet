package ui;

import persistence.*;

import java.util.Scanner;
import java.util.List;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.FocusSession;
import model.Interaction;
import model.Pet;
import model.PetManager;
import model.SessionLog;

@ExcludeFromJacocoGeneratedReport
// Console-based application for compet productivity companion.
// Allows users to manage focus sessions and interact with virtual pets.
public class CompetApp {
    private PetManager petManager;
    private SessionLog sessionLog;
    private Interaction interaction;
    private Scanner scanner;
    private boolean running;
    private static final String JSON_STORE = "./data/competapp.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    
    // EFFECTS: constructs compet application and runs it.
    public CompetApp() {
        init();
        runApp();
    }
    
    // MODIFIES: this
    // EFFECTS: initializes application components.
    private void init() {
        petManager = new PetManager();
        sessionLog = new SessionLog();
        interaction = new Interaction();
        scanner = new Scanner(System.in);
        running = true;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }
    
    // MODIFIES: this
    // EFFECTS: displays menu and processes user input until user quits.
    private void runApp() {
        System.out.println("\n=== Welcome to compet ===");
        System.out.println("Your productivity companion awaits!\n");
        
        offerLoadAtStartup();
        
        while (running) {
            displayMenu();
            String command = scanner.nextLine().trim().toLowerCase();
            processCommand(command);
        }
        
        offerSaveBeforeQuit();
        System.out.println("\nThank you for using compet. Keep up the great work!");
        scanner.close();
    }
    
    // EFFECTS: displays main menu options.
    private void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Create a new pet");
        System.out.println("2. Start a focus session");
        System.out.println("3. View current pet");
        System.out.println("4. View all pets");
        System.out.println("5. Switch pet");
        System.out.println("6. View session history");
        System.out.println("7. Save application state");
        System.out.println("8. Load application state");
        System.out.println("9. Quit");
        System.out.print("Enter your choice: ");
    }
    
    // MODIFIES: this
    // EFFECTS: processes user's menu choice.
    private void processCommand(String command) {
        if (command.equals("1")) {
            createPet();
        } else if (command.equals("2")) {
            startFocusSession();
        } else if (command.equals("3")) {
            viewCurrentPet();
        } else if (command.equals("4")) {
            viewAllPets();
        } else if (command.equals("5")) {
            switchCurrentPet();
        } else if (command.equals("6")) {
            viewSessionHistory();
        } else if (command.equals("7")) {
            saveApplicationState();
        } else if (command.equals("8")) {
            loadApplicationState();
        } else if (command.equals("9")) {
            running = false;
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
    
    // MODIFIES: this
    // EFFECTS: creates new pet with user-provided name, type, and room.
    private void createPet() {
        System.out.print("\nEnter pet name: ");
        String name = scanner.nextLine().trim();
    
        System.out.print("Enter pet type (e.g., Cat, Dog, Bird, or Bunny): ");
        String type = scanner.nextLine().trim();
    
        System.out.print("Enter room theme (e.g., Modern Living Room, "
                + "Sunset Living Room, Starry Bedroom, or Lush Bedroom): ");
        String room = scanner.nextLine().trim();
    
        Pet newPet = new Pet(name, type, room);
        petManager.addPet(newPet);
    
        System.out.println("\n" + name + " the " + type + " has joined you!");
        System.out.println("They love their " + room + "!");
    }
    
    // MODIFIES: this
    // EFFECTS: starts one focus session with user-specified duration.
    private void startFocusSession() {
        if (petManager.getCurrentPet() == null) {
            System.out.println("\nYou need to create a pet first!");
            return;
        }
        
        System.out.print("\nEnter session duration (minutes): ");
        int duration = Integer.parseInt(scanner.nextLine().trim());
        
        Pet currentPet = petManager.getCurrentPet();
        
        System.out.println("\nFocus session started for " + duration + " minutes...");
        System.out.println("\nStay focused!\n");
        
        runTimerDemo(duration);
        
        String interactionType = interaction.getRandomInteraction();
        FocusSession session = new FocusSession(duration, currentPet.getName(), interactionType);
        sessionLog.addSession(session);
        currentPet.increaseFondness(session.getFondnessGained());
        
        displayInteraction(interactionType);
        System.out.println(currentPet.getName() + "'s fondness increased to " + currentPet.getFondnessLevel() + "!");
    }
    
    // EFFECTS: runs fast demo timer (1 second per "minute" for testing).
    private void runTimerDemo(int durationMinutes) {
        System.out.println("(Running in demo mode - 1 second per minute)");
        
        for (int remaining = durationMinutes; remaining >= 0; remaining--) {
            System.out.print("\rMinutes remaining: " + remaining + " ");
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
        
        System.out.println("\n\nSession complete! Well done!");
    }
    
    // EFFECTS: runs countdown timer for the specificied duration in minutes.
    // private void runTimer(int durationMinutes) {
    //     int totalSeconds = durationMinutes * 60;
    
    //     for (int remaining = totalSeconds; remaining > 0; remaining--) {
    //         int mins = remaining / 60;
    //         int secs = remaining % 60;
    //         System.out.print("\rTime remaining: " + mins + ":" + String.format("%02d", secs));
    
    //         try {
    //             Thread.sleep(1000);
    //         } catch (InterruptedException e) {
    //             break;
    //         }
    //     }
    
    //     System.out.println("\n\nSession complete! Well done!");
    // }
    
    // EFFECTS: displays random interaction message for current pet.
    private void displayInteraction(String interactionType) {
        Pet currentPet = petManager.getCurrentPet();
        System.out.println("=== Interaction Time! ===");
        
        if (interactionType.equals("petting")) {
            System.out.println("You pet " + currentPet.getName() + ". They purr contentedly!");
        } else if (interactionType.equals("feeding")) {
            System.out.println("You feed " + currentPet.getName() + ". Nom nom!");
        } else if (interactionType.equals("playing")) {
            System.out.println("You play with " + currentPet.getName() + ". So fun!");
        } else if (interactionType.equals("cleaning")) {
            System.out.println("You clean " + currentPet.getName() + ". They sparkle now!");
        }
    }
    
    // MODIFIES: this
    // EFFECTS: switches to different pet by name.
    private void switchCurrentPet() {
        if (petManager.getPetCount() == 0) {
            System.out.println("\nYou don't have any pets yet!");
            return;
        }
        
        System.out.println("\nAvailable pets:");
        for (Pet pet : petManager.getAllPets()) {
            System.out.println("- " + pet.getName() + " (" + pet.getType() + ")");
        }
        
        System.out.print("Enter pet name to switch to: ");
        String name = scanner.nextLine().trim();
        
        Pet foundPet = petManager.getPetByName(name);
        if (foundPet != null) {
            petManager.switchPet(name);
            System.out.println("\nSwitched to " + name + "!");
        } else {
            System.out.println("\nPet not found!");
        }
    }
    
    // EFFECTS: displays all available pets and their fondness levels.
    private void viewAllPets() {
        if (petManager.getPetCount() == 0) {
            System.out.println("\nYou don't have any pets yet!");
            return;
        }
        
        System.out.println("\n=== Your Pets ===");
        for (Pet pet : petManager.getAllPets()) {
            System.out.println("Name: " + pet.getName());
            System.out.println("Type: " + pet.getType());
            System.out.println("Room: " + pet.getRoom());
            System.out.println("Fondness: " + pet.getFondnessLevel());
            System.out.println();
        }
    }
    
    // EFFECTS: displays all past focus sessions.
    private void viewSessionHistory() {
        if (sessionLog.getSessionCount() == 0) {
            System.out.println("\nNo sessions completed yet!");
            return;
        }
        
        System.out.println("\n=== Session History ===");
        System.out.println("Total sessions: " + sessionLog.getSessionCount());
        System.out.println("Total time: " + sessionLog.getTotalDuration() + " minutes\n");
        
        List<FocusSession> sessions = sessionLog.getSessions();
        for (int i = 0; i < sessions.size(); i++) {
            FocusSession session = sessions.get(i);
            System.out.println("Session " + (i + 1) + ":");
            System.out.println(" Duration: " + session.getDurationMinutes() + " minutes");
            System.out.println(" Pet: " + session.getPetName());
            System.out.println(" Interaction: " + session.getInteractionType());
            System.out.println();
        }
    }
    
    // EFFECTS: displays current pet information.
    private void viewCurrentPet() {
        Pet currentPet = petManager.getCurrentPet();
        if (currentPet == null) {
            System.out.println("\nNo pet selected. Create one first!");
            return;
        }
        
        System.out.println("\n=== Current Pet ===");
        System.out.println("Name: " + currentPet.getName());
        System.out.println("Type: " + currentPet.getType());
        System.out.println("Room: " + currentPet.getRoom());
        System.out.println("Fondness: " + currentPet.getFondnessLevel());
    }
    
    // EFFECTS: offer user option to load saved state at startup.
    private void offerLoadAtStartup() {
        System.out.print("Would you like to load your saved data? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes") || response.equals("y")) {
            loadApplicationState();
        }
    }
    
    // EFFECTS: offer user option to save state before quitting.
    private void offerSaveBeforeQuit() {
        System.out.print("\nWould you like to save your progress? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes") || response.equals("y")) {
            saveApplicationState();
        }
    }
    
    // EFFECTS: save current application state to file.
    private void saveApplicationState() {
        try {
            jsonWriter.open();
            jsonWriter.write(petManager, sessionLog);
            jsonWriter.close();
            System.out.println("\nApplication state saved to " + JSON_STORE);
        } catch (java.io.FileNotFoundException e) {
            System.out.println("\nUnable to write to file: " + JSON_STORE);
        }
    }
    
    // MODIFIES: this
    // EFFECTS: load application state from file.
    private void loadApplicationState() {
        try {
            persistence.AppState state = jsonReader.read();
            petManager = state.getPetManager();
            sessionLog = state.getSessionLog();
            System.out.println("\nApplication state loaded from " + JSON_STORE);
            System.out.println("Loaded " + petManager.getPetCount() + " pet(s) and " 
                    + sessionLog.getSessionCount() + " session(s)");
        } catch (java.io.IOException e) {
            System.out.println("\nUnable to read from file: " + JSON_STORE);
        }
    }
}