import javafx.scene.image.Image;
public class NormalWall extends Element {

    public NormalWall(int x, int y) {
        super(x, y);
        image = new Image("images/icon.png");
        canBeEntered = false;
        boolean canExplode = true;
        name = "NormalWall";
    }

    @Override
    public String toString() {
        return "NormalWall";
    }
}
