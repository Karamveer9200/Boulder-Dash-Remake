import javafx.scene.image.Image;
public class Tile extends Element {
    protected boolean canBeEntered;
    protected boolean canExplode;
    protected String name;


    public Tile (int x, int y){
        super(x,y);
        canBeEntered = true;
        canExplode = true;
        name = "Tyle";
    }

    public void setName(String name){
        this.name = name;
    }

    public void transformTo(Tile newTile){

    }

}
