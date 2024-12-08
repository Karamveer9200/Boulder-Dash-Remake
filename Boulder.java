import javafx.scene.image.Image;

/**
 * @author Omar Sanad
 * The Boulder class represents a boulder in the game, which is an element that extends
 * from DangerousRock. It defines characteristics specific to boulders, such as their image
 * and name. A boulder has the potential to gain momentum and move within a grid-based system
 * when interacting with other elements like magic walls or paths.
 */
public class Boulder extends  DangerousRock {
    private final boolean hasMomentum = false;

    /**
     * Constructs a Boulder object positioned at a specified row and column
     * within the grid. The Boulder is initialized with an image and name, and
     * can interact with other game elements such as paths and magic walls.
     *
     * @param row the row position of the boulder in the grid.
     * @param column the column position of the boulder in the grid.
     */
    public Boulder(int row ,int column) {
        super(row,column);
        image = new Image("images/boulder.png");

        name = "Boulder";
    }



    @Override
    public String toString() {
        return "Boulder";
    }
}
