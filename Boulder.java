import javafx.scene.image.Image;
public class Boulder extends Actor {

    public Boulder(int column, int row){
        super(column,row);
        canExplode = true;
        image = new Image("images/boulder.png");
        canBeEntered = false;
        name = "Player";
    }
    /**
     * Tries to move the boulder down by one step if the position below is a Path object.
     *
     * @param gridManager the grid manager to access and update the grid
     */
    public void fall (GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();

        int newRow = this.getRow() + 1;
        int col = this.getColumn();
        // Check if the position below is within bounds and is a Path object
        if (newRow < grid.length && grid[newRow][col] instanceof Path) {
            // Update the grid to move the boulder
            gridManager.removeFromList(gridManager.getElement(newRow, col));
            Element p = new Path(this.getRow(), this.getColumn());
            //sets previous boulder location to a path
            gridManager.setElement(this.getRow(), this.getColumn(),p );
            gridManager.addToList(p); //add to list of Path elements

            gridManager.setElement(newRow, col, this);

            // Update the boulder's position
            this.setRow(newRow);
        }

//        // Check if the position below is within bounds and below is a Player object
//        if (newRow < grid.length && grid[newRow][col] instanceof Player) {
//            // Update the grid to move the boulder
//            //sets previous boulder location to a path
//
//            gridManager.removeFromList(gridManager.getElement(row+2,col)); //add to list of Path elements
//            gridManager.setElement(newRow, col, this);
//
//            // Update the boulder's position
//            this.setRow(newRow);
//        }

    }




//    private boolean isValidMoveBoulder(int col, int row ) {
//        Element[][] elementGrid = Grid.getElementGrid();
//        return row >= 0 && row < elementGrid.length &&
//                col >= 0 && col < elementGrid[0].length && // returns true if player is moving to a co-ordinate in bounds
//                elementGrid[row][col].isCanBeEntered(); // and moving to a co-ordinate that canBeEntered == true
//    }
//
//    public void moveBoulderDown() {
//        if (isValidMove(newRow, newColumn)) {
//            gridManager.setElement(player.getRow(), player.getColumn(), new Path(player.getRow(), player.getColumn())); //Put a path where the player just was
//            gridManager.setElement(newRow, newColumn, player); //Put a player where the player is going to
//            player.setRow(newRow);
//            player.setColumn(newColumn);
//        }
//    }

    @Override
    public void notified(){

    }

    @Override
    public String toString() {
        return "Player";
    }
}
