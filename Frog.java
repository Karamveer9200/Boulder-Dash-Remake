import javafx.scene.image.Image;

public class Frog extends Actor {

        public Frog(int column, int row) {
            super(column, row);
            image = new Image("images/frog.png");
            canBeEntered = false;
            canExplode = false;
            name = "Frog";
        }

        /**
         * Moves the Frog one step closer to the player if possible.
         *
         * @param gridManager the grid manager to access and update the grid
         * @param player      the player to seek
         */
        public void seekAndKill(GridManager gridManager, Player player) {
            if (player == null) {
                return; // No player to seek
            }

            Element[][] grid = gridManager.getElementGrid();
            int playerRow = player.getRow();
            int playerCol = player.getColumn();
            int frogRow = this.getRow();
            int frogCol = this.getColumn();

            // Determine primary movement direction (prioritize row movement if both are possible)
            int deltaRow = Integer.compare(playerRow, frogRow); // -1, 0, or 1
            int deltaCol = Integer.compare(playerCol, frogCol); // -1, 0, or 1

            // Prevent diagonal movement
            if (deltaRow != 0 && deltaCol != 0) {
                deltaCol = 0; // Prioritize vertical movement
            }

            // Calculate new position
            int newRow = frogRow + deltaRow;
            int newCol = frogCol + deltaCol;

            // Check bounds and if the move is valid
            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length) {
                Element target = grid[newRow][newCol];


                // Check if the new position is a Path
                if (target instanceof Path) {
                    // Move to the new position
                    gridManager.setElement(newRow, newCol, this); // Move Frog
                    this.setRow(newRow);
                    this.setColumn(newCol);
                    // Replace the Frog's current position with a Path
                    gridManager.setElement(frogRow, frogCol, new Path(frogRow, frogCol));

                } else if (target instanceof Player) {
                    // "Kill" the player
                    gridManager.removeFromList(player); // Remove player from the game
                    gridManager.setElement(newRow, newCol, this); // Replace player with Frog
                    this.setRow(newRow);
                    this.setColumn(newCol);
                    // Replace the Frog's current position with a Path
                    gridManager.setElement(frogRow, frogCol, new Path(frogRow, frogCol));

                    System.out.println("Player has been killed by the Frog!");
                }
            }
        }

        @Override
        public void notified() {
            // Notify the Frog to seek the player
            Player player = Main.player; // Assuming `Main.player` is a reference to the current player
            if (player != null) {
                seekAndKill(gridManager, player);
            }
        }

        @Override
        public String toString() {
            return "Frog";
        }
    }