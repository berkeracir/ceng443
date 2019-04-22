/**
 *
 * @author Berker Acir
 */
public enum IngotType {
    IRON,
    COPPER,
    UNKONWN;

    public static IngotType getIngotType(int type) {
        switch (type) {
            case 0:
                return IRON;
            case 1:
                return COPPER;
            default:
                return UNKONWN;
        }
    }

    public int ingotRequirement() {
        switch (this) {
            case IRON:
                return 2;
            case COPPER:
                return 3;
            default:
                return -1;
        }
    }
}
