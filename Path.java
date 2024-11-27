public class Path extends Tile {

    public Path(int x, int y) {
        super(x, y);
        canExplode = true;
        canBeEntered = true;
        name = "Path";
    }

}
