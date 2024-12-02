import java.io.*;
import java.util.*;

/**
 * FileHandler handles reading and writing grid templates to and from files.
 */
public class FileHandler {

    public static String[][] readFile(String filename) {
        String directoryPath = "Boulder-Dash-Remake/Level1.txt";
        File readFile = new File(directoryPath);
        try
        {
            Scanner in = new Scanner(readFile);
            String firstLine = in.nextLine();
            String[] splits = firstLine.split(" ");

            int width = Integer.parseInt(splits[0]);
            int height = Integer.parseInt(splits[1]);

            String[][] initialGrid = new String[height][width];

            int i = 0;
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] elements = line.split(" ");
                for (int j = 0; j < elements.length; j++) {
                    initialGrid[i][j] = elements[j];
                }
                i++;
            }
            in.close();
            return initialGrid;
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("Error in finding file");

        }
        return null;
    }

    public void writeFile(String filename) {
        //Implement later
    }


}