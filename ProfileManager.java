import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

//    private static final Map<Integer, PlayerProfile> profiles = new HashMap<>();

    public static PlayerProfile promptForProfile(Stage primaryStage) {
        Stage dialog = new Stage();
        dialog.setTitle("Create Profile");
        dialog.initModality(Modality.APPLICATION_MODAL);

        Label nameLabel = new Label("Enter Name:");
        TextField nameField = new TextField();

        PlayerProfile createdPlayerProfile = new PlayerProfile();
        System.out.println(createdPlayerProfile.getPlayerId());
        System.out.println(createdPlayerProfile.getMaxLevelReached());


        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String name = nameField.getText().trim(); // Get the name from the field and trim whitespace.
            if (name.isEmpty()) { // Check if the name is empty
                // Show a pop-up alert
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setHeaderText("Name is Required");
                alert.setContentText("Please enter a name.");
                alert.showAndWait(); // Show the pop-up and wait for user action
            } else {
                createdPlayerProfile.setName(name);
                saveProfileToFile(createdPlayerProfile);
                dialog.close();
            }
        });

        VBox dialogBox = new VBox(10, nameLabel, nameField, saveButton);
        dialogBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene dialogScene = new Scene(dialogBox, 300, 150);
        dialog.setScene(dialogScene);
        dialog.showAndWait();

        return createdPlayerProfile;
    }


    // Update to be able to override already existant txt
    //Create a playerprofile txt or update a txt
    public static void saveProfileToFile(PlayerProfile profile) {
        try {
            String outputFile = "Boulder-Dash-Remake/txt/" + profile.getPlayerId() + ".txt";
            PrintWriter out = new PrintWriter(outputFile);
            out.println(profile.getPlayerId());
            out.println(profile.getName());
            out.print(profile.getMaxLevelReached());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//            for (int i = 0; i < profile.getHighScores().length; i++) {
//                writer.write(profile.getHighScores()[i]);
//                if (i != profile.getHighScores().length - 1) {
//                    writer.write(" ");
//                }
//            }

    }

    public static PlayerProfile loadProfileFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int playerId = Integer.parseInt(reader.readLine());
            String playerName = reader.readLine();
            int maxLevelReached = Integer.parseInt(reader.readLine());

//            String[] highScoresLine = reader.readLine().split(" ");
//            int highScore1 = Integer.parseInt(highScoresLine[0]);
//            int highScore2 = Integer.parseInt(highScoresLine[1]);
//            int highScore3 = Integer.parseInt(highScoresLine[2]);
//            int[] highScores = {highScore1, highScore2, highScore3};

            PlayerProfile profile = new PlayerProfile(playerId, playerName, maxLevelReached);
            return profile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<PlayerProfile> getAvailableProfiles() {
        String folderPath = "Boulder-Dash-Remake/txt";
        ArrayList<PlayerProfile> profiles = new ArrayList<>();

        ArrayList<File> matchingFiles = new ArrayList<>(); // Create a list to hold the matching files.
        File folder = new File(folderPath); // Represent the folder as a File object.

        if (folder.exists() && folder.isDirectory()) { // Check if the folder exists and is valid.
            File[] files = folder.listFiles((dir, name) -> name.matches("\\d+\\.txt")); // Filter files with regex.

            if (files != null) { // Ensure files is not null (folder might be empty).
                for (File file : files) {
                    matchingFiles.add(file); // Add each matching file to the list.
                }
            }
        } else {
            System.out.println("Folder does not exist or is not a directory: " + folderPath); // Handle invalid folder.
        }

        for (File file : matchingFiles) { // Iterate over each file.
            try (Scanner scanner = new Scanner(file)) {  // Use try-with-resources to automatically close the scanner.
                int playerId = Integer.parseInt(scanner.nextLine());
                String name = scanner.nextLine();
                int maxLevelReached = Integer.parseInt(scanner.nextLine());
                PlayerProfile profile = new PlayerProfile(playerId, name, maxLevelReached);
                profiles.add(profile);
            } catch (IOException e) {
                System.out.println("Error reading file: " + file.getName());
            }
        }



        return profiles;
    }

    public static void deleteProfile(int idToDelete) {
        String folderPath = "Boulder-Dash-Remake/txt";
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.matches("\\d+\\.txt"));
            if (files != null) { // Ensure files is not null (folder might be empty).
                for (File file : files) { // Iterate over matching files.
                    try (Scanner scanner = new Scanner(file)) { // Use Scanner to read the file.
                        int playerId = Integer.parseInt(scanner.nextLine()); // Read the first line as ID.
                        if (playerId == idToDelete) { // Check if it matches the ID to delete.
                            try {
                                scanner.close();
                                java.nio.file.Files.delete(file.toPath());
                            } catch (IOException e) {
                                System.out.println("Failed to delete file: " + file.getName());
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException | NumberFormatException e) {
                        System.out.println("Error reading file: " + file.getName());
                    }
                }
            } else {
                System.out.println("No matching files found in the folder.");
            }
        } else {
            System.out.println("Folder does not exist or is not a directory: " + folderPath);
        }
    }
}
