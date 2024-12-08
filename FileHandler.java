import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * FileHandler handles reading and writing grid templates to and from files.
 */
public class FileHandler {


//             case 0 -> new Path(row, col);
//            case 1 -> new Dirt(row, col);
//            case 2 -> player = new Player(row, col);
//            case 3 -> new NormalWall(row, col);
//            case 4 -> new Boulder(row, col);
//            case 5 -> new Frog(row, col);
//            case 6 -> new Amoeba(row, col);
//            case 7 -> new Diamond(row, col);
//            case 8 -> new TitaniumWall(row, col);
//            case 9 -> new MagicWall(row, col);
//            case 10 -> new LockedDoor(row, col, KeyColour.RED);
//            case 11 -> new Key(row, col, KeyColour.RED);
//            case 12 -> new LockedDoor(row, col, KeyColour.GREEN);
//            case 13 -> new Key(row, col, KeyColour.GREEN);
//            case 14 -> new LockedDoor(row, col, KeyColour.YELLOW);
//            case 15 -> new Key(row, col, KeyColour.YELLOW);
//            case 16 -> new LockedDoor(row, col, KeyColour.BLUE);
//            case 17 -> new Key(row, col, KeyColour.BLUE);
//            case 18 ->  exit = new Exit(row, col);
//            case 19 -> new Butterfly(row, col, followsLeftEdge);
//            case 20 -> new Firefly(row, col, followsLeftEdge);

//    public static int [][] level1Grid = {
//            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,},
//            {3, 19, 0, 1, 4, 1, 1, 0, 1, 1, 7, 1, 4, 0, 1, 1, 1, 1, 1, 4, 1, 4, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 4, 1, 4, 4, 1, 3,},
//            {3, 5, 8, 0, 0, 0, 1, 1, 4, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 7, 1, 1, 4, 2, 1, 1, 1, 0, 1, 1, 1, 1, 1, 7, 1, 1, 3,},
//            {3, 9, 20, 1, 5, 1, 1, 4, 7, 4, 1, 4, 1, 1, 0, 1, 1, 9, 1, 1, 4, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 4, 4, 3,},
//            {3, 4, 1, 0, 9, 1, 1, 1, 4, 1, 1, 1, 9, 1, 4, 1, 1, 1, 1, 1, 9, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 4, 3,},
//            {3, 4, 1, 4, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 4, 1, 4, 17, 1, 3,},
//            {3, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 0, 4, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 4, 4, 1, 3,},
//            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 4, 1, 1, 4, 1, 3,},
//            {3, 1, 0, 4, 1, 4, 4, 1, 1, 7, 1, 0, 1, 1, 4, 1, 4, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 7, 1, 4, 0, 1, 1, 4, 1, 1, 4, 1, 1, 3,},
//            {3, 7, 4,11, 1, 4, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 4, 0, 0, 4, 1, 1, 7, 1, 4, 1, 1, 7, 1, 1, 1, 3,},
//            {3, 1, 1, 4, 4, 1, 4, 4, 0, 4, 19, 4, 1, 4, 1, 1, 1, 1, 1,1 ,1 ,1 , 1,1 , 4, 4, 1, 4, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 3,},
//            {3, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 4, 4, 1, 1, 4, 4, 0, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 4, 1, 1, 1, 1, 0, 1, 3,},
//            {3, 1, 4, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 4, 1, 1, 4, 1, 4, 7, 1, 1, 7, 1, 1, 1, 1, 4, 1, 1, 4, 4, 1, 1, 1, 1, 4, 1, 3,},
//            {3, 1, 7, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 4, 4, 4, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 4, 7, 1, 1, 1, 1, 1, 4, 3,},
//            {3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,},
//            {3, 0, 0, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 1, 7, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 10, 1, 1, 3,},
//            {3, 4, 0, 1, 1, 1, 1, 4, 1, 1, 3, 4, 1, 4, 4, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 4, 1, 4, 4, 1, 18, 3,},
//            {3, 1, 4, 1, 1, 4, 1, 1, 4, 1, 3, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 4, 7, 4, 4, 1, 1, 3,},
//            {3, 1, 1, 1, 1, 4, 7, 1, 1, 0, 16, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 4, 1, 4, 1, 1, 4, 4, 1, 4, 1, 4, 1, 1, 3,},
//            {3, 1, 1, 1, 0, 1, 1, 0, 1, 4, 3, 1, 4, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 4, 1, 1, 1, 7, 1, 1, 4, 4, 1, 4, 3,},
//            {3, 1, 7, 1, 1, 1, 1, 4, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 4, 1, 1, 3,},
//            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,},
//    };
//
//    public static int [][] level2Grid = {
//            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,},
//            {3, 18, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 16, 3, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 19, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 1, 4, 1, 1, 1, 4, 4, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 1, 1, 4, 4, 4, 1, 11, 4, 4, 1, 1, 1, 4, 4, 1, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 1, 4, 1, 7, 4, 1, 4, 1, 4, 1, 1, 1, 1, 7, 4, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 2, 1, 1, 1, 4, 1, 4, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 1, 1, 1, 1,4, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 1, 4, 1, 1, 4, 4, 1, 4, 1, 4, 4, 1, 1, 1, 7, 1, 1, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,},
//    };
//
//    public static int [][] level3Grid = {
//            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
//            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,},
//    };

