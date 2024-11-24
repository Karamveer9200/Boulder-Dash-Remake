public abstract class Actor extends Element{

    Actor(int row, int column) {
        super(row, column);
    }

    public abstract void notified();
}
