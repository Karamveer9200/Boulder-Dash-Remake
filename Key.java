import javafx.scene.image.Image;

public class Key extends Tile {
    private final KeyColour colour;

    public Key(int row, int column, KeyColour colour) {
        super(row, column);
        this.colour = colour;
        canBeEntered = true;
        name = colour + "Key";

        switch (colour) {
            case RED -> image = new Image("images/RedKey.png");
            case GREEN -> image = new Image("images/KeyGreen.png");
            case YELLOW -> image = new Image("images/KeyYellow.png");
            case BLUE -> image = new Image("images/KeyBlue.png");
        }
    }

    public KeyColour getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return "Key Colour: " + colour;
    }
}


