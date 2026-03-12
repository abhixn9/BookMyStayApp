import java.util.HashMap;
import java.util.Map;
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay - Hotel Booking App");
        System.out.println("Version 4.1");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();

        RoomSearchService searchService = new RoomSearchService(inventory);
        searchService.searchAvailableRooms();
    }
}


abstract class Room {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq.ft");
        System.out.println("Price     : $" + price);
    }
}


class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 100.0);
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 180.0);
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 500, 350.0);
    }
}

/* ---------------- INVENTORY MANAGEMENT ---------------- */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 7);
        inventory.put("Suite Room", 3);
    }

    public Map<String, Integer> getAllRooms() {
        return inventory;
    }
}

/* ---------------- SEARCH SERVICE ---------------- */

class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms() {

        System.out.println("\n--- Available Rooms ---");

        for (Map.Entry<String, Integer> entry : inventory.getAllRooms().entrySet()) {

            String roomType = entry.getKey();
            int availability = entry.getValue();

            if (availability > 0) {

                Room room;

                if (roomType.equals("Single Room")) {
                    room = new SingleRoom();
                } else if (roomType.equals("Double Room")) {
                    room = new DoubleRoom();
                } else {
                    room = new SuiteRoom();
                }

                room.displayRoomDetails();
                System.out.println("Available Rooms: " + availability);
                System.out.println();
            }
        }
    }
}