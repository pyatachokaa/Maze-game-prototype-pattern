import java.util.HashMap;
import java.util.Map;

enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST
}

class Maze {
    Map<Integer, Room> rooms = new HashMap<>();

    public void addRoom(Room r) {
        rooms.put(r.getRoomNo(), r);
    }

    public Room roomNo(int r) {
        return rooms.get(r);
    }

    public Room cloneRoom(int r) throws CloneNotSupportedException {
        Room originalRoom = rooms.get(r);
        return (originalRoom != null) ? originalRoom.clone() : null;
    }

    public void printMaze() {
        for (Room room : rooms.values()) {
            System.out.println("Room: " + room.getRoomNo());
            System.out.println("NORTH: " + getWallType(room.getSide(Direction.NORTH)));
            System.out.println("EAST: " + getWallType(room.getSide(Direction.EAST)));
            System.out.println("SOUTH: " + getWallType(room.getSide(Direction.SOUTH)));
            System.out.println("WEST: " + getWallType(room.getSide(Direction.WEST)));
            System.out.println();
        }
    }

    private String getWallType(Wall wall) {
        if (wall instanceof DoorWall) {
            return "Door";
        } else {
            return "Wall";
        }
    }
}


class Room implements Cloneable {
    private Map<Direction, Wall> sides = new HashMap<>();
    private int roomNo;

    public Room(int roomNo) {
        this.roomNo = roomNo;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public Wall getSide(Direction direction) {
        return sides.get(direction);
    }

    public void setSide(Direction direction, Wall wall) {
        sides.put(direction, wall);
    }

    @Override
    public Room clone() throws CloneNotSupportedException {
        Room clonedRoom = (Room) super.clone();
        // Deep copy of walls
        clonedRoom.sides = new HashMap<>(sides);
        return clonedRoom;
    }
}

class Wall implements Cloneable {
    // This class can have more properties or methods if needed

    @Override
    public Wall clone() throws CloneNotSupportedException {
        return (Wall) super.clone();
    }
}

class DoorWall extends Wall {
    private Room r1;
    private Room r2;
    private boolean isOpen;

    public DoorWall(Room r1, Room r2) {
        this.r1 = r1;
        this.r2 = r2;
        this.isOpen = false;
    }

    @Override
    public DoorWall clone() throws CloneNotSupportedException {
        return (DoorWall) super.clone();
    }
}

class MazeGame {
    public static void main(String[] argv) throws CloneNotSupportedException {
        Maze aMaze = createMaze();
        Maze clonedMaze = cloneMaze(aMaze);

        System.out.println("Original Maze:");
        aMaze.printMaze();

        System.out.println("\nCloned Maze:");
        clonedMaze.printMaze();
    }

    private static Maze createMaze() {
        Maze aMaze = new Maze();
        Room r1 = new Room(1);
        Room r2 = new Room(2);
        DoorWall d = new DoorWall(r1, r2);

        aMaze.addRoom(r1);
        aMaze.addRoom(r2);

        r1.setSide(Direction.NORTH, d);
        r1.setSide(Direction.EAST, new Wall());
        r1.setSide(Direction.SOUTH, new Wall());
        r1.setSide(Direction.WEST, new Wall());
        r2.setSide(Direction.NORTH, new Wall());
        r2.setSide(Direction.EAST, new Wall());
        r2.setSide(Direction.SOUTH, d);
        r2.setSide(Direction.WEST, new Wall());

        return aMaze;
    }

    private static Maze cloneMaze(Maze maze) throws CloneNotSupportedException {
        Maze clonedMaze = new Maze();
        for (Room room : maze.rooms.values()) {
            clonedMaze.addRoom(room.clone());
        }
        return clonedMaze;
    }
}

