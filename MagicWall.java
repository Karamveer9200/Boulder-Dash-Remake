import javafx.scene.image.Image;

public class MagicWall extends Tile {

    public MagicWall(int x, int y) {
        super(x, y);
        image = new Image("images/MagicWall.png");
        canBeEntered = false;
        canExplode = true;
        name = "MagicWall";
    }
    public void transformRock(Tile tile) {
        if (tile.getName().equals("Boulder")) {
            tile.setName("Diamond");
            tile.setImage(new Image("images/Diamond.png"));
        } else if (tile.getName().equals("Diamond")) {
            tile.setName("Boulder");
            tile.setImage(new Image("images/Boulder.png"));
        }
    }

    @Override
    public String toString() {
        return "MagicWall";
    }
}
