import javafx.scene.image.Image;

/**
 * Represents a Magic Wall tile in the game.
 * This tile cannot be entered/walked over but can explode.
 * Represented by an image found at "images/MagicWall.png".
 */
public class MagicWall extends Tile {

    /**
     * Creates a new Magic Wall tile at the specified row and column.
     *
     * @param row the row (X co-ord)position of the tile
     * @param column the column (Y co-ord) position of the tile
     */
    public MagicWall(final int row, final int column) {
        super(row, column);
        image = new Image("images/MagicWall.png");
        canBeEntered = false;
        canExplode = true;
        name = "MagicWall";
    }

    /**
     * Transforms a rock element (Boulder or Diamond)
     * when it enters a Magic Wall.
     *
     * @param element the element to transform
     * @param gridManager the grid manager to handle element transformations
     */
    public void transformRock(final Element element, final GridManager gridManager) {
        if (element instanceof Boulder) {
            // Transform Boulder into Diamond
            Element elementBelowMagicWall = gridManager.getElement(this.getRow() + 1, this.getColumn());
            gridManager.removeFromList(elementBelowMagicWall);
            System.out.println("Boulder entered a magic wall");
            Diamond diamond = new Diamond(element.getRow() + 2, element.getColumn());
            gridManager.removeFromList(element);
            gridManager.removeElement(element.getRow(), element.getColumn());
            gridManager.setElement(element.getRow() + 2, element.getColumn(), diamond);
            gridManager.addToList(diamond);
        } else if (element instanceof Diamond) {
            // Transform Diamond into Boulder
            Element elementBelowMagicWall = gridManager.getElement(this.getRow() + 1, this.getColumn());
            gridManager.removeFromList(elementBelowMagicWall);
            System.out.println("Diamond entered a magic wall");
            Boulder boulder = new Boulder(element.getRow() + 2, element.getColumn());
            gridManager.setElement(element.getRow() + 2, element.getColumn(), boulder);
            gridManager.addToList(boulder);
            gridManager.removeFromList(element);
            gridManager.removeElement(element.getRow(), element.getColumn());
        }
    }

    /**
     * Returns the name of the tile as a string.
     *
     * @return the name "MagicWall"
     */
    @Override
    public String toString() {
        return "MagicWall";
    }
}
