import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Maze extends UnicastRemoteObject implements IMaze {
    private int height, width;
    private MazeObject[][] mazeArray;
    private int agentID;
    private int agentCount;
    //private final int id;

    //public Maze(int id) throws RemoteException {
        //this.id = id;
    public Maze() throws RemoteException {
        this.agentID = 0;
        this.agentCount = 0;
    }

    public void create(int height, int width) throws RemoteException {
        DEBUG(height + "x" + width + " Maze created");

        this.height = height;
        this.width = width;
        this.mazeArray = new MazeObject[height][width];
    }

    public MazeObject getObject(Position position) throws RemoteException {
        int x = position.getX();
        int y = position.getY();

        if (isValidPosition(position)) {
            //DEBUG("Get the object at position " + position);
            return mazeArray[y][x];
        }
        else {
            //DEBUG("Couldn't get the object at position " + position);
            return null;
        }
    }

    public boolean createObject(Position position, MazeObjectType type) throws RemoteException {
        int x = position.getX();
        int y = position.getY();

        if (isValidPosition(position) && isEmpty(position)) {
            if (type == MazeObjectType.AGENT) {
                agentCount++;
                mazeArray[y][x] = new Agent(position, agentID++);

                DEBUG("Agent created at " + position);
            }
            else {
                mazeArray[y][x] = new MazeObject(position, type);

                DEBUG("Maze Object(" + type + ") created at " + position);
            }

            return true;
        }
        else {
            DEBUG("Maze Object(" + type + ") NOT created at " + position);

            return false;
        }
    }

    public boolean deleteObject(Position position) throws RemoteException {
        if (isValidPosition(position) && !isEmpty(position)) {
            MazeObject mazeObject = getObject(position);

            if (mazeObject.getType() == MazeObjectType.AGENT) {
                agentCount--;
            }

            mazeArray[position.getY()][position.getX()] = null;

            DEBUG("Maze Object(" + mazeObject.getType() + ") at " + position + " deleted");

            return true;
        }
        else {
            DEBUG("Maze Object at " + position + " NOT deleted");

            return false;
        }
    }

    public Agent[] getAgents() throws RemoteException {
        Agent[] agents = new Agent[agentCount];

        int index = 0;
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                MazeObject mazeObject = getObject(new Position(x, y));

                if (mazeObject != null && mazeObject.getType() == MazeObjectType.AGENT) {
                    agents[index] = (Agent) mazeObject;
                    index++;
                }
            }
        }

        return agents;
    }

    public boolean moveAgent(int id, Position position) throws RemoteException {
        Agent agent = getAgent(id);

        if (agent == null) {
            DEBUG("Agent with ID " + id + " NOT exist");
            return false;
        }
        else if (isValidPosition(position) && checkDistance(agent.getPosition(), position)) {
            MazeObject mazeObject = getObject(position);

            if (mazeObject == null) {
                DEBUG("Agent with ID " + id + " moved to " + position);
                moveObject(agent, position);

                return true;
            }
            else if (mazeObject.getType() == MazeObjectType.HOLE) {
                DEBUG("Agent with ID " + id + " moved into the hole at " + position);
                deleteObject(agent.getPosition());

                return true;
            }
            else if (mazeObject.getType() == MazeObjectType.WALL || mazeObject.getType() == MazeObjectType.AGENT) {
                DEBUG("Agent with ID " + id + " moved into a wall or an agent at " + position);
                return true;
            }
            else if (mazeObject.getType() == MazeObjectType.GOLD) {
                DEBUG("Agent with ID " + id + " moved to " + position + " and obtained gold");
                moveObject(agent, position);
                agent.setCollectedGold(agent.getCollectedGold() + 1);

                return true;
            }
            else {
                DEBUG("Agent with ID " + id + " NOT moved to " + position);
                return false;
            }
        }
        else {
            DEBUG("Invalid Position to move: " + agent.getPosition() + " -> " + position);
            return false;
        }
    }

    public String print() throws RemoteException {
        StringBuilder s = new StringBuilder();

        s.append("+");
        for (int i=0; i<width; i++)
            s.append("-");
        s.append("+\n");

        for (int y=0; y<height; y++) {
            s.append("|");
            for (int x=0; x<width; x++) {
                MazeObject mazeObject = getObject(new Position(x, y));

                if (mazeObject != null) {
                    s.append(mazeArray[y][x]);
                }
                else {
                    s.append(" ");
                }
            }
            s.append("|\n");
        }

        s.append("+");
        for (int i=0; i<width; i++)
            s.append("-");
        s.append("+");

        return s.toString();
    }

    private boolean isValidPosition(Position position) throws RemoteException {
        int x = position.getX();
        int y = position.getY();

        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isEmpty(Position position) throws RemoteException {
        MazeObject mazeObject = getObject(position);

        if (mazeObject == null) {
            return true;
        } else {
            return false;
        }
    }

    private Agent getAgent(int id) throws RemoteException {
        Agent[] agents = getAgents();

        for (int i=0; i<agents.length; i++) {
            if (agents[i].getId() == id) {
                return agents[i];
            }
        }

        return null;
    }

    private boolean checkDistance(Position pos1, Position pos2) {
        int x1 = pos1.getX();
        int y1 = pos1.getY();
        int x2 = pos2.getX();
        int y2 = pos2.getY();

        if (Math.abs(x1-x2) + Math.abs(y1-y2) <= 1) {
            return true;
        }
        else {
            return false;
        }
    }

    private void moveObject(MazeObject mazeObject, Position position) {
        int x_obj = mazeObject.getPosition().getX();
        int y_obj = mazeObject.getPosition().getY();
        int x = position.getX();
        int y = position.getY();

        mazeArray[y_obj][x_obj] = null;
        mazeArray[y][x] = mazeObject;
        mazeObject.setPosition(position);
    }

    private void DEBUG(String s) {
        if (Debug.DEBUG_ENABLED)
            System.out.println("[MAZE]\t\t" + s);
    }
}
