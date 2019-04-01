package e2098697;

/**
 *
 *
 */
public enum ZombieType {
    SLOW,
    REGULAR,
    FAST;

    public static double getSpeed(ZombieType type) {
        if (SLOW == type) {
            return 2.0;
        }
        else if (REGULAR == type) {
            return 5.0;
        }
        else if (FAST == type) {
            return 20.0;
        }

        return 0;
    }

    public static double getCollisionRange(ZombieType type) {
        if (SLOW == type) {
            return 1.0;
        }
        else if (REGULAR == type) {
            return 2.0;
        }
        else if (FAST == type) {
            return 2.0;
        }

        return 0;
    }

    public static double getDetectionRange(ZombieType type) {
        if (SLOW == type) {
            return 40.0;
        }
        else if (REGULAR == type) {
            return 20.0;
        }
        else if (FAST == type) {
            return 20.0;
        }

        return 0;
    }
}
