import javafx.scene.image.Image;
public class Player extends Actor {
    public Player(int column, int row) {
        super(column, row);
        canExplode = true;
        image = new Image("images/player.png");
        canBeEntered = true;
        name = "Player";
    }

    @Override
    public void notified() {

    }

    @Override
    public String toString() {
        return "Player";
    }

}
