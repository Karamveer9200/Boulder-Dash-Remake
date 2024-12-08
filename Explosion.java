import javafx.scene.image.Image;

public class Explosion extends Element {

    public Explosion(final int row, final int column) {
        super(row, column);
        image = new Image("images/explosion.png");
        canBeEntered = false;
        canExplode = true;
        name = "Explosion";
    }



    // In the 3x3 around the selected index replace explodable tiles with explosion tiles
    public static void createExplosion(final int row, final int col,
                                       final GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
                    Element element = gridManager.getElement(i, j);
                    if (element.isCanExplode()) {
                        // If the element is an amoeba, remove it from its group
                        if (element instanceof Amoeba amoeba) {
                            AmoebaGroup group = findAmoebaGroup(amoeba, gridManager);
                            if (group != null) {
                                group.removeAmoeba(amoeba);
                            }
                        }

                        // Replace the element with an explosion
                        gridManager.explosionRemoveFromList(element);
                        Explosion explosion = new Explosion(i, j);
                        gridManager.setElement(i, j, explosion);
                    }
                }
            }
        }
    }

    /**
     * Finds and returns the AmoebaGroup that contains the given amoeba.
     *
     * @param amoeba the Amoeba object to search for
     * @param gridManager the GridManager containing the list of AmoebaGroups
     * @return the AmoebaGroup containing the amoeba,
     * or null if no such group exists
     */
    private static AmoebaGroup findAmoebaGroup(final Amoeba amoeba,
                                               final GridManager gridManager) {
        for (AmoebaGroup group : gridManager.getAmoebaGroups()) {
            if (group.contains(amoeba)) {
                return group;
            }
        }
        return null;
    }


    // SHOULD ONLY HAPPEN AFTER 'createExplosion'
    // In the 3x3 around the selected index replace the explosions that were there with paths.
    public static void createExplosionAfterMath(final int row, final int col, final GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
                    Element element = gridManager.getElement(i, j);
                    if (element instanceof Explosion) {
                        // Replace explosion with Path
                        Path path = new Path(i, j);
                        gridManager.setElement(i, j, path);
                    }
                }
            }
        }
    }

}
