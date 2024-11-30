public abstract class Tile extends Element {

    public Tile(int row, int column) {
        super(row, column);

        this.name = "Tile";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void transformTo(Tile newTile) {
    }
}
