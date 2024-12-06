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

    public static int [][] level1Grid = {
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,},
            {3, 19, 0, 1, 4, 1, 1, 0, 1, 1, 7, 1, 4, 0, 1, 1, 1, 1, 1, 4, 1, 4, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 4, 1, 4, 4, 1, 3,},
            {3, 5, 8, 0, 0, 0, 1, 1, 4, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 7, 1, 1, 4, 2, 1, 1, 1, 0, 1, 1, 1, 1, 1, 7, 1, 1, 3,},
            {3, 9, 20, 1, 5, 1, 1, 4, 7, 4, 1, 4, 1, 1, 0, 1, 1, 9, 1, 1, 4, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 4, 4, 3,},
            {3, 4, 1, 0, 9, 1, 1, 1, 4, 1, 1, 1, 9, 1, 4, 1, 1, 1, 1, 1, 9, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 4, 3,},
            {3, 4, 1, 4, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 4, 1, 4, 17, 1, 3,},
            {3, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 0, 4, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 4, 4, 1, 3,},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 4, 1, 1, 4, 1, 3,},
            {3, 1, 0, 4, 1, 4, 4, 1, 1, 7, 1, 0, 1, 1, 4, 1, 4, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 7, 1, 4, 0, 1, 1, 4, 1, 1, 4, 1, 1, 3,},
            {3, 7, 4,11, 1, 4, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 4, 0, 0, 4, 1, 1, 7, 1, 4, 1, 1, 7, 1, 1, 1, 3,},
            {3, 1, 1, 4, 4, 1, 4, 4, 0, 4, 19, 4, 1, 4, 1, 1, 1, 1, 1,1 ,1 ,1 , 1,1 , 4, 4, 1, 4, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 3,},
            {3, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 4, 4, 1, 1, 4, 4, 0, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 4, 1, 1, 1, 1, 0, 1, 3,},
            {3, 1, 4, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 4, 1, 1, 4, 1, 4, 7, 1, 1, 7, 1, 1, 1, 1, 4, 1, 1, 4, 4, 1, 1, 1, 1, 4, 1, 3,},
            {3, 1, 7, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 4, 4, 4, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 4, 7, 1, 1, 1, 1, 1, 4, 3,},
            {3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,},
            {3, 0, 0, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 1, 7, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 10, 1, 1, 3,},
            {3, 4, 0, 1, 1, 1, 1, 4, 1, 1, 3, 4, 1, 4, 4, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 4, 1, 4, 4, 1, 18, 3,},
            {3, 1, 4, 1, 1, 4, 1, 1, 4, 1, 3, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 4, 7, 4, 4, 1, 1, 3,},
            {3, 1, 1, 1, 1, 4, 7, 1, 1, 0, 16, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 4, 1, 4, 1, 1, 4, 4, 1, 4, 1, 4, 1, 1, 3,},
            {3, 1, 1, 1, 0, 1, 1, 0, 1, 4, 3, 1, 4, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 4, 1, 1, 1, 7, 1, 1, 4, 4, 1, 4, 3,},
            {3, 1, 7, 1, 1, 1, 1, 4, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 4, 1, 1, 3,},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,},
    };

    public static int [][] level2Grid = {
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,},
            {3, 18, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 16, 3, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 19, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 1, 4, 1, 1, 1, 4, 4, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 1, 1, 4, 4, 4, 1, 11, 4, 4, 1, 1, 1, 4, 4, 1, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 1, 4, 1, 7, 4, 1, 4, 1, 4, 1, 1, 1, 1, 7, 4, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 2, 1, 1, 1, 4, 1, 4, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 1, 1, 1, 1,4, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 1, 4, 1, 1, 4, 4, 1, 4, 1, 4, 4, 1, 1, 1, 7, 1, 1, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,},
    };

    public static int [][] level3Grid = {
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,},
    };

    public static int[][] readFile(String filename) {
        //Implement Later
        return level1Grid;
    }

    public void writeFile(String filename) {
        //Implement later
    }


}
