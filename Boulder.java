import javafx.scene.image.Image;
public class Boulder extends Actor {
private boolean hasMomentum = false;
    public Boulder(int column, int row){
        super(column,row);
        canExplode = true;
        image = new Image("images/boulder.png");
        canBeEntered = false;
        name = "Boulder";

    }
    private void gainMomentum(){
        hasMomentum = true;
    }

    /**
     * Tries to move the boulder down by one step and handles interactions with the player.
     *
     * @param gridManager the grid manager to access and update the grid
     */
    public void fall(GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();

        int newRow = this.getRow() + 1;
        int col = this.getColumn();

        // Check if the position below is within bounds and is a Path object
        if (newRow < grid.length && grid[newRow][col] instanceof Path) {
            // Update the grid to move the boulder
            gridManager.removeFromList(gridManager.getElement(newRow, col));
            Element p = new Path(this.getRow(), this.getColumn());
            gridManager.setElement(this.getRow(), this.getColumn(), p); // Replace old location with a Path
            gridManager.addToList(p);

            gridManager.setElement(newRow, col, this); // Move the boulder to the new location

            // Update the boulder's position
            this.setRow(newRow);

            // Set momentum if the boulder has fallen for more than one block
            hasMomentum = true;

        } else if (newRow < grid.length && grid[newRow][col] instanceof Player) {
            // If the position below is a Player and the boulder has momentum
            if (hasMomentum) {
                gridManager.removeFromList(grid[newRow][col]); // Remove the player from its list
                gridManager.setElement(newRow, col, this); // Replace player with the boulder
                Element p = new Path(this.getRow(), this.getColumn()); // Old location becomes a Path
                gridManager.setElement(this.getRow(), this.getColumn(), p);
                gridManager.addToList(p); // Add the new Path to the list

                // Update the boulder's position
                this.setRow(newRow);            }

            // Reset momentum after the interaction
            hasMomentum = false;

        } else {
            // If the boulder stops (hits a non-Path and non-Player), reset momentum
            hasMomentum = false;
        }
    }

    public Boolean canFall(GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();
        int newRow = this.getRow() + 1;
        int col = this.getColumn();
        return newRow < grid.length && grid[newRow][col] instanceof Path;
    }

    @Override
    public void notified() {

        if (canFall(gridManager)) {
            fall(gridManager);
        }
    }

    @Override
    public String toString() {
        return "Player";
    }
}

