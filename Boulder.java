import javafx.scene.image.Image;

public class Boulder extends  DangerousRock {
    private boolean hasMomentum = false;

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
