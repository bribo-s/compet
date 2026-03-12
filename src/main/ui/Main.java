package ui;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import javax.swing.SwingUtilities;

// Main class to launch compet app.
@ExcludeFromJacocoGeneratedReport
public class Main {

    // EFFECTS: launches Compet GUI on Swing event dispatch thread.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CompetGUI());
    }
}