import java.util.ArrayList;

/**
 * The GridManager is responsible for managing the grid of elements in the game.
 * It initializes the grid based on a template, provides access to individual elements,
 * manages lists of specific element types, and supports adding, removing, and updating elements.
 */
public class GridManager {
    private final Element[][] elementGrid;
    private final ArrayList<Path> paths = new ArrayList<>();
    private final ArrayList<Dirt> dirts = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<NormalWall> walls = new ArrayList<>();
    final ArrayList<Boulder> boulders = new ArrayList<>();

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
     * Initializes the grid and categorizes elements into appropriate lists.
     * Clears any existing lists before initializing.
     *
     * @param gridTemplate the 2D array representing the initial grid setup
     */
    public void initializeGrid(int[][] gridTemplate) {
        // Clear all memory of existing lists
        getBoulders().clear();

        for (int row = 0; row < gridTemplate.length; row++) {
            for (int col = 0; col < gridTemplate[row].length; col++) {
                Element element = createElement(gridTemplate[row][col], row, col);
                elementGrid[row][col] = element;
                addToList(element);
            }
        }
    }

    /**
     * Creates an element based on the provided code and its position in the grid.
     *
     * @param code the integer code representing the type of element
     * @param row  the row position of the element
     * @param col  the column position of the element
     * @return the created Element object
     * @throws IllegalArgumentException if the code does not correspond to a known element type
     */
    private Element createElement(int code, int row, int col) {
        return switch (code) {
            case 0 -> new Path(row, col);
            case 1 -> new Dirt(row, col);
            case 2 -> new Player(row, col);
            case 3 -> new NormalWall(row, col);
            case 4 -> new Boulder(row, col);
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
        }
    }

    /**
     * Removes an element from its corresponding list based on its type.
     *
     * @param element the Element to be removed
     */
    public void removeFromList(Element element) {
        if (element instanceof Path path) {
            paths.remove(path);
        } else if (element instanceof Dirt dirt) {
            dirts.remove(dirt);
        } else if (element instanceof Player player) {
            players.remove(player);
        } else if (element instanceof NormalWall wall) {
            walls.remove(wall);
        } else if (element instanceof Boulder boulder) {
            boulders.remove(boulder);
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
        removeFromList(elementGrid[row][col]);
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
}
