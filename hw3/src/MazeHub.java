import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class MazeHub extends UnicastRemoteObject implements IMazeHub {
    //private int mazeID;
    private ArrayList<IMaze> mazes;

    public MazeHub() throws RemoteException {
        this.mazes = new ArrayList<>();
        //this.mazeID = 0;
    }

    public void createMaze(int width, int height) throws RemoteException {
        DEBUG(height + "x" + width + " Maze created");

        IMaze maze = new Maze();
        maze.create(height, width);
        mazes.add(maze);
    }

    public IMaze getMaze(int index) throws RemoteException {
        if (index < 0 || index > mazes.size() - 1) {
            DEBUG("Couldn't get the maze at index " + index);
            return null;
        }
        else {
            DEBUG("Get the maze at index " + index);
            return mazes.get(index);
        }
    }

    public boolean removeMaze(int index) throws RemoteException {
        if (index < 0 || index > mazes.size() - 1) {
            DEBUG("Couldn't remove the maze at index " + index);
            return false;
        }
        else {
            DEBUG("Remove the maze at index " + index);
            mazes.remove(index);
            return true;
        }
    }

    private void DEBUG(String s) {
        if (Debug.DEBUG_ENABLED)
            System.out.println("[MAZEHUB]\t" + s);
    }
}
