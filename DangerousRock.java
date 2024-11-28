public interface DangerousRock {
    /**
     * Handles the falling logic for dangerous rocks.
     *
     * @param gridManager the grid manager to access and update the grid
     */
    void fall(GridManager gridManager);

    void roll(GridManager gridManager);

    /**
     * Determines if the object can fall.
     *
     * @param gridManager the grid manager to access and update the grid
     * @return true if the object can fall, false otherwise
     */
    boolean canFall(GridManager gridManager);

    /**
     * Gains momentum when the object falls.
     */
    void gainMomentum();
}


