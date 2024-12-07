import javafx.scene.image.Image;

public class Amoeba extends Element {

    public Amoeba(int row, int column) {
        super(row, column);
        image = new Image("images/amoeba.png");
        canBeEntered = false;
        canExplode = true;
        name = "Amoeba";
    }

    @Override
    public String toString() {
        return "Amoeba";
    }
}
