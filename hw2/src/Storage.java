/**
 *
 * @author Berker Acir
 */
public class Storage {
    private static int ID = 0;
    private int id;
    private int ingotCount;
    private final int capacity;

    Storage(int capacity) {
        this.capacity = capacity;
        this.ingotCount = 0;
        this.id = ID++;
    }

     void takeIngot() {
        if (ingotCount > 0) {
            ingotCount--;
            //System.out.println("Storage["+ id + "] " + "Ingot taken: " + this);
        }
    }

    void takeIngot(int count) {
        if (ingotCount > count-1) {
            ingotCount -= count;
            //System.out.println("Storage["+ id + "] " + "Ingot taken: " + this);
        }
    }

    void dropIngot() {
        ingotCount++;
        //System.out.println("Storage["+ id + "] " + "Ingot put:   " + this);
    }

    int getIngotCount() {
        return ingotCount;
    }

    boolean isFull() {
        if (ingotCount == capacity) {
            return true;
        }
        return false;
    }

    boolean isEmpty() {
        if (ingotCount == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return ingotCount + " (" + capacity + ")";
    }
}
