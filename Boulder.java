import javafx.scene.image.Image;
public class Boulder extends Actor {

    public Boulder(int x, int y){
        super(x,y);
        canExplode = true;
        image = new Image("images/boulder.png");
        canBeEntered = false;
        name = "Player";
    }

    @Override
    public void notified(){

    }

    @Override
    public String toString() {
        return "Player";
    }
}
