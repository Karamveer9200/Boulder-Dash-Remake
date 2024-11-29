import javafx.scene.image.Image;

public class LockedDoor extends Tile {
    private final KeyColour colour;

    public LockedDoor(int x, int y, KeyColour colour) {
        super(x, y);
        this.colour = colour;
        canBeEntered = false;
        name = "LockedDoor";

        switch (colour) {
            case RED -> image = new Image("images/RedLockedDoor.png");
            case GREEN -> image = new Image("images/GreenLockedDoor.png");
            case YELLOW -> image = new Image("images/YellowLockedDoor.png");
            case BLUE -> image = new Image("images/BlueLockedDoor.png");
        }
    }

    public KeyColour getColour() {
        return colour;
    }

    public void unlock() {
        this.canBeEntered = true;
    }
}


