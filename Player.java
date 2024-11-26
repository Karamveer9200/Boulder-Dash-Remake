import javafx.scene.image.Image;

public class Player extends Actor {
    public Player(int column, int row) {
        super(column, row);
        canExplode = true;
        image = new Image("images/player.png");
        canBeEntered = true;
        name = "Player";
    }

    /**
     * Moves the player to a new position on the grid if the move is valid.
     * Updates the player's current position and the grid elements accordingly.
     *
     * @param newRow      the new row position for the player
     * @param newColumn   the new column position for the player
     * @param gridManager the grid manager to update the player's position in the grid
     */
    public void movePlayer(int newRow, int newColumn, GridManager gridManager) {
        if (isValidMove(newRow, newColumn, gridManager)) {
            // Replace the player's current position with a Path
            gridManager.setElement(this.getRow(), this.getColumn(), new Path(this.getRow(), this.getColumn()));

            // Update the grid and the player's position
            gridManager.setElement(newRow, newColumn, this);
            this.setRow(newRow);
            this.setColumn(newColumn);
        }
    }

    /**
     * Checks whether the specified move is valid based on the grid boundaries and the target cell's properties.
     *
     * @param row         the row of the desired move
     * @param col         the column of the desired move
     * @param gridManager the grid manager to access the grid's properties
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMove(int row, int col, GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();
        return row >= 0 && row < grid.length &&
                col >= 0 && col < grid[0].length &&
                grid[row][col].isCanBeEntered();
    }

    @Override
    public void notified() {
        // Custom behavior for when the player is notified
    }

    @Override
    public String toString() {
        return "Player";
    }
}
