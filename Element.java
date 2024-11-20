import javafx.scene.image.Image;
public abstract class Element {

    protected Image image;
    protected int x;
    protected int y;
    protected boolean canExplode;
    //FOR THE MOMENT ONLY ACTOR IS A PLAYER
    protected boolean canBeEntered;
    protected String name;

    public Element(int x, int y, boolean canExplode, Image image) {
        this.x = x;
        this.y = y;
        this.canExplode = canExplode;
        this.image = image;
    }

    public Element(int x, int y, boolean canExplode) {
        this.x = x;
        this.y = y;
        this.canExplode = canExplode;
    }

    public Element(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public javafx.scene.image.Image getImage() {
        return image;
    }

    public void setImage(javafx.scene.image.Image image) {
        this.image = image;
    }

    public boolean isCanExplode() {
        return canExplode;
    }

    public boolean isCanBeEntered() {
        return canBeEntered;
    }

    public String getName() {
        return name;
    }


}
