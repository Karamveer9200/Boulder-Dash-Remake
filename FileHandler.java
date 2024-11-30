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
//            case 18 -> new Exit(row, col);
    public static int [][] initialGrid = {
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {3, 10, 0, 0, 0, 0, 0, 1, 7, 3},
            {3, 0, 1, 1, 1, 0, 0, 0, 0, 3},
            {3, 0, 1, 0, 4, 0, 0, 0, 0, 3},
            {3, 0, 1, 1, 1, 4, 1, 0, 11, 3},
            {3, 0, 0, 0, 0, 9, 0, 0, 0, 3},
            {3, 7, 0, 0, 0, 0, 0, 18, 0, 3},
            {3, 0, 0, 0, 3, 2, 0, 0, 0, 3},
            {3, 0, 0, 3, 0, 3, 0, 0, 0, 3},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
    };

    public static int[][] readFile(String filename) {
        //Implement Later
        return initialGrid;
    }

    public void writeFile(String filename) {
        //Implement later
    }


}
