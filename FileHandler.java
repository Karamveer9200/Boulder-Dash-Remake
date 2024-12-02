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

            String[] splitGridDimensions = in.nextLine().split(" ");
            int width = Integer.parseInt(splitGridDimensions[0]);
            int height = Integer.parseInt(splitGridDimensions[1]);

            int secondsLeft = Integer.parseInt(in.nextLine());
            int diamondsToCollect = Integer.parseInt(in.nextLine());

            String[] splitAmoebaInfo = in.nextLine().split(" ");
            int amoebaGrowthRate = Integer.parseInt(splitAmoebaInfo[0]);
            int amoebaSizeLimit = Integer.parseInt(splitAmoebaInfo[1]);

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

    public static void writeFile(GridManager gridManager) {
        Element[][] currentGrid = gridManager.getElementGrid();

        try
        {
            String outputFile = "Boulder-Dash-Remake/Level1Save.txt";
            PrintWriter out = new PrintWriter(outputFile);
            out.println(currentGrid[0].length + " " + currentGrid.length);
            out.println(120); //Pass seconds left and output it here
            out.println(10); //Pass diamonds left to collect and output it here
            out.println(2 + " " + 8); //Pass Ameoba growthrate and size limit and output it here

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

                        //HOW TO SPECIFY FIREFLY AND BUTTERFLY LEFT OR RIGHT? IS IT IN THEIR NAME?
                        // MAYBE AN IF gridManager.getElement(i,j).isButtefly && isLeft???
//                            //case "FFL" -> new FireFly(row,col,LEFT);
//                            //case "FFR" -> new FireFly(row,col,RIGHT);
//                            //case "BFL" -> new FireFly(row,col,LEFT);
//                            //case "BFR" -> new FireFly(row,col,RIGHT);


                        //HOW TO DO KEYS AND DOORS? IS IT IN THEIR NAME OR SHOULD WE DO IF STATEMENT?
//                            case "RLD" -> new LockedDoor(row, col, KeyColour.RED);
//                            case "RK" -> new Key(row, col, KeyColour.RED);
//                            case "GLD" -> new LockedDoor(row, col, KeyColour.GREEN);
//                            case "GK" -> new Key(row, col, KeyColour.GREEN);
//                            case "YLD" -> new LockedDoor(row, col, KeyColour.YELLOW);
//                            case "YK" -> new Key(row, col, KeyColour.YELLOW);
//                            case "BLD" -> new LockedDoor(row, col, KeyColour.BLUE);
//                            case "BK" -> new Key(row, col, KeyColour.BLUE);
                    }
                    if (!(j == currentGrid[i].length - 1)) {
                        out.print(" ");
                    }
                }
                out.println();
            }
            out.close();
        }
        catch (IOException e)
        {
            System.out.println("Cannot write file");
        }
    }

}