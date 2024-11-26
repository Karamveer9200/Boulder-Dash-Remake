import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Amoeba extends Element {
    public Amoeba(int column, int row) {
        super(column, row);
        image = new Image("images/amoeba.png");
        canBeEntered = false;
        canExplode = false;
        name = "Amoeba";
    }

    /**
     * Spreads the Amoeba to one adjacent position, consuming an element if possible.
     *
     * @param gridManager the grid manager to access and update the grid
     */
    public void spread(GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();
        int rows = grid.length;
        int cols = grid[0].length;

        // Directions for adjacent positions (up, down, left, right)
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        // Collect valid adjacent positions
        List<int[]> validPositions = new ArrayList<>();
        for (int[] dir : directions) {
            int newRow = this.getRow() + dir[0];
            int newCol = this.getColumn() + dir[1];

            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                Element target = grid[newRow][newCol];
                if (target instanceof Path || target instanceof Player || target instanceof Element) {
                    validPositions.add(new int[]{newRow, newCol});
                }
            }
        }

        // Spread to a single valid position (if any)
        if (!validPositions.isEmpty()) {
            // Optionally shuffle positions to randomize spreading direction
            Collections.shuffle(validPositions);

            int[] targetPos = validPositions.get(0); // Take the first position after shuffling
            int newRow = targetPos[0];
            int newCol = targetPos[1];
            Element target = grid[newRow][newCol];

            // Consume the target
            if (target instanceof Player) {
                gridManager.removeFromList(target);
                System.out.println("Player consumed by Amoeba!");
            } else if (target instanceof NormalWall) {
                gridManager.removeFromList(target);
                System.out.println("Element consumed by Amoeba!");
            }

            // Replace the target position with a new Amoeba
            gridManager.setElement(newRow, newCol, new Amoeba(newCol, newRow));
        }
    }

    @Override
    public String toString() {
        return "Amoeba";
    }
}

