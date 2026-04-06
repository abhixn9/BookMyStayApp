import java.util.*;

// Room Domain Model
class Room {
    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }
}

// Inventory (State Holder)
class Inventory {
    private Map<String, Integer> roomAvailability;

    public Inventory() {
        roomAvailability = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        roomAvailability.put(type, count);
    }

    // Read-only access
    public int getAvailability(String type) {
        return roomAvailability.getOrDefault(type, 0);
    }

    public Set<String> getAllRoomTypes() {
        return roomAvailability.keySet();
    }
}

// Search Service (Read-only logic)
class SearchService {
    private Inventory inventory;
    private Map<String, Room> roomDetails;

    public SearchService(Inventory inventory, Map<String, Room> roomDetails) {
        this.inventory = inventory;
        this.roomDetails = roomDetails;
    }

    public void searchAvailableRooms() {
        System.out.println("Available Rooms:\n");

        for (String type : inventory.getAllRoomTypes()) {

            int available = inventory.getAvailability(type);

            // Validation: only show available rooms
            if (available > 0 && roomDetails.containsKey(type)) {

                Room room = roomDetails.get(type);

                System.out.println("Room Type: " + room.getType());
                System.out.println("Price: " + room.getPrice());
                System.out.println("Amenities: " + room.getAmenities());
                System.out.println("Available Rooms: " + available);
                System.out.println("----------------------------");
            }
        }
    }
}

// Main Class
public class UseCase4RoomSearch {
    public static void main(String[] args) {

        // Inventory setup
        Inventory inventory = new Inventory();
        inventory.addRoom("Single", 5);
        inventory.addRoom("Double", 0);
        inventory.addRoom("Suite", 3);

        // Room details setup
        Map<String, Room> roomDetails = new HashMap<>();
        roomDetails.put("Single", new Room("Single", 2000, "WiFi, TV"));
        roomDetails.put("Double", new Room("Double", 3500, "WiFi, TV, AC"));
        roomDetails.put("Suite", new Room("Suite", 5000, "WiFi, TV, AC, Mini Bar"));

        // Search service
        SearchService searchService = new SearchService(inventory, roomDetails);

        // Guest searches rooms
        searchService.searchAvailableRooms();
    }
}