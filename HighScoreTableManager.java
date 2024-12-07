import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    public static void displayHighScoresInMainMenu(int level, Stage parentDialog) {
        Stage dialog = new Stage();
        dialog.setTitle("High Score Table for Level " + level);
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        ArrayList<HighScore> highScores = HighScoreTableManager.getHighScores(level);

        for (HighScore highScore : highScores) {
            Label scoreLabel = new Label(highScore.getName() + " : " + highScore.getScore());
            scoreLabel.setStyle("-fx-font-size: 18px; -fx-text-alignment: center;");
            vbox.getChildren().add(scoreLabel);
        }

        Button backButton = new Button("Back to High Scores");
        backButton.setOnAction(event -> {
            dialog.close();
            parentDialog.show();
        });

        vbox.getChildren().add(backButton);
        
        Scene dialogScene = new Scene(vbox, 400, 500);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    public static void displayHighScoresAfterLevel(int level, int scoreAchieved) {
        Stage dialog = new Stage();
        dialog.setTitle("High Score Table for Level " + level);
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        ArrayList<HighScore> highScores = HighScoreTableManager.getHighScores(level);

        for (HighScore highScore : highScores) {
            Label scoreLabel = new Label(highScore.getName() + " : " + highScore.getScore());
            scoreLabel.setStyle("-fx-font-size: 18px; -fx-text-alignment: center;");
            vbox.getChildren().add(scoreLabel);
        }

        Label addSpace = new Label();
        addSpace.setMinHeight(20);
        vbox.getChildren().add(addSpace);

        Label recentScore = new Label("Your Score: " + scoreAchieved);
        recentScore.setStyle("-fx-font-size: 18px; -fx-text-alignment: center;");

        vbox.getChildren().add(recentScore);


        String buttonText;

        if (level == 3) {
            buttonText = "Close";
        } else {
            buttonText = "Begin Next Level";
        }


        Button backButton = new Button(buttonText);
        backButton.setOnAction(event -> {
            dialog.close();
        });

        vbox.getChildren().add(backButton);

        Scene dialogScene = new Scene(vbox, 400, 500);
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
