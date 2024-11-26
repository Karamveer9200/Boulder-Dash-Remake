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
     * Spreads the amoeba to a valid adjacent cell, consuming any element in its path.
     *
     * @param gridManager the grid manager controlling the grid
     */
    public void spread(GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();
        int rows = grid.length;
        int cols = grid[0].length;

        // Possible directions for the amoeba to move (up, down, left, right)
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        // Collect valid adjacent positions for spreading
        List<int[]> validPositions = new ArrayList<>();
        for (int[] dir : directions) {
            int newRow = this.getRow() + dir[0];
            int newCol = this.getColumn() + dir[1];

            // Check grid bounds
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                Element target = grid[newRow][newCol];

                // Valid targets: Path, Player, or consumable Elements
                if (target instanceof Dirt || target instanceof Path || target instanceof Player || !(target instanceof Amoeba)) {
                    validPositions.add(new int[]{newRow, newCol});
                }
            }
        }

        // Spread to one valid position (if any)
        if (!validPositions.isEmpty()) {
            // Shuffle to randomize spreading direction
            Collections.shuffle(validPositions);

            // Choose the first position
            int[] targetPos = validPositions.get(0);
            int newRow = targetPos[0];
            int newCol = targetPos[1];
            Element target = grid[newRow][newCol];

            // Handle target consumption
            if (target instanceof Player) {
                gridManager.removeFromList(target); // Remove player from the game
                System.out.println("Player consumed by Amoeba at (" + newRow + ", " + newCol + ")!");
            } else if (target != null) {
                gridManager.removeFromList(target); // Remove any other consumable element
                System.out.println(target.getName() + " consumed by Amoeba at (" + newRow + ", " + newCol + ")!");
            }

            // Replace the target position with a new Amoeba
            Amoeba newAmoeba = new Amoeba(newCol, newRow);
            gridManager.setElement(newRow, newCol, newAmoeba);

            // Replace the current position with a Path (optional for gameplay)
            gridManager.setElement(this.getRow(), this.getColumn(), new Path(this.getColumn(), this.getRow()));
        }
    }

    @Override
    public String toString() {
        return "Amoeba";
    }
}
