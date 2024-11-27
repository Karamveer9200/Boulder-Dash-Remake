import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Player extends Element {

    private final List<KeyColour> keyInventory;
    private int diamondCount = 0;
    private boolean hasEnoughDiamonds;


    public Player(int x, int y){
        super(x,y);
        canExplode = true;
        image = new Image("images/player.png");
        canBeEntered = true;
        name = "Player";
        this.keyInventory = new ArrayList<>();
        hasEnoughDiamonds = false;
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

    public int getDiamondCount() {
        return diamondCount;
    }

    public void setDiamondCount(int diamondCount) {
        this.diamondCount = diamondCount;
    }
    public boolean isHasEnoughDiamonds() {
        return hasEnoughDiamonds;
    }

    public void setHasEnoughDiamonds(boolean hasEnoughDiamonds) {
        this.hasEnoughDiamonds = hasEnoughDiamonds;
    }
    public void collectDiamond(Diamond diamond) {
        setDiamondCount(getDiamondCount() + 1);
        System.out.println("Diamonds collected: " + getDiamondCount());
        checkDiamonds();
    }
    public void checkDiamonds() {
        if (getDiamondCount() >= 1) {
            setHasEnoughDiamonds(true);
            System.out.println("You have enough Diamonds to finish the level!");
        }
    }




    @Override
    public String toString() {
        return "Player";
    }

}
