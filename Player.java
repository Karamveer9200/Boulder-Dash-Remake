import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Player extends Element {

    private final List<KeyColour> keyInventory;
    private int diamondCount = 0;
    private boolean hasEnoughDiamonds;

    public Player(int row, int column){
        super(row, column);
        canExplode = true;
        image = new Image("images/player.png");
        canBeEntered = true;
        name = "Player";
        this.keyInventory = new ArrayList<>();
        hasEnoughDiamonds = false;
    }

    public void collectKey(Key key) {
        System.out.println(key);
        keyInventory.add(key.getColour());
    }

    public void resetKeyInventory(){
        System.out.println("Key inventory reset");
        keyInventory.clear();
    }

    public boolean hasKey(KeyColour colour) {
        return keyInventory.contains(colour);
    }

    public void useKey(KeyColour colour) {
        keyInventory.remove(colour);
    }

    public int getDiamondCount() {
        return diamondCount;
    }

    public boolean isHasEnoughDiamonds() {
        return hasEnoughDiamonds;
    }

    public void setHasEnoughDiamonds(boolean hasEnoughDiamonds) {
        this.hasEnoughDiamonds = hasEnoughDiamonds;
    }
    public void checkDiamonds() {
        if (getDiamondCount() >= 1) {
            setHasEnoughDiamonds(true);
            System.out.println("You have enough Diamonds to finish the level!");
        }
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
            gridManager.removeElement(this.getRow(),this.getColumn());
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
        if (grid[targetRow][targetColumn] instanceof Dirt || grid[targetRow][targetColumn] instanceof Path) {
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
        if(grid[targetRow][targetColumn] instanceof Diamond) {
            diamondCount++;
            gridManager.removeFromList(gridManager.getElement(targetRow, targetColumn)); //remove from diamonds list to stop falling
            gridManager.removeElement(targetRow, targetColumn);
            grid[targetRow][targetColumn] = null;
            System.out.println("Diamond removed");
            Main.calculateScore();
            checkDiamonds();
            return true;
        }
        if (grid[targetRow][targetColumn] instanceof Key key) {
            collectKey(key);
            return true;
        }
        if (grid[targetRow][targetColumn] instanceof LockedDoor lockedDoor) {
            System.out.println(hasKey(KeyColour.RED));
            if (hasKey(lockedDoor.getColour())) {
                useKey(lockedDoor.getColour());
                lockedDoor.unlock();
                return true;
            } else {
                System.out.println("Player needs a " + lockedDoor.getColour() + " key to open this door.");
                return false;
            }
        }
        if (grid[targetRow][targetColumn] instanceof Exit exit) {
            if (isHasEnoughDiamonds()) {
                // If the player has enough diamonds, unlock the Exit and announce level win
                exit.unlock();
                exit.announceLevelWin();
                return true;
            } else {
                System.out.println("Player needs more diamonds to enter the exit!");
                return false;
            }

            // Otherwise, the move is invalid
        }
        return false;
    }


    public void resetDiamondCountStatus() {
        this.diamondCount = 0;
        this.hasEnoughDiamonds = false;
        System.out.println("Diamond count reset to: " + this.diamondCount);
    }


    @Override
    public String toString() {
        return "Player CollectedDiamonds: " + diamondCount;
    }

}
