import javafx.scene.image.Image;

public class Boulder extends DangerousRocks {

    public Boulder(int column, int row) {
        super(column, row);
        canExplode = true;
        image = new Image("images/boulder.png");
        canBeEntered = false;
        name = "Boulder";
    }

    @Override
    public String toString() {
        return "Boulder";
    }
}
