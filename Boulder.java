import javafx.scene.image.Image;

public class Boulder extends DangerousRock {
    private boolean hasMomentum = false;

    public Boulder(int row, int column) {
        super(row, column);
        canExplode = true;
        image = new Image("images/boulder.png");
        name = "Boulder";
    }

}