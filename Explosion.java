import javafx.scene.image.Image;

public class Explosion extends Element {

    public Explosion(int row, int column) {
        super(row, column );
        image = new Image("images/explosion.png");
        canBeEntered = false;
        canExplode = true;
        name = "Explosion";
    }



    // In the 3x3 around the selected index replace explodable tiles with explosion tiles
    public static void createExplosion(int row, int col, GridManager gridManager) {
        Element[][] grid = gridManager.getElementGrid();

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
                    if (gridManager.getElement(i,j).isCanExplode()) {
                        gridManager.explosionRemoveFromList(gridManager.getElement(i,j));
                        System.out.println(gridManager.getElement(i,j).toString());
                        Explosion explosion = new Explosion(i, j);
                        gridManager.setElement(i, j, explosion);
                    }
                }
            }
        }
    }

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
