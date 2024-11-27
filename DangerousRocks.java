import javafx.scene.image.Image;

public abstract class DangerousRocks extends Element {
    protected boolean hasMomentum = false;

    public DangerousRocks(int column, int row) {
        super(column, row);
    }

    /**
     * Gains momentum when the object falls.
     */
    protected void gainMomentum() {
        hasMomentum = true;
    }

    /**
     * Handles the falling logic for dangerous rocks.
     *
     * @param gridManager the grid manager to access and update the grid
     */
    public void fall(GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();

        int newRow = this.getRow() + 1;
        int col = this.getColumn();

        if (newRow < grid.length && grid[newRow][col] instanceof Path) {
            // Update the grid to move the rock
            gridManager.removeFromList(gridManager.getElement(newRow, col));
            Element p = new Path(this.getRow(), this.getColumn());
            gridManager.setElement(this.getRow(), this.getColumn(), p); // Replace old location with a Path
            gridManager.addToList(p);

            gridManager.setElement(newRow, col, this); // Move the rock to the new location

            // Update position
            this.setRow(newRow);

            // Set momentum if the rock falls more than one block
            gainMomentum();

        } else if (newRow < grid.length && grid[newRow][col] instanceof Player) {
            // If the rock lands on a player and has momentum
            if (hasMomentum) {
                gridManager.removeFromList(grid[newRow][col]); // Remove the player
                gridManager.setElement(newRow, col, this); // Replace player with the rock
                System.out.println("DangerousRock has crushed Player. GAME OVER");
                Element p = new Path(this.getRow(), this.getColumn());
                gridManager.setElement(this.getRow(), this.getColumn(), p);
                gridManager.addToList(p);

                // Update position
                this.setRow(newRow);
            }
            // Reset momentum
            hasMomentum = false;

        } else {
            // Reset momentum if rock stops
            hasMomentum = false;
        }
    }

    /**
     * Determines if the object can fall.
     *
     * @param gridManager the grid manager to access and update the grid
     * @return true if the object can fall, false otherwise
     */
    public boolean canFall(GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();
        int newRow = this.getRow() + 1;
        int col = this.getColumn();
        return newRow < grid.length && grid[newRow][col] instanceof Path;
    }


    public void notified() {
        if (canFall(gridManager)) {
            fall(gridManager);
        }
    }

    @Override
    public abstract String toString(); // Force subclasses to provide their own string representation
}
