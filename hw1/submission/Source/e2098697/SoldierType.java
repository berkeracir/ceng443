package e2098697;

/**
 *
 *
 */
public enum SoldierType {
    REGULAR,
    COMMANDO,
    SNIPER;

    /**
     * Returns speed of a soldier with a given soldier type
     */
    public static double getSpeed(SoldierType type) {
        if (REGULAR == type) {
            return 5.0;
        }
        else if (COMMANDO == type) {
            return 10.0;
        }
        else if (SNIPER == type) {
            return 2.0;
        }

        return 0;
    }

    /**
     * Returns collison range of a soldier with a given soldier type
     */
    public static double getCollisionRange(SoldierType type) {
        if (REGULAR == type) {
            return 2.0;
        }
        else if (COMMANDO == type) {
            return 2.0;
        }
        else if (SNIPER == type) {
            return 5.0;
        }

        return 0;
    }

    /**
     * Returns shooting range of a soldier with a given soldier type
     */
    public static double getShootingRange(SoldierType type) {
        if (REGULAR == type) {
            return 20.0;
        }
        else if (COMMANDO == type) {
            return 10.0;
        }
        else if (SNIPER == type) {
            return 40.0;
        }

        return 0;
    }
}
