import javafx.scene.image.Image;

public class Fly extends Element {

    public boolean followsLeftEdge;
    private int currentDirection;

    // Directions represented as (rowDelta, colDelta)
    private static final int[][] DIRECTIONS = {
            {-1, 0}, // Up
            {0, 1},  // Right
            {1, 0},  // Down
            {0, -1}  // Left
    };

    public Fly(int row, int column, boolean followsLeftEdge) {
        super(row, column);
        image = new Image("images/butterfly.png");
        canBeEntered = false;
        canExplode = false;
        name = "fly";
        this.followsLeftEdge = followsLeftEdge;
        this.currentDirection = 0; // Start with "Up" direction
    }

    public void move(GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();

        // Determine the next valid direction
        int nextDirection = getNextDirection(grid);
        if (nextDirection != -1) {
            // Calculate new position
            int newRow = this.getRow() + DIRECTIONS[nextDirection][0];
            int newCol = this.getColumn() + DIRECTIONS[nextDirection][1];

            // Move to the new position
            gridManager.setElement(this.getRow(), this.getColumn(), new Path(this.getRow(), this.getColumn())); // Replace current position with Path
            gridManager.setElement(newRow, newCol, this); // Set the butterfly in the new position
            this.setRow(newRow);
            this.setColumn(newCol);

            // Update the current direction
            this.currentDirection = nextDirection;
        }
    }

    private int getNextDirection(Element[][] grid) {
        int direction = currentDirection;

        // Check the wall-following rule
        for (int i = 0; i < 4; i++) {
            int checkDirection = followsLeftEdge ? (direction + 3) % 4 : (direction + 1) % 4; // Left or Right turn
            int row = this.getRow() + DIRECTIONS[checkDirection][0];
            int col = this.getColumn() + DIRECTIONS[checkDirection][1];

            // If valid tile to move
            if (isValidMove(grid, row, col)) {
                return checkDirection;
            }

            // Rotate direction clockwise or counterclockwise
            direction = followsLeftEdge ? (direction + 1) % 4 : (direction + 3) % 4;
        }

        // No valid move found
        return -1;
    }


    private boolean isValidMove(Element[][] grid, int row, int col) {
        // Check boundaries
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            return false;
        }

        // Tile must be a Path, Player, or Amoeba
        Element target = grid[row][col];
        return (target instanceof Path || target instanceof Player || target instanceof Amoeba);
    }

    @Override
    public String toString() {
        return "fly";
    }
}
