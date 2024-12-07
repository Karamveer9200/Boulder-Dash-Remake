import javafx.scene.image.Image;

/**
 * Represents an explosion element within a grid-based game.
 * An Explosion object occupies a grid cell and impacts surrounding cells,
 * potentially transforming them into other elements such as paths or diamonds.
 * This class extends the Element class, inheriting its properties and behaviors.
 */
public class Explosion extends Element {

    /**
     * Constructs an Explosion object at the specified grid position.
     *
     * @param row the row position of the explosion in the grid.
     * @param column the column position of the explosion in the grid.
     */
    public Explosion(int row, int column) {
        super(row, column );
        image = new Image("images/explosion.png");
        canBeEntered = false;
        canExplode = true;
        name = "Explosion";
    }



    /**
     * Creates an explosion effect in a 3x3 grid area around the specified index.
     * This method replaces explodable tiles within the area with explosion tiles.
     *
     * @param row the row index of the center tile around which the explosion occurs
     * @param col the column index of the center tile around which the explosion occurs
     * @param gridManager the grid manager that manages the grid of elements
     */
    // In the 3x3 around the selected index replace explodable tiles with explosion tiles
    public static void createExplosion(int row, int col, GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
                    if (gridManager.getElement(i,j).isCanExplode()) {
                        gridManager.destroyRemoveFromList(gridManager.getElement(i,j));
                        System.out.println(gridManager.getElement(i,j).toString());
                        Explosion explosion = new Explosion(i, j);
                        gridManager.setElement(i, j, explosion);
                    }
                }
            }
        }
    }

    /**
     * Replaces explosion elements with paths in a 3x3 grid area around the specified index.
     * This method should be called after an explosion has occurred in the grid.
     *
     * @param row the row index of the center tile around which exploded elements will be replaced
     * @param col the column index of the center tile around which exploded elements will be replaced
     * @param gridManager the grid manager that manages the grid of elements
     */
    // SHOULD ONLY HAPPEN AFTER 'createExplosion'
    // In the 3x3 around the selected index replace the explosions that were there with paths.
    public static void createExplosionAfterMath (int row, int col, GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
                    if (gridManager.getElement(i,j).isCanExplode()) {
                        Path path = new Path(i, j);
                        gridManager.setElement(i, j, path);
                    }
                }
            }
        }
    }

    /**
     * Transforms exploded elements into diamonds within a 3x3 grid area centered at the specified index.
     * This method should be invoked after an explosion has been created in the grid to handle the aftermath.
     *
     * @param row the row index of the grid's center tile where the diamond effect is to be applied
     * @param col the column index of the grid's center tile where the diamond effect is to be applied
     * @param gridManager the grid manager that manages the grid of elements
     */
    // SHOULD ONLY HAPPEN AFTER 'createExplosion'
    // In the 3x3 around the selected index replace the explosions that were there with paths.
    public static void createDiamondExplosionAfterMath (int row, int col, GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
                    if (gridManager.getElement(i,j).isCanExplode()) {
                        Diamond diamond = new Diamond(i, j);
                        gridManager.addToList(diamond);
                        gridManager.setElement(i, j, diamond);
                    }
                }
            }
        }
    }

}
