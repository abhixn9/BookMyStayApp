import java.util.HashMap;
import java.util.Map;

// Centralized inventory management class
class RoomInventory {

    // Single source of truth for room availability
    private Map<String, Integer> availabilityMap;

    // Constructor initializes inventory
    public RoomInventory() {
        availabilityMap = new HashMap<>();

        // Register room types with initial availability
        availabilityMap.put("Single", 5);
        availabilityMap.put("Double", 3);
        availabilityMap.put("Suite", 2);
    }

    // Retrieve current availability for a room type
    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    // Controlled update to room availability
    public void updateAvailability(String roomType, int newCount) {
        if (newCount >= 0) {
            availabilityMap.put(roomType, newCount);
        } else {
            System.out.println("Invalid availability count.");
        }
    }

    // Display complete inventory state
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}

// Main class for Use Case 3.0
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        System.out.println();

        // Retrieve availability for a room type
        System.out.println("Available Single Rooms: "
                + inventory.getAvailability("Single"));

        // Update availability through controlled method
        inventory.updateAvailability("Single", 4);

        System.out.println();

        // Display updated inventory
        inventory.displayInventory();
    }
}