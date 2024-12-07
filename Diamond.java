import javafx.scene.image.Image;

public class Diamond extends DangerousRock {

    public Diamond(int row, int column) {
        super(row, column);
        image = new Image("images/diamond.png");
        name = "Diamond";
    }


    @Override
    public String toString() {
        return "Diamond";
    }
}
