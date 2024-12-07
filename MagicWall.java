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

            System.out.println("Boulder entered a magic wall");
            //disable old rock from falling
            gridManager.removeFromList(element);
            // replace rock location above the magicWall with a path
            gridManager.removeElement(element.getRow(), element.getColumn());
            Diamond diamond = new Diamond(element.getRow() + 1,element.getColumn() );
            gridManager.addToList(diamond);
            diamond.gainMomentum();
//            System.out.println(diamond.getRow() + " " + diamond.getColumn());
        } else if (element instanceof Diamond) {

            System.out.println("Diamond entered a magic wall");
            //disable old rock from falling
            gridManager.removeFromList(element);
            // replace rock location above the magicWall with a path
            gridManager.removeElement(element.getRow(), element.getColumn());
            Boulder boulder = new Boulder(element.getRow() + 1,element.getColumn() );
            gridManager.addToList(boulder);
            boulder.gainMomentum();
//            System.out.println(boulder.getRow() + " " + boulder.getColumn());
        }
    }

    @Override
    public String toString() {
        return "MagicWall";
    }
}
