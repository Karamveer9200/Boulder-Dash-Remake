import javafx.scene.image.Image;

public class Explosion extends Element {

    public Explosion(int row, int column) {
        super(row, column );
        image = new Image("images/explosion.png");
        canBeEntered = false;
        canExplode = true;
        name = "Explosion";
    }


    public static void createExplosion(GridManager gridManager, int row, int col) {
        Element[][] grid = gridManager.getElementGrid();

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
                    if (gridManager.getElement(i,j).isCanExplode()) {
                        Explosion explosion = new Explosion(i, j);
                        gridManager.setElement(i, j, explosion);
                    }
                }
            }
        }
    }

    public static void createExplosionAfterMath (GridManager gridManager, int row, int col) {
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

}
