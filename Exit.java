import javafx.scene.image.Image;
public class Exit extends Tile {
    private static boolean exitExists = false; //allows us to keep track of whether an exit has already been created

    public Exit(int row, int column) {
        super(row, column);
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
    public static void toggleFalseExitExists() {
        exitExists = false;
    }
    // Notifies the player that they have completed the level
    public void announceLevelWin() {
        System.out.println("Level Completed!");
        Main.stopTimer();
    }
    // Method to unlock the Exit so it can be entered
    public void unlock() {
        this.canBeEntered = true;
        System.out.println("Exit Unlocked!");
    }

    public void lock(){
        this.canBeEntered = false;
        System.out.println("Exit Locked!");
    }
    public boolean isLocked() {
        return canBeEntered;
    }

    @Override
    public String toString() {
        return super.toString() + " Unlocked: " + canBeEntered;
    }
}
