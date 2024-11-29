import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AmoebaManager {


     //Spreads all amoebas as a single unit, one cell at a time.
     //If no valid dirt cells remain, all amoebas transform into diamonds.
    public static void spreadAll(GridManager gridManager) {

        Element[][] grid = gridManager.getElementGrid();
        int rows = grid.length;
        int cols = grid[0].length;

        // Directions for spreading: up, down, left, right
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        // Collect all amoebas
        List<Amoeba> amoebas = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] instanceof Amoeba) {
                    amoebas.add((Amoeba) grid[row][col]);
                }
            }
        }

        // Collect valid dirt positions for all amoebas
        List<int[]> validPositions = new ArrayList<>();
        for (Amoeba amoeba : amoebas) {
            for (int[] direction : directions) {
                int newRow = amoeba.getRow() + direction[0];
                int newCol = amoeba.getColumn() + direction[1];

                // Check grid boundaries and if the cell contains dirt
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    Element target = grid[newRow][newCol];
                    if (target instanceof Dirt) {
                        validPositions.add(new int[]{newRow, newCol});
                    }
                }
            }
        }

        // If no valid positions, transform all amoebas into diamonds
        if (validPositions.isEmpty()) {
            transformAllToDiamonds(gridManager, amoebas);
        } else {
            // Spread to one random dirt position
            Collections.shuffle(validPositions);
            int[] targetPosition = validPositions.get(0);
            int targetRow = targetPosition[0];
            int targetCol = targetPosition[1];

            // Replace the dirt with a new amoeba
            gridManager.setElement(targetRow, targetCol, new Amoeba(targetRow, targetCol));
            System.out.println("Amoeba spread to (" + targetRow + ", " + targetCol + ")!");
        }
    }

    //Transforms all amoebas into diamonds.
    private static void transformAllToDiamonds(GridManager gridManager, List<Amoeba> amoebas) {
        System.out.println("All Amoebas transformed into a Diamond!");
        for (Amoeba amoeba : amoebas) {
            int row = amoeba.getRow();
            int col = amoeba.getColumn();
            Diamond  newDiamond = new Diamond(row, col);
            gridManager.setElement(row, col,newDiamond ); // Replace amoeba with a diamond
            gridManager.addToList(newDiamond);
        }
    }
}
