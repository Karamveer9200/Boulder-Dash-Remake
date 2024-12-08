public class DangerousRock extends Element {

    private boolean hasMomentum = false;

    public DangerousRock(int row, int column) {
        super(row, column);
        canExplode = true;
    }
    /**
     * Gains momentum when the boulder falls.
     */
    public void gainMomentum() {
        hasMomentum = true;
    }

    /**
     * Handles the falling logic for the diamond.
     *
     * @param gridManager the grid manager to access and update the grid
     */
    public void fall(final GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();
        int newRow = this.getRow() + 1;
        int col = this.getColumn();

        if (newRow < grid.length && grid[newRow][col] instanceof Path) {
            // Update the grid to move the boulder
            gridManager.removeFromList(grid[newRow][col]);
            Element p = new Path(this.getRow(), this.getColumn());
            gridManager.setElement(this.getRow(), this.getColumn(), p);
            gridManager.addToList(p);

            gridManager.setElement(newRow, col, this);

            // Update position
            this.setRow(newRow);
            gainMomentum();

        } else if (newRow < grid.length && (grid[newRow][col] instanceof Player
                || grid[newRow][col] instanceof Frog
                || grid[newRow][col] instanceof Firefly)) {
            // If the boulder lands on a player and has momentum
            if (hasMomentum) {
                gridManager.removeFromList(grid[newRow][col]); // Remove the player or enemy
                gridManager.setElement(newRow, col, this);

                Element p = new Path(this.getRow(), this.getColumn());
                gridManager.setElement(this.getRow(), this.getColumn(), p);
                gridManager.addToList(p);

                this.setRow(newRow);
            }
            hasMomentum = false;

        } else if (newRow < grid.length && grid[newRow][col] instanceof MagicWall && (grid[newRow + 1][col] instanceof Path || grid[newRow + 1][col] instanceof Player || grid[newRow + 1][col] instanceof Frog || grid[newRow + 1][col] instanceof Fly)) {

            if (grid[newRow + 1][col] instanceof Frog || grid[newRow + 1][col] instanceof Fly) {
                System.out.println("Boulder has crushed Enemy");
            }
            MagicWall magicWall = (MagicWall) grid[newRow][col];

            magicWall.transformRock(this, gridManager);
            //row under magic wall is within range , and is a path ,
            // anything else it stays over the  magic wall until its clear beneath the magic wall
            //turn into diamond and vice versa
            gainMomentum();
        } else if (newRow < grid.length && grid[newRow][col] instanceof MagicWall && (grid[newRow + 1][col] instanceof Player || grid[newRow + 1][col] instanceof Frog || grid[newRow + 1][col] instanceof Fly)) {
            MagicWall magicWall = (MagicWall) grid[newRow][col];
            magicWall.transformRock(this, gridManager);
            //row under magic wall is within range , and is a path ,
            // anything else it stays over the  magic wall until its clear beneath the magic wall
            //turn into diamond and vice versa
            gainMomentum();

        } else {
            hasMomentum = false; // Reset momentum if the diamond stops
        }
    }

    /**
     * Gains momentum when the object falls.
     * Handles the rolling logic for the diamond.
     *
     * @param gridManager the grid manager to access and update the grid
     */
    public void roll(GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();
        int newRow = this.getRow() + 1; // Row below the current position
        int col = this.getColumn();

        // Check if below is a Boulder, Diamond, or NormalWall
        if (newRow < grid.length &&
                (grid[newRow][col] instanceof Boulder ||
                        grid[newRow][col] instanceof Diamond ||
                        grid[newRow][col] instanceof NormalWall ||
                        grid[newRow][col] instanceof TitaniumWall ||
                        grid[newRow][col] instanceof MagicWall)) {

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

                this.gainMomentum();
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
                this.gainMomentum();
            }
        }
    }
}


