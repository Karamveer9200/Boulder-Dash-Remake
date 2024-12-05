import javafx.scene.image.Image;

public class MagicWall extends Tile {

    public MagicWall(int row,int column) {
        super(row, column);
        image = new Image("images/MagicWall.png");
        canBeEntered = false;
        canExplode = true;
        name = "MagicWall";
    }

    public void transformRock(Element element,GridManager gridManager) {
        if (element instanceof Boulder) {
            // removes and disables the ability for enemies to move, also player
            Element elementBelowMagicWall = gridManager.getElement(this.getRow() + 1, this.getColumn());
            gridManager.removeFromList(elementBelowMagicWall);
            System.out.println("Boulder entered a magic wall");
            Diamond diamond = new Diamond(element.getRow() + 2,element.getColumn() );
            gridManager.removeFromList(element);
            gridManager.removeElement(element.getRow(), element.getColumn());
            gridManager.setElement(element.getRow() + 2, element.getColumn(), diamond);
            gridManager.addToList(diamond);

        } else if (element instanceof Diamond) {
            // removes and disables the ability for enemies to move, also player
            Element elementBelowMagicWall = gridManager.getElement(this.getRow() + 1, this.getColumn());
            gridManager.removeFromList(elementBelowMagicWall);

            System.out.println("Diamond entered a magic wall");
            Boulder boulder = new Boulder( element.getRow() + 2, element.getColumn());
            gridManager.setElement(element.getRow() + 2, element.getColumn(), boulder);
            gridManager.addToList(boulder);

            gridManager.removeFromList(element);
            gridManager.removeElement(element.getRow(), element.getColumn());
        }
    }

    @Override
    public String toString() {
        return "MagicWall";
    }
}
