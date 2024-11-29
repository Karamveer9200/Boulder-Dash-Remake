public abstract class Tile extends Element {

    public Tile(int x, int y) {
        super(x, y);
        this.canBeEntered = true;
        this.canExplode = true;
        this.name = "Tile";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void transformTo(Tile newTile) {
    }
}