    public static String[][] readElementGridFromLevelFile(String fileName) {
        File readFile = new File(fileName);
        try {
            Scanner in = new Scanner(readFile);

            String[] splitGridDimensions = in.nextLine().split(" ");
            int width = Integer.parseInt(splitGridDimensions[0]);
            int height = Integer.parseInt(splitGridDimensions[1]);

            in.nextLine(); //Skip line containing seconds left
            in.nextLine(); //Skip line containing diamond information
            in.nextLine(); //Skip line containing amoeba information
            in.nextLine(); //skip line containing key inventory information

            String[][] initialGrid = new String[height][width];
            int i = 0;
            while (in.hasNextLine()) {
                String[] elements = in.nextLine().split(" ");
                System.arraycopy(elements, 0, initialGrid[i], 0, elements.length);
                i++;
            }
            in.close();
            return initialGrid;
        } catch (FileNotFoundException exception) {
            System.out.println("Error in finding file");

        }
        return null;
    }

    public static void writeFile(GameController gameController, PlayerProfile currentProfile, int secondsRemaining,
                                 ArrayList<KeyColour> keyInventory) {

        Element[][] currentGrid = gameController.getGridManager().getElementGrid();
        int diamondCount = gameController.getGridManager().getPlayer().getDiamondCount();

        int id = currentProfile.getPlayerId();
        String fileName = "Save" + id + ".txt";

        try {
            String outputFile = "txt/" + fileName;
            PrintWriter out = new PrintWriter(outputFile);
            out.println(currentGrid[0].length + " " + currentGrid.length);
            out.println(secondsRemaining); //Pass seconds left and output it here

            out.println(diamondCount + " " + gameController.getDiamondsRequired()); //Pass diamonds collected and how many diamonds left to collect and output it here
            out.println(2 + " " + 8); //Pass Amoeba growth rate and size limit and output it here

            out.println(createKeyInventoryString(keyInventory)); //Code here to output all the player's collected keys so far.

            for (int i = 0; i < currentGrid.length; i++) {
                for (int j = 0; j < currentGrid[i].length; j++) {
                    switch (gameController.getGridManager().getElement(i, j).getName()) {
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
                        case "BLUELockedDoor" -> out.print("BLD");

                        case "Explosion" -> out.print("P"); // If there is an explosion when we want to save, load a path in its place when the save is loaded

                        case "FireflyLeft" -> out.print("FFL");
                        case "FireflyRight" -> out.print("FFR");
                        case "ButterflyLeft" -> out.print("BFL");
                        case "ButterflyRight" -> out.print("BFR");
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
        } catch (IOException e) {
            System.out.println("Cannot write file");
        }
    }

    public static int readSecondsFromLevelFile(String fileName) {
        File readFile = new File(fileName);
        try {
            Scanner in = new Scanner(readFile);
            in.nextLine(); // Skip first line
            return Integer.parseInt(in.nextLine()); // Return seconds
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + fileName, e);
        }
    }

    public static int readDiamondsCollectedFromLevelFile(String fileName) {
        return readDiamondsInformationFromLevelFile(fileName, 0); // Index 0 for diamonds collected
    }

    public static int readRequiredDiamondsFromLevelFile(String fileName) {
        return readDiamondsInformationFromLevelFile(fileName, 1); // Index 1 for required diamonds
    }

    private static int readDiamondsInformationFromLevelFile(String fileName, int index) {
        File readFile = new File(fileName);
        try {
            Scanner in = new Scanner(readFile);
            in.nextLine(); // Skip first line
            in.nextLine(); // Skip second line about seconds left
            String[] splitDiamondInformation = in.nextLine().split(" ");
            return Integer.parseInt(splitDiamondInformation[index]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + fileName, e);
        }
    }

    public static int readAmoebaGrowthRateFromLevelFile(String fileName) {
        return readAmoebaInformationFromLevelFile(fileName, 0); // Index 0 for amoeba growth rate
    }

    public static int readAmoebaSizeLimitFromLevelFile(String fileName) {
        return readAmoebaInformationFromLevelFile(fileName, 1); // Index 1 for amoeba size limit
    }

    private static int readAmoebaInformationFromLevelFile(String fileName, int index) {
        File readFile = new File(fileName);
        try (Scanner in = new Scanner(readFile)) {
            in.nextLine(); // Skip first line about grid dimensions
            in.nextLine(); // Skip second line about seconds left
            in.nextLine(); // Skip third line about diamonds information
            String[] splitAmoebaInfo = in.nextLine().split(" ");
            return Integer.parseInt(splitAmoebaInfo[index]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + fileName, e);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new RuntimeException("Error parsing amoeba information from file: " + fileName, e);
        }
    }

    public static ArrayList<KeyColour> readKeyInventoryFromLevelFile(String fileName) {
        File readFile = new File(fileName);
        ArrayList<KeyColour> keyInventory = new ArrayList<>();
        try {
            Scanner in = new Scanner(readFile);
            in.nextLine(); // Skip first line
            in.nextLine(); // Skip second line about seconds left
            in.nextLine(); // Skip third line about diamond information
            in.nextLine(); // Skip fourth line about amoeba information
            String keyInfo = in.nextLine();
            if (!keyInfo.isBlank()) { // Check if the input line is not blank
                String[] splitKeyInformation = keyInfo.split(" ");
                for (String key : splitKeyInformation) {
                    switch (key) {
                        case "RK":
                            keyInventory.add(KeyColour.RED);
                            break;
                        case "BK":
                            keyInventory.add(KeyColour.BLUE);
                            break;
                        case "YK":
                            keyInventory.add(KeyColour.YELLOW);
                            break;
                        case "GK":
                            keyInventory.add(KeyColour.GREEN);
                            break;
                        default:
                            System.out.println("Error: can't read key " + key);
                            break;
                    }
                }
            }
            return keyInventory;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + fileName, e);
        }
    }

    public static String createKeyInventoryString(ArrayList<KeyColour> keyInventory) {
        StringBuilder keyInventoryString = new StringBuilder();
        for (int i = 0; i < keyInventory.size(); i++) {
            KeyColour key = keyInventory.get(i);
            switch (key) {
                case RED:
                    keyInventoryString.append("RK");
                    break;
                case BLUE:
                    keyInventoryString.append("BK");
                    break;
                case YELLOW:
                    keyInventoryString.append("YK");
                    break;
                case GREEN:
                    keyInventoryString.append("GK");
                    break;
            }

            // Add a space if it's not the last element
            if (i < keyInventory.size() - 1) {
                keyInventoryString.append(" ");
            }
        }

        return keyInventoryString.toString();
    }

}

