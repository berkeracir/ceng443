import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Maze extends UnicastRemoteObject implements IMaze {
    private int height, width;
    private MazeObject[][] mazeArray;

    public Maze() throws RemoteException {

    }

    public void create(int height, int width) throws RemoteException {
        this.height = height;
        this.width = width;
        this.mazeArray = new MazeObject[height][width];
    }

    public MazeObject getObject(Position position) throws RemoteException {
        int x = position.getX();
        int y = position.getY();

        if (isValidPosition(position)) {
            return mazeArray[y][x];
        }
        else {
            return null;
        }
    }

    public boolean createObject(Position position, MazeObjectType type) throws RemoteException {
        int x = position.getX();
        int y = position.getY();

        if (isValidPosition(position)) {

        }
        else {
            return false;
        }
    }

    public boolean deleteObject(Position position) throws RemoteException {
        return false;
    }

    public Agent[] getAgents() throws RemoteException {
        return new Agent[0];
    }

    public boolean moveAgent(int id, Position position) throws RemoteException {
        return false;
    }

    public String print() throws RemoteException {
        return null;
    }

    private boolean isValidPosition(Position position) {
        int x = position.getX();
        int y = position.getY();

        if (x < 0 || x > this.width || y < 0 || y > this.height) {
            return false;
        }
        else {
            return true;
        }
    }
}
