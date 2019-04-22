/**
 *
 * @author Berker Acir
 */
public class Constructor implements Runnable {
    private static int ID = 1;
    private final int id;
    private final int interval;
    private final IngotType ingotType;
    private final Storage storage;
    private int ingotProduct;
    private boolean alive;

    Constructor(int interval, int capacity, IngotType type) {
        this.id = this.ID++;
        this.interval = interval;
        this.ingotType = type;
        this.storage = new Storage(capacity);
        this.ingotProduct = 0;
        this.alive = true;
    }

    @Override
    public void run() {
        HW2Logger.WriteOutput(0, 0, id, Action.CONSTRUCTOR_CREATED);

        while (true) {
            try {
                if (WaitIngots()) {
                    break;
                }
            } catch (InterruptedException ex) {}
            HW2Logger.WriteOutput(0, 0, id, Action.CONSTRUCTOR_STARTED);
            Utility.threadSleep(interval);
            ConstructorProduced();
            HW2Logger.WriteOutput(0, 0, id, Action.CONSTRUCTOR_FINISHED);
        }

        ConstructorStopped();
        HW2Logger.WriteOutput(0, 0, id, Action.CONSTRUCTOR_STOPPED);
    }

    // Wait until required ingots arrive at its storage and reserve the storage spaces until the production is finished.
    // There should be 3 ingots for copper and 2 ingots for iron. If storage of constructor already have required
    // ingots, thread will directly continue, otherwise it will block.
    private boolean WaitIngots() throws InterruptedException {
        int requiredIngotCount = ingotType.ingotRequirement();
        int interruptCount = 0;

        while (storage.getIngotCount() < requiredIngotCount && interruptCount < requiredIngotCount) {
            synchronized (storage) {
                storage.wait(3000/ingotType.ingotRequirement());
            }

            interruptCount++;
        }

        if (storage.getIngotCount() >= requiredIngotCount) {
            return false;
        }

        return true;
    }

    // Signals available transporters that storage spaces have been opened in this constructor.
    private void ConstructorProduced() {
        synchronized (storage) {
            storage.takeIngot(ingotType.ingotRequirement());
            storage.notifyAll();
        }
        ingotProduct++;
    }

    // Marks the constructor out of simulation so that transporters who are delivering this constructor can quit.
    private void ConstructorStopped() {
        synchronized (storage) {
            storage.notifyAll();
        }

        alive = false;
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
