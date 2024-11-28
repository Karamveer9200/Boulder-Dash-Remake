import javafx.scene.image.Image;

public class Player extends Element {
    int diamondsCollected = 0;
    public Player(int row, int column) {
        super(row, column);
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

    private boolean isValidMove(int targetRow, int targetColumn, GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();
        // Ensure the move is within bounds
        if (targetRow < 0 || targetRow >= grid.length || targetColumn < 0 || targetColumn >= grid[0].length) {
            return false;
        }

        // Check if the target cell is enterable (e.g., a Path)
        if (grid[targetRow][targetColumn].isCanBeEntered()) {
            return true;
        }

        // Check if the target cell contains a Boulder
        if (targetRow == this.getRow() && grid[targetRow][targetColumn] instanceof Boulder) {
            // Determine the direction of the push
            int pushBoulderToRow = targetRow;
            int pushBoulderToColumn;
            //right or left depending on
            if(targetColumn > this.getColumn()){
                pushBoulderToColumn = targetColumn + 1;
            }else {
                pushBoulderToColumn = targetColumn - 1;
            }

            // Ensure the adjacent cell (where the boulder would move) is within bounds and is a Path
            if (pushBoulderToColumn >= 0 && pushBoulderToColumn < grid[0].length &&
                    gridManager.getElement(pushBoulderToRow,pushBoulderToColumn) instanceof Path) {
                // Move the boulder to the new position
                Boulder boulder = (Boulder)gridManager.getElement(targetRow, targetColumn);

                gridManager.setElement(pushBoulderToRow, pushBoulderToColumn, boulder);
                gridManager.setElement(targetRow, targetColumn, new Path(targetRow, targetColumn));
                boulder.setColumn(pushBoulderToColumn);
                return true;
            }
        }

        // Otherwise, the move is invalid
        return false;
    }





    @Override
    public String toString() {
        return "Player";
    }
}
