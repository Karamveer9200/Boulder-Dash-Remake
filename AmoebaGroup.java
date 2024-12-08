import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AmoebaGroup {
    private List<Amoeba> amoebas; // All amoebas in this group
    private boolean isGrowing;
    private static final int amoebaSizeLimit = 4;

    public AmoebaGroup() {
        this.amoebas = new ArrayList<>();
        this.isGrowing = true;
    }


    /**
     * Adds an amoeba to the group.
     *
     * @param amoeba the Amoeba to be added to the group
     */
    public void addAmoeba(Amoeba amoeba) {
        amoebas.add(amoeba);
    }

    /**
     * Spreads the amoeba group by one cell if possible.
     */
    public void spread(GridManager gridManager) {
        if (amoebas.size() > amoebaSizeLimit) {
            transformToBoulders(gridManager);
        } else {
            if (!isGrowing) return;

            Element[][] grid = gridManager.getElementGrid();
            int rows = grid.length;
            int cols = grid[0].length;

            // Directions for spreading: up, down, left, right
            int[][] directions = {
                    {-1, 0}, {1, 0}, {0, -1}, {0, 1}
            };

            // Collect valid positions for this group
            List<int[]> validPositions = new ArrayList<>();
            for (Amoeba amoeba : amoebas) {
                for (int[] direction : directions) {
                    int newRow = amoeba.getRow() + direction[0];
                    int newCol = amoeba.getColumn() + direction[1];

                    // Check grid boundaries and if the cell contains dirt, path or an enemy
                    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                        Element target = grid[newRow][newCol];
                        if (target instanceof Dirt || target instanceof Path ||
                                target instanceof Butterfly || target instanceof Firefly) {
                            validPositions.add(new int[]{newRow, newCol});
                        }
                    }
                }
            }


            // If no valid positions, stop growing and convert to diamonds
            if (validPositions.isEmpty()) {
                transformToDiamonds(gridManager);
                isGrowing = false;
            } else {
                // Spread to one random valid position
                Collections.shuffle(validPositions);
                int[] targetPosition = validPositions.get(0);
                int targetRow = targetPosition[0];
                int targetCol = targetPosition[1];

                // Replace the dirt with a new amoeba
                Amoeba newAmoeba = new Amoeba(targetRow, targetCol);
                gridManager.setElement(targetRow, targetCol, newAmoeba);
                this.addAmoeba(newAmoeba);
                System.out.println("Amoeba spread to (" + targetRow + ", " + targetCol + ")!");
            }
        }
    }

    /**
     * Replaces all amoebas in the group with diamonds and removes them from the list.
     *
     * @param gridManager the grid manager to access and update the grid
     */
    private void transformToDiamonds(GridManager gridManager) {
        for (Amoeba amoeba : amoebas) {
            int row = amoeba.getRow();
            int col = amoeba.getColumn();
            Diamond newDiamond = new Diamond(row, col);
            gridManager.setElement(row, col, newDiamond); // Replace amoeba with a diamond
            gridManager.addToList(newDiamond);
        }
        amoebas.clear();
        System.out.println("All Amoebas in the group transformed into diamonds!");
    }

    /**
     * Replaces all amoebas in the group with boulders and removes them from the list.
     *
     * @param gridManager the grid manager to access and update the grid
     */
    private void transformToBoulders(GridManager gridManager) {
        for (Amoeba amoeba : amoebas) {
            int row = amoeba.getRow();
            int col = amoeba.getColumn();
            Boulder newBoulder = new Boulder(row, col);
            gridManager.setElement(row, col, newBoulder);
            gridManager.addToList(newBoulder);
        }
        amoebas.clear();
        System.out.println("All Amoebas in the group transformed into boulders!");
    }

    /**
     * Returns the number of amoebas in this group.
     *
     * @return the number of amoebas in this group
     */
    public int size() {
        return amoebas.size();
    }

    /**
     * Checks if the amoeba group is empty.
     *
     * @return true if there are no amoebas in the group, false otherwise
     */
    public boolean isEmpty() {
        return amoebas.isEmpty();
    }

    /**
     * Removes the given amoeba from the group.
     * @param amoeba the Amoeba to be removed from the group
     */
    public void removeAmoeba(Amoeba amoeba) {
        amoebas.remove(amoeba);
    }

    /**
     * Checks if the specified amoeba is part of this group.
     *
     * @param amoeba the Amoeba to be checked
     * @return true if the amoeba is in the group, false otherwise
     */
    public boolean contains(Amoeba amoeba) {
        return amoebas.contains(amoeba);
    }
}
