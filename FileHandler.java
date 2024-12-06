import java.io.*;
import java.util.*;

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
            String outputFile = "txt/" + fileName;
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

}