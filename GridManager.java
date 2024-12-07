import java.util.ArrayList;

/**
 * The GridManager is responsible for managing the grid of elements in the game.
 * It initializes the grid based on a template, provides access to individual elements,
 * manages lists of specific element types, and supports adding, removing, and updating elements.
 */
public class GridManager {
    private final Element[][] elementGrid;
    private final ArrayList<Path> paths = new ArrayList<>();
    final ArrayList<Dirt> dirts = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<NormalWall> walls = new ArrayList<>();
    final ArrayList<Boulder> boulders = new ArrayList<>();
    final ArrayList<Diamond> diamonds = new ArrayList<>();
    final ArrayList<Butterfly> butterflies = new ArrayList<>();
    final ArrayList<Firefly> fireflies = new ArrayList<>();
    final ArrayList<Frog> frogs = new ArrayList<>();
    final ArrayList<Amoeba> amoebas = new ArrayList<>();
    private  Player player;
    private Exit exit;


    /**
     * Constructs a GridManager with a grid template.
     * Initializes the grid based on the provided template and categorizes elements into lists.
     *
     * @param gridTemplate the 2D array representing the initial grid setup
     */
    public GridManager(int[][] gridTemplate) {
        this.elementGrid = new Element[gridTemplate.length][gridTemplate[0].length];
        initializeGrid(gridTemplate);

    }

    /**
     * Initializes the player's position based on the grid template.
     * Searches for the Player element in the grid and sets its initial location.
     *
     * @param gridTemplate the 2D array representing the grid layout
     */
    public void initializePlayer(int[][] gridTemplate) {
        Element[][] elementGrid = this.getElementGrid();
        for (int row = 0; row < gridTemplate.length; row++) {
            for (int col = 0; col < gridTemplate[row].length; col++) {
                if (elementGrid[row][col] instanceof Player) {
                    player.setRow(row);
                    player.setColumn(col);
                    break;
                }
            }
        }
    }

    /**
     * Initializes the grid and categorizes elements into appropriate lists.
     * Clears any existing lists before initializing.
     *
     * @param gridTemplate the 2D array representing the initial grid setup
     */
    public void reinitializeGrid(int[][] gridTemplate) {
        GameController.waitingForExplosion = false;
        GameController.waitingForExplosionAfterMath = false;

        initializePlayer(gridTemplate);
        Exit.toggleFalseExitExists();
        getBoulders().clear();
        getDiamonds().clear();
        getFrogs().clear();
        getAmoebas().clear();
        getButterflies().clear();
        getFireflies().clear();
        GameController.gameStart();

        // Clear specific references
        player.resetDiamondCountStatus();
        player.resetKeyInventory();

        for (int row = 0; row < gridTemplate.length; row++) {
            for (int col = 0; col < gridTemplate[row].length; col++) {
                Element element = createElement(this, gridTemplate[row][col], row, col, false);
                elementGrid[row][col] = element;
                addToList(element);
            }
        }

    }

    /**
     * Initializes the grid and categorizes elements into appropriate lists.
     * Clears any existing lists before initializing.
     *
     * @param gridTemplate the 2D array representing the initial grid setup
     */
    public void initializeGrid(int[][] gridTemplate) {
        // Clear all memory of existing lists
        Exit.toggleFalseExitExists();
        getBoulders().clear();
        getDiamonds().clear();
        getFrogs().clear();
        getAmoebas().clear();
        getButterflies().clear();
        getFireflies().clear();
        GameController.gameStart();
        // follows LeftEdge is true by default
        for (int row = 0; row < gridTemplate.length; row++) {
            for (int col = 0; col < gridTemplate[row].length; col++) {
                Element element = createElement(this,gridTemplate[row][col], row, col, false);
                elementGrid[row][col] = element;
                addToList(element);
            }
        }
    }
    /**
     * Creates an element based on the provided code and its position in the grid.
     *
     * @param gridManager
     * @param code        the integer code representing the type of element
     * @param row         the row position of the element
     * @param col         the column position of the element
     * @return the created Element object
     * @throws IllegalArgumentException if the code does not correspond to a known element type
     */
    private Element createElement(GridManager gridManager, int code, int row, int col, boolean followsLeftEdge) {
        return switch (code) {
            case 0 -> new Path(row, col);
            case 1 -> new Dirt(row, col);
            case 2 -> player = new Player(row, col);
            case 3 -> new NormalWall(row, col);
            case 4 -> new Boulder(row, col);
            case 5 -> new Frog(row, col);
            case 6 -> new Amoeba(row, col);
            case 7 -> new Diamond(row, col);
            case 8 -> new TitaniumWall(row, col);
            case 9 -> new MagicWall(row, col);
            case 10 -> new LockedDoor(row, col, KeyColour.RED);
            case 11 -> new Key(row, col, KeyColour.RED);
            case 12 -> new LockedDoor(row, col, KeyColour.GREEN);
            case 13 -> new Key(row, col, KeyColour.GREEN);
            case 14 -> new LockedDoor(row, col, KeyColour.YELLOW);
            case 15 -> new Key(row, col, KeyColour.YELLOW);
            case 16 -> new LockedDoor(row, col, KeyColour.BLUE);
            case 17 -> new Key(row, col, KeyColour.BLUE);
            case 18 -> exit = new Exit(row, col);
            case 19 -> new Butterfly(row, col, followsLeftEdge);
            case 20 -> new Firefly(row, col, followsLeftEdge);


            default -> throw new IllegalArgumentException("Unknown element code: " + code);
        };
    }

    /**
     * Adds an element to its corresponding list based on its type.
     *
     * @param element the Element to be added
     */
    public void addToList(Element element) {
        if (element instanceof Path path) {
            paths.add(path);
        } else if (element instanceof Dirt dirt) {
            dirts.add(dirt);
        } else if (element instanceof Player player) {
            players.add(player);
        } else if (element instanceof NormalWall wall) {
            walls.add(wall);
        } else if (element instanceof Boulder boulder) {
            boulders.add(boulder);
        } else if (element instanceof Butterfly butterfly) {
            butterflies.add(butterfly);
        } else if (element instanceof Firefly firefly) {
            fireflies.add(firefly);
        } else if (element instanceof Frog frog) {
            frogs.add(frog);
        } else if (element instanceof Amoeba amoeba) {
            amoebas.add(amoeba);
        } else if (element instanceof Diamond diamond) {
            diamonds.add(diamond);
        }
    }

    /**
     * Removes an element from its corresponding list based on its type.
     * Explodes if it is an enemy
     * @param element the Element to be removed
     */
    public void removeFromList(Element element) {
        if (element instanceof Path path) {
            paths.remove(path);
        } else if (element instanceof Dirt dirt) {
            dirts.remove(dirt);
        } else if (element instanceof Player player) {
            players.remove(player);
            System.out.println("Player removed");
            GameController.gameOver();
        } else if (element instanceof NormalWall wall) {
            walls.remove(wall);
        } else if (element instanceof Boulder boulder) {
            boulders.remove(boulder);
            System.out.println("Boulder removed");
        } else if (element instanceof Frog frog) {
            frogs.remove(frog);
            System.out.println("Frog removed");
            GameController.applyExplosion(element.row, element.column,Frog.dropDiamond );
        } else if (element instanceof Amoeba amoeba) {
            amoebas.remove(amoeba);
        } else if (element instanceof Diamond diamond) {
            diamonds.remove(diamond);
            System.out.println("Diamond removed");
        } else if (element instanceof Butterfly butterfly) {
            butterflies.remove(butterfly);
            System.out.println("Butterfly removed");
            GameController.applyExplosion(element.row, element.column, Butterfly.dropDiamond);
        } else if (element instanceof Firefly firefly) {
            fireflies.remove(firefly);
            System.out.println("Firefly removed");
            GameController.applyExplosion(element.row, element.column, Firefly.dropDiamond);
        }
    }

    /**
     * Removes an element from its corresponding list based on its type.
     * Objects here only get destroyed, thus avoiding a chain reaction.
     * @param element the Element to be removed
     */
    public void explosionRemoveFromList(Element element) {
        if (element instanceof Path path) {
            paths.remove(path);
        } else if (element instanceof Dirt dirt) {
            dirts.remove(dirt);
            System.out.println("Explosion Dirt removed");
        } else if (element instanceof Player player) {
            players.remove(player);
            GameController.gameOver();
            System.out.println("Explosion Player removed");
        } else if (element instanceof NormalWall wall) {
            walls.remove(wall);
            System.out.println("Explosion NormalWall removed");
        } else if (element instanceof Boulder boulder) {
            boulders.remove(boulder);
            System.out.println("Explosion Boulder removed");
        } else if (element instanceof Frog frog) {
            frogs.remove(frog);
            System.out.println("Explosion Frog removed");
        } else if (element instanceof Amoeba amoeba) {
            amoebas.remove(amoeba);
        } else if (element instanceof Diamond diamond) {
            diamonds.remove(diamond);
            System.out.println("Explosion Diamond removed");
        } else if (element instanceof Butterfly butterfly) {
            butterflies.remove(butterfly);
            System.out.println("Explosion Butterfly removed");
        } else if (element instanceof Firefly firefly) {
            fireflies.remove(firefly);
            System.out.println("Explosion Firefly removed");
        }
    }
    /**
     * Retrieves the 2D array of elements in the grid.
     *
     * @return the element grid
     */
    public Element[][] getElementGrid() {
        return elementGrid;
    }

    /**
     * Retrieves the element at the specified position in the grid.
     *
     * @param row the row position of the element
     * @param col the column position of the element
     * @return the Element at the specified position
     */
    public Element getElement(int row, int col) {
        return elementGrid[row][col];
    }

    /**
     * Sets an element at the specified position in the grid.
     *
     * @param row     the row position of the element
     * @param col     the column position of the element
     * @param element the Element to set at the specified position
     */
    public void setElement(int row, int col, Element element) {
        elementGrid[row][col] = element;
    }

    /**
     * Removes an element from the grid at the specified position.
     * Replaces the removed element with a Path and removes it from its corresponding list.
     *
     * @param row the row position of the element to remove
     * @param col the column position of the element to remove
     */
    public void removeElement(int row, int col) {
//        removeFromList(elementGrid[row][col]);
        Path p = new Path(row, col);
        elementGrid[row][col] = p;
    }

    /**
     * Retrieves the list of Boulder elements in the grid.
     *
     * @return the ArrayList of Boulder elements
     */
    public ArrayList<Boulder> getBoulders() {
        return boulders;
    }

    /**
     * Retrieves the list of Diamond elements in the grid.
     *
     * @return the ArrayList of Boulder elements
     */
    public ArrayList<Diamond> getDiamonds() {
        return diamonds;
    }

    public ArrayList<Firefly> getFireflies() {
        return fireflies;
    }

    public ArrayList<Butterfly> getButterflies() {
        return butterflies;
    }

    public ArrayList<Frog> getFrogs() {
        return frogs;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Amoeba> getAmoebas() {
        return amoebas;
    }


    // built for debugging purpose
    public void printGridState() {
        for (int row = 0; row < elementGrid.length; row++) {
            for (int col = 0; col < elementGrid[row].length; col++) {
                System.out.print(elementGrid[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------------------------------------------");
    }
}
