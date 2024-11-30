import javafx.scene.image.Image;

public class Boulder extends Element implements DangerousRock {
    private boolean hasMomentum = false;

    public Boulder(int row , int column) {
        super(row, column);
        canExplode = true;
        image = new Image("images/boulder.png");
        canBeEntered = false;
        name = "Boulder";
    }

    /**
     * Gains momentum when the boulder falls.
     */
    @Override
    public void gainMomentum() {
        hasMomentum = true;
    }


    /**
     * Handles the falling logic for the diamond.
     *
     * @param gridManager the grid manager to access and update the grid
     */
    @Override
    public void fall(GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();
        int newRow = this.getRow() + 1;
        int col = this.getColumn();

        if (newRow < grid.length && grid[newRow][col] instanceof Path) {
            // Update the grid to move the diamond
            gridManager.removeFromList(grid[newRow][col]);
            Element p = new Path(this.getRow(), this.getColumn());
            gridManager.setElement(this.getRow(), this.getColumn(), p);
            gridManager.addToList(p);

            gridManager.setElement(newRow, col, this);

            // Update position
            this.setRow(newRow);
            gainMomentum();

        } else if (newRow < grid.length && grid[newRow][col] instanceof Player) {
            // If the diamond lands on a player and has momentum
            if (hasMomentum) {
                gridManager.removeFromList(grid[newRow][col]); // Remove the player
                gridManager.setElement(newRow, col, this);     // Replace player with the diamond
                System.out.println("Boulder has crushed Player. GAME OVER");

                Element p = new Path(this.getRow(), this.getColumn());
                gridManager.setElement(this.getRow(), this.getColumn(), p);
                gridManager.addToList(p);

                this.setRow(newRow);
            }
            hasMomentum = false;

        } else if(newRow < grid.length && grid[newRow][col] instanceof MagicWall && grid[newRow+1][col] instanceof Path ) {
            MagicWall magicWall = (MagicWall) grid[newRow][col];
            magicWall.transformRock(this,gridManager);
            //row under magic wall is within range , and is a path ,
            // anything else it stays over the  magic wall until its clear beneath the magic wall
            //turn into diamond and vice versa
        }else {
            hasMomentum = false; // Reset momentum if the diamond stops
        }
    }
    /**
     * Handles the rolling logic for the diamond.
     *
     * @param gridManager the grid manager to access and update the grid
     */
    @Override
    public void roll(GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();
        int newRow = this.getRow() + 1; // Row below the current position
        int col = this.getColumn();

        // Check if below is a Boulder, Diamond, or NormalWall
        if (newRow < grid.length &&
                (grid[newRow][col] instanceof Boulder ||
                        grid[newRow][col] instanceof Diamond ||
                        grid[newRow][col] instanceof NormalWall)) {

            // Check if rolling to the right is possible
            if (col + 1 < grid[0].length &&
                    grid[newRow][col + 1] instanceof Path &&
                    grid[this.getRow()][col + 1] instanceof Path) {

                // Move to the diagonal right
                Element p = new Path(this.getRow(), this.getColumn());
                gridManager.setElement(this.getRow(), this.getColumn(), p);
                gridManager.addToList(p);

                gridManager.setElement(newRow, col + 1, this);
                this.setRow(newRow);
                this.setColumn(col + 1);

                return;
            }

            // Check if rolling to the left is possible
            if (col - 1 >= 0 &&
                    grid[newRow][col - 1] instanceof Path &&
                    grid[this.getRow()][col - 1] instanceof Path) {

                // Move to the diagonal left
                Element p = new Path(this.getRow(), this.getColumn());
                gridManager.setElement(this.getRow(), this.getColumn(), p);
                gridManager.addToList(p);

                gridManager.setElement(newRow, col - 1, this);
                this.setRow(newRow);
                this.setColumn(col - 1);
            }
        }
    }


    /**
     * Determines if the boulder can fall.
     *
     * @param gridManager the grid manager to access and update the grid
     * @return true if the boulder can fall, false otherwise
     */
    @Override
    public boolean canFall(GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();
        int newRow = this.getRow() + 1;
        int col = this.getColumn();
        return newRow < grid.length && grid[newRow][col] instanceof Path;
    }

    @Override
    public String toString() {
        return "Boulder";
    }
}
