import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * @author Omar Sanad
 * The Player class represents a player in the game, managing their position
 * on the grid, inventory of keys, counting diamonds collected, and game status such as
 * whether they have won.
 *
 * This class extends the Element class, inheriting properties and behaviors
 * common to all elements on the game grid.
 */
public class Player extends Element {

    public static boolean dropDiamond = true;

    private ArrayList<KeyColour> keyInventory;
    private int diamondCount = 0;
    private boolean hasEnoughDiamonds;
    private boolean hasPlayerWon;
    private int diamondsRequired;
    public boolean lookingRight;

    public Player(int row, int column){
        super(row, column);
        canExplode = true;
        image = new Image("images/player.png");
        canBeEntered = true;
        name = "Player";
        this.keyInventory = new ArrayList<>();
        hasEnoughDiamonds = false;
    }

    /**
     * updates the player's image at every call of the animation, currently at every valid player move.
     * If the player is currently facing right, they will be made to face left and
     * vice versa. The corresponding image for the player's direction will be set.
     */
    public void imageAnimation(){
        lookingRight= !lookingRight;
        if(lookingRight){
            image = new Image("images/playerLookingRight.png");
        } else if (!lookingRight) {
            image = new Image("images/player.png");
        }
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

    public void setDiamondCount(int diamondCount) {
        this.diamondCount = diamondCount;
    }

    public boolean isHasEnoughDiamonds() {
        return hasEnoughDiamonds;
    }

    public void setHasEnoughDiamonds(boolean hasEnoughDiamonds) {
        this.hasEnoughDiamonds = hasEnoughDiamonds;
    }

    public void checkDiamonds() {
        if (getDiamondCount() >= diamondsRequired) {
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

    /**
     * Determines if the player's desired move to a specified location on the grid is valid.
     * The validity of the move depends on several factors such as the type of element at the target location,
     * the availability of keys if the element is a locked door, and the direction of the move if it involves pushing a boulder.
     *
     * @param targetRow  the row player intends to move to
     * @param targetColumn the column the player intends to move to
     * @param gridManager the grid manager responsible for managing the grid's state and elements
     * @return true if the move is valid according to the game's rules, false otherwise
     */
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
            checkDiamonds();
            if (isHasEnoughDiamonds()) {
                exit.unlock();
                hasPlayerWon = true;
                return true;
            } else {
                System.out.println("Player needs more diamonds to enter the exit!");
                return false;
            }

            // Otherwise, the move is invalid
        }
        return false;
    }


    /**
     * Reset collected diamond count when user restarts the game.
     */
    public void resetDiamondCountStatus() {
        this.diamondCount = 0;
        this.hasEnoughDiamonds = false;
        System.out.println("Diamond count reset to: " + this.diamondCount);
    }

    public boolean hasPlayerWon() {
        return hasPlayerWon;
    }

    public ArrayList<KeyColour> getKeyInventory() {
        return keyInventory;
    }

    public void setKeyInventory(ArrayList<KeyColour> keyInventory) {
        this.keyInventory = keyInventory;
    }

    public void setDiamondsRequired(int diamondsRequired) {
        this.diamondsRequired = diamondsRequired;
    }

    public int getDiamondsRequired() {
        return diamondsRequired;
    }


    @Override
    public String toString() {
        return "Player CollectedDiamonds: " + diamondCount;
    }

}
