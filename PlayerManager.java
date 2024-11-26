///**
// * Manages the player object and handles player-related operations such as movement.
// */
//public class PlayerManager {
//    private Player player;
//
//    /**
//     * Constructs a PlayerManager with the given player.
//     *
//     * @param player player object to be managed
//     */
//    public PlayerManager(Player player) {
//        this.player = player;
//    }
//
//    /**
//     * Retrieves the current player.
//     *
//     * @return the player object being managed
//     */
//    public Player getPlayer() {
//        return player;
//    }
//
//    /**
//     * Moves the player to a new position on the grid if the move is valid.
//     * Updates the player's current position and the grid elements accordingly.
//     *
//     * @param newRow      the new row position for the player
//     * @param newColumn   the new column position for the player
//     * @param gridManager the grid manager to update the player's position in the grid
//     */
//    public void movePlayer(int newRow, int newColumn, GridManager gridManager) {
//        if (isValidMove(newRow, newColumn, gridManager)) {
//            gridManager.setElement(player.getRow(), player.getColumn(), new Path(player.getRow(), player.getColumn()));
//            gridManager.setElement(newRow, newColumn, player);
//            player.setRow(newRow);
//            player.setColumn(newColumn);
//        }
//    }
//
//    /**
//     * Checks whether the specified move is valid based on the grid boundaries and the target cell's properties.
//     *
//     * @param row         the row of the desired move
//     * @param col         the column of the desired move
//     * @param gridManager the grid manager to access the grid's properties
//     * @return true if the move is valid, false otherwise
//     */
//    private boolean isValidMove(int row, int col, GridManager gridManager) {
//        Element[][] grid = gridManager.getElementGrid();
//        return row >= 0 && row < grid.length &&
//                col >= 0 && col < grid[0].length &&
//                grid[row][col].isCanBeEntered();
//    }
//}
