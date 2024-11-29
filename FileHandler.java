/**
 * FileHandler handles reading and writing grid templates to and from files.
 */
public class FileHandler {

    public static int [][] initialGrid = {
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {3, 0, 0, 0, 0, 0, 0, 0, 7, 3},
            {3, 0, 1, 1, 1, 0, 0, 0, 0, 3},
            {3, 0, 1, 2, 4, 0, 0, 0, 0, 3},
            {3, 0, 1, 1, 1, 1, 1, 0, 0, 3},
            {3, 0, 0, 0, 0, 4, 0, 0, 0, 3},
            {3, 7, 0, 0, 0, 0, 0, 0, 0, 3},
            {3, 0, 0, 0, 3, 0, 0, 0, 0, 3},
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
