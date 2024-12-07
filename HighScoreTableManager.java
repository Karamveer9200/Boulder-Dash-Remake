import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages the high scores for the game, allowing new entries to be added and saved to a file.
 */
public class HighScoreTableManager {
    private static final int MAX_HIGH_SCORES = 10;

    public static void displayHighScoreTable(int level) {
        Stage dialog = new Stage();
        dialog.setTitle("High Score Table for level " + level);
        dialog.initModality(Modality.APPLICATION_MODAL);
        ArrayList<HighScore> highScores = getHighScores(level);

        // Create a VBox layout to hold the high scores
        VBox vbox = new VBox(10);  // 10 is the spacing between each label

        // Iterate through each high score and create a label
        for (HighScore highScore : highScores) {
            // Create a label with the format "name score"
            String scoreText = highScore.getName() + " " + highScore.getScore();
            Label scoreLabel = new Label(scoreText);
            vbox.getChildren().add(scoreLabel);  // Add the label to the VBox
        }

        // Set up the scene and show the dialog
        Scene dialogScene = new Scene(vbox, 500, 500);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    public static void updateHighScoreTable(String playerName, int score, int level) {
        HighScore newHighScore = new HighScore(playerName, score);
        ArrayList<HighScore> highScores = getHighScores(level);
        highScores.add(newHighScore);
        highScores.sort((h1, h2) -> Integer.compare(h2.getScore(), h1.getScore()));
        if (highScores.size() > 10) {
            highScores.remove(10);
        }
        saveHighScoreTable(highScores, level);
    }

    public static ArrayList<HighScore> getHighScores(int level) {
        String folderPath = "txt";
        ArrayList<HighScore> highScores = new ArrayList<>();

        File folder = new File(folderPath); // Represent the folder as a File object.

        if (folder.exists() && folder.isDirectory()) { // Check if the folder exists and is valid.
            // Find the file matching the level's high score table.
            File[] files = folder.listFiles((dir, name) -> name.matches("HighScoreTable" + level + ".txt"));

            if (files != null && files.length > 0) {
                File matchingFile = files[0]; // Assuming only one file per level.

                try (Scanner scanner = new Scanner(matchingFile)) {  // Use try-with-resources to automatically close the scanner.
                    while (scanner.hasNextLine()) {
                        String[] splitHighScore = scanner.nextLine().split(" ");
                        String name = splitHighScore[0];
                        int score = Integer.parseInt(splitHighScore[1]);
                        HighScore highScore = new HighScore(name, score);
                        highScores.add(highScore);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading file: " + matchingFile.getName());
                }
            } else {
                System.out.println("No matching file found for level " + level);
            }
        } else {
            System.out.println("Folder does not exist or is not a directory: " + folderPath);
        }
        return highScores;
    }

    public static void saveHighScoreTable(ArrayList<HighScore> highScores, int level) {
        try {
            String outputFile = "txt/HighScoreTable" + level + ".txt";
            PrintWriter out = new PrintWriter(outputFile);
            for (int i = 0; i < highScores.size(); i++) {
                HighScore highScore = highScores.get(i);
                out.print(highScore.getName() + " " + highScore.getScore());

                if (i < highScores.size() - 1) {
                    out.println();
                }
            }
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
