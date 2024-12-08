import javafx.scene.image.Image;
import java.util.*;

public class Frog extends Element {
    public static boolean dropDiamond = true;

    /**
     * Constructor for the Frog class.
     * @param row row position
     * @param column column position
     */
    public Frog(final int row, final int column) {
        super(row, column);
        image = new Image("images/frog.png");
        canBeEntered = false;
        canExplode = true;
        name = "Frog";
    }

    /**
     * Moves the Frog one step closer to the player using Dijkstra's algorithm.
     * Removes the player from the grid on collision.
     * @param gridManager gridManager
     * @param player player
     */
    // Uses Dijkstra's algorithm to move the Frog one step closer to the player.
    public void seekAndKill(final GridManager gridManager, final Player player) {
        if (player == null) {
            return; // No player to seek
        }

        Element[][] grid = gridManager.getElementGrid();
        int rows = grid.length;
        int cols = grid[0].length;

        // Get player and frog positions
        int playerRow = player.getRow();
        int playerCol = player.getColumn();
        int frogRow = this.getRow();
        int frogCol = this.getColumn();

        // Dijkstra's algorithm setup
        int[][] distances = new int[rows][cols];
        boolean[][] visited = new boolean[rows][cols];
        // Store the previous cell in the shortest path
        int[][][] previous = new int[rows][cols][2];

        for (int i = 0; i < rows; i++) {
            Arrays.fill(distances[i], Integer.MAX_VALUE);
            Arrays.fill(visited[i], false);
        }
        distances[frogRow][frogCol] = 0;

        PriorityQueue<int[]> pq =
                new PriorityQueue<>(Comparator.comparingInt(cell ->
                        distances[cell[0]][cell[1]]));
        pq.add(new int[]{frogRow, frogCol});

        // Directions for movement: up, down, left, right
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1} // Up, down, left, right
        };

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currRow = current[0];
            int currCol = current[1];

            if (visited[currRow][currCol]) {
                continue;
            }
            visited[currRow][currCol] = false;

            // Stop if we reach the player
            if (currRow == playerRow && currCol == playerCol) {
                break;
            }

            for (int[] dir : directions) {
                int newRow = currRow + dir[0];
                int newCol = currCol + dir[1];

                // Check bounds and valid paths
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
                        && !visited[newRow][newCol]
                        && (grid[newRow][newCol] instanceof Path
                        || grid[newRow][newCol] instanceof Player)) {

                    // Add movement cost
                    int newDist = distances[currRow][currCol] + 1;
                    if (newDist < distances[newRow][newCol]) {
                        distances[newRow][newCol] = newDist;
                        previous[newRow][newCol] = new int[]{currRow, currCol};
                        pq.add(new int[]{newRow, newCol});
                    }
                }
            }
        }

        // Check if the player is unreachable
        if (distances[playerRow][playerCol] == Integer.MAX_VALUE) {
//            System.out.println("Player is unreachable by the Frog!");

            // Random movement
            List<int[]> validMoves = new ArrayList<>();
            for (int[] dir : directions) {
                int newRow = frogRow + dir[0];
                int newCol = frogCol + dir[1];

                // Check bounds and if the position can be entered
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
                        && grid[newRow][newCol] instanceof Path) {
                    validMoves.add(new int[]{newRow, newCol});
                }
            }

            if (!validMoves.isEmpty()) {
                // Choose a random valid move
                Random random = new Random();
                int[] randomMove = validMoves.get(random.nextInt(validMoves.size()));
                int newRow = randomMove[0];
                int newCol = randomMove[1];

                // Move the Frog
                gridManager.setElement(frogRow, frogCol, new Path(frogRow, frogCol));
                gridManager.setElement(newRow, newCol, this);
                this.setRow(newRow);
                this.setColumn(newCol);
//                System.out.println("Frog is moving randomly.");
            }
            return;
        }

        // Trace the path back from the player to the frog
        LinkedList<int[]> path = new LinkedList<>();
        int[] current = new int[]{playerRow, playerCol};
        while (!Arrays.equals(current, new int[]{frogRow, frogCol})) {
            path.addFirst(current);
            current = previous[current[0]][current[1]];

            // Safeguard against invalid paths
            if (current == null) {
//                System.err.println("Error: Path tracing failed."
//                + "Aborting movement.");
                return;
            }
        }

        // Move one step along the shortest path
        if (!path.isEmpty()) {
            int[] nextStep = path.getFirst();
            int newRow = nextStep[0];
            int newCol = nextStep[1];

            // Move the Frog
            // Replace current position
            gridManager.setElement(frogRow, frogCol,
                    new Path(frogRow, frogCol));
            Element target = grid[newRow][newCol];

            if (target instanceof Path) {
                // Move to new position
                gridManager.setElement(newRow, newCol, this);
                this.setRow(newRow);
                this.setColumn(newCol);

            } else if (target instanceof Player) {
                // Replace player with Frog
                gridManager.setElement(newRow, newCol, this);
                // Remove player from the game
                gridManager.removeFromList(player);
                this.setRow(newRow);
                this.setColumn(newCol);
//                System.out.println("Player has been killed by the Frog!");
            }
        }
    }


    /**
     * Returns a string representation of the Frog object.
     *
     * @return a string "Frog"
     */
    @Override
    public String toString() {
        return "Frog";
    }
}