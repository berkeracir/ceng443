/**
 *
 * @author Berker Acir
 */
public class Smelter implements Runnable {
    private static int ID = 1;
    private final int id;
    private final int interval;
    //private final int capacity;
    private final IngotType ingotType;
    private int totalIngot;
    private final Storage storage;
    private boolean alive;

    public Smelter(int interval, int capacity, IngotType type, int totalIngot) {
        this.id = this.ID++;
        this.interval = interval;
        //this.capacity = capacity;
        this.ingotType = type;
        this.totalIngot = totalIngot;
        this.storage = new Storage(capacity);
        this.alive = true;
    }

    @Override
    public void run() {
        HW2Logger.WriteOutput(id, 0, 0, Action.SMELTER_CREATED);

        while (totalIngot > 0) {
            try {
                WaitCanProduce();
            } catch (InterruptedException ex) {}
            HW2Logger.WriteOutput(id, 0, 0, Action.SMELTER_STARTED);
            Utility.threadSleep(interval);
            IngotProduced();
            HW2Logger.WriteOutput(id, 0, 0, Action.SMELTER_FINISHED);
            Utility.threadSleep(interval);
        }

        try {
            SmelterStopped();
        } catch (InterruptedException ex) {}
        HW2Logger.WriteOutput(id, 0, 0, Action.SMELTER_STOPPED);
    }

    // Wait until a storage space is cleared by a transporter and reserve a storage space for the next ingot before
    // production.
    private void WaitCanProduce() throws InterruptedException {
        while (storage.isFull()) {
            synchronized (storage) {
                storage.wait(100);
            }
        }
    }

    // Informs available transporters that there is available ingots in the smelter’s storage.
    private void IngotProduced() {
        synchronized (storage) {
            totalIngot--;
            storage.dropIngot();
            storage.notifyAll();
        }
    }

    // Signals the transporters waiting on the smelter that this smelter has stopped producing. Transporters can keep
    // loading remaining ingots in the storage. If there is not available storage for all waiting transporters, extra
    // transporters quit.
    private void SmelterStopped() throws InterruptedException{
        alive = false;

        while (!storage.isEmpty()) {
            synchronized (storage) {
                storage.notifyAll();
            }
            Utility.threadSleep(interval);
        }
    }

    public int getTotalIngot() {
        return totalIngot;
    }

    public Storage getStorage() {
        return storage;
    }

    public boolean isAlive() {
        return alive;
    }

    public IngotType getIngotType() {
        return ingotType;
    }
}