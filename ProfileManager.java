import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProfileManager {

    private static final Map<Integer, PlayerProfile> profiles = new HashMap<>();

    public PlayerProfile promptForProfile(Stage primaryStage) {
        Stage dialog = new Stage();
        dialog.setTitle("Create Profile");
        dialog.initModality(Modality.APPLICATION_MODAL);

        Label nameLabel = new Label("Enter Name:");
        TextField nameField = new TextField();

        final PlayerProfile[] newProfile = new PlayerProfile[1];

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            newProfile[0] = new PlayerProfile(name, 0, 0); // Create new profile with default values
            profiles.put(newProfile[0].getPlayerId(), newProfile[0]); // Save the profile in memory
            saveProfileToFile(newProfile[0]); // Save the profile to file
            dialog.close();
        });

        VBox dialogBox = new VBox(10, nameLabel, nameField, saveButton);
        dialogBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene dialogScene = new Scene(dialogBox, 300, 150);
        dialog.setScene(dialogScene);
        dialog.showAndWait();

        return newProfile[0];
    }

    public static void saveProfileToFile(PlayerProfile profile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("#" + profile.getPlayerId() + "." + profile.getName() + ".txt"))) {
            writer.write("PlayerID:" + profile.getPlayerId());
            writer.newLine();
            writer.write("Name:" + profile.getName());
            writer.newLine();
            writer.write("MaxLevelReached:" + profile.getMaxLevelReached());
            writer.newLine();
            writer.write("HighScore:" + profile.getHighScore());
            writer.newLine();
            writer.write("CurrentScore:" + profile.getCurrentScore());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PlayerProfile loadProfileFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String idLine = reader.readLine();
            String nameLine = reader.readLine();
            String maxLevelLine = reader.readLine();
            String highScoreLine = reader.readLine();
            String currentScoreLine = reader.readLine();

            int playerId = Integer.parseInt(idLine.split(":")[1]);
            String playerName = nameLine.split(":")[1];
            int maxLevelReached = Integer.parseInt(maxLevelLine.split(":")[1]);
            int highScore = Integer.parseInt(highScoreLine.split(":")[1]);

            int currentScore = 0; // Default value
            if (currentScoreLine != null) {
                currentScore = Integer.parseInt(currentScoreLine.split(":")[1]);
            }

            PlayerProfile profile = new PlayerProfile(playerName, maxLevelReached, highScore);
            profile.setCurrentScore(currentScore);
            profiles.put(playerId, profile);
            return profile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getAvailableProfiles() {
        File folder = new File(".");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.matches("#\\d+\\.\\w+\\.txt"));
        if (listOfFiles != null) {
            return Arrays.stream(listOfFiles)
                    .map(File::getName)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public static Map<Integer, PlayerProfile> getProfiles() {
        return profiles;
    }
}
