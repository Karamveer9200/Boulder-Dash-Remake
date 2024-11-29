import javafx.scene.image.Image;
public class Exit extends Tile {
    private static boolean exitExists = false; //allows us to keep track of whether an exit has already been created

    public Exit(int x, int y) {
        super(x, y);
        // Ensures only one exit can be created per level
        if (exitExists) {
            throw new IllegalStateException("An exit already exists!");
        }
        image = new Image("images/Exit.png");
        canBeEntered = false;  // Initially can't be entered
        canExplode = false;
        name = "Exit";
        exitExists = true;
    }
    // Notifies the player that they have completed the level
    public void announceLevelWin() {
        System.out.println("Level Completed!");
    }
    // Method to unlock the Exit so it can be entered
    public void unlock() {
        this.canBeEntered = true;
        System.out.println("Exit Unlocked!");
    }
}
