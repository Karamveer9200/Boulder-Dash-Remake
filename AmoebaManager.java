import java.util.ArrayList;
import java.util.List;

public class AmoebaManager {
    private static final List<AmoebaGroup> amoebaGroups = new ArrayList<>(); // Initialize properly

    public static boolean isEmpty() {
        return amoebaGroups.isEmpty();
    }

    public static void addAmoebaGroup(AmoebaGroup group) {
        amoebaGroups.add(group);
    }


    public static void clearGroups() {
        amoebaGroups.clear();
    }

    public static void updateAll(GridManager gridManager) {

        for (AmoebaGroup group : gridManager.getAmoebaGroups()) {
            group.spread(gridManager);
        }
    }
}
