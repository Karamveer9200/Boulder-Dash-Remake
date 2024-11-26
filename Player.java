import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Player extends Element {

    private final List<KeyColour> keyInventory;


    public Player(int x, int y){
        super(x,y);
        canExplode = true;
        image = new Image("images/player.png");
        canBeEntered = true;
        name = "Player";
        this.keyInventory = new ArrayList<>();
    }

    public void collectKey(Key key) {
        keyInventory.add(key.getColour());
    }

    public boolean hasKey(KeyColour colour) {
        return keyInventory.contains(colour);
    }

    public void useKey(KeyColour colour) {
        keyInventory.remove(colour);
    }

    @Override
    public String toString() {
        return "Player";
    }
}
