import javafx.scene.image.Image;

public class Explosion extends Element {

    public Explosion(int row, int column) {
        super(row, column );
        image = new Image("images/explosion.png");
        canBeEntered = false;
        canExplode = true;
        name = "Explosion";
    }

}
