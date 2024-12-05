import java.io.*;
import java.util.*;

/**
 * FileHandler handles reading and writing grid templates to and from files.
 */
public class FileHandler {

    public static String[][] readFile(String fileName) {
        File readFile = new File(fileName);
        try
        {
            Scanner in = new Scanner(readFile);

            String[] splitGridDimensions = in.nextLine().split(" ");
            int width = Integer.parseInt(splitGridDimensions[0]);
            int height = Integer.parseInt(splitGridDimensions[1]);

            int secondsLeft = Integer.parseInt(in.nextLine());
            int diamondsToCollect = Integer.parseInt(in.nextLine());

            String[] splitAmoebaInfo = in.nextLine().split(" ");
            int amoebaGrowthRate = Integer.parseInt(splitAmoebaInfo[0]);
            int amoebaSizeLimit = Integer.parseInt(splitAmoebaInfo[1]);

            String[] splitCollectedKeys = in.nextLine().split(" ");
            for (int i = 0; i < splitCollectedKeys.length; i++)
            {
                // Have to write code here that reads in collected keys so far
            }

            String[][] initialGrid = new String[height][width];
            int i = 0;
            while (in.hasNextLine()) {
                String[] elements = in.nextLine().split(" ");
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

    public static void writeFile(GridManager gridManager, PlayerProfile currentProfile) {
        Element[][] currentGrid = gridManager.getElementGrid();

        int id = currentProfile.getPlayerId();
        String fileName = "Save" + id + ".txt";

        try
        {
            String outputFile = "Boulder-Dash-Remake/txt/" + fileName;
            PrintWriter out = new PrintWriter(outputFile);
            out.println(currentGrid[0].length + " " + currentGrid.length);
            out.println(120); //Pass seconds left and output it here
            out.println(10); //Pass diamonds left to collect and output it here
            out.println(2 + " " + 8); //Pass Amoeba growth rate and size limit and output it here
            
            out.println(); //Code here to output all the player's collected keys so far.

            for (int i = 0; i < currentGrid.length; i++) {
                for (int j = 0; j < currentGrid[i].length; j++) {
                    switch (gridManager.getElement(i,j).getName()) {
                        case "Player" -> out.print("*");

                        case "Path" -> out.print("P");
                        case "Dirt" -> out.print("DT");
                        case "Exit" -> out.print("E");

                        case "NormalWall" -> out.print("NW");
                        case "TitaniumWall" -> out.print("TW");
                        case "MagicWall" -> out.print("MW");

                        case "Boulder" -> out.print("B");
                        case "Diamond" -> out.print("DD");

                        case "Frog" -> out.print("F");
                        case "Amoeba" -> out.print("A");

                        case "REDKey" -> out.print("RK");
                        case "REDLockedDoor" -> out.print("RLD");
                        case "GREENKey" -> out.print("GK");
                        case "GREENLockedDoor" -> out.print("GLD");
                        case "YELLOWKey" -> out.print("YK");
                        case "YELLOWLockedDoor" -> out.print("YLD");
                        case "BLUEKey" -> out.print("BK");
                        case "BLUELockedDoor" -> out.print("BLK");

                        case "Explosion" -> out.print("P"); // If there is an explosion when we want to save, load a path in its place when the save is loaded

                        //HOW TO SPECIFY FIREFLY AND BUTTERFLY LEFT OR RIGHT? IS IT IN THEIR NAME?
                        // MAYBE AN IF gridManager.getElement(i,j).isButtefly && isLeft???
//                            //case "FFL" -> new FireFly(row,col,LEFT);
//                            //case "FFR" -> new FireFly(row,col,RIGHT);
//                            //case "BFL" -> new FireFly(row,col,LEFT);
//                            //case "BFR" -> new FireFly(row,col,RIGHT);


                        //
                    }
                    if (!(j == currentGrid[i].length - 1)) {
                        out.print(" ");
                    }
                }
                if (i != currentGrid.length - 1) {
                    out.println();
                }
            }
            out.close();
        }
        catch (IOException e)
        {
            System.out.println("Cannot write file");
        }
    }

}