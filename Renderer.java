import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer {
    private static final int GRID_CELL_WIDTH = 50;
    private static final int GRID_CELL_HEIGHT = 50;

    public void draw(GraphicsContext gc, Element[][] elementGrid) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Set background color
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Draw all elements
        for (int row = 0; row < elementGrid.length; row++) {
            for (int col = 0; col < elementGrid[row].length; col++) {
                Element element = elementGrid[row][col];
                if (element.getImage() != null) {
                    gc.drawImage(element.getImage(), col * GRID_CELL_WIDTH, row * GRID_CELL_HEIGHT);
                }
            }
        }
    }
}
