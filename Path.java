public class Path extends Element {

    public Path(int row, int column) {
        super(row, column);
        canExplode = true;
        canBeEntered = true;
        name = "Path";
    }

}
