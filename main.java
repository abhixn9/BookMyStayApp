import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String toString() {
        return guestName + " | " + roomType + " | " + roomId;
    }
}

// Inventory Service (Serializable)
class InventoryService implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void displayInventory() {
        System.out.println("Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "booking_data.ser";

    // Save state
    public static void save(Object data) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(data);
            System.out.println("Data saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load state
    public static Object load() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("Data loaded successfully.");
            return in.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return null;
    }
}

// Wrapper class for persistence
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    public InventoryService inventory;
    public List<Reservation> bookings;

    public SystemState(InventoryService inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Main class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        // Try loading previous state
        SystemState state = (SystemState) PersistenceService.load();

        InventoryService inventory;
        List<Reservation> bookings;

        if (state == null) {
            // Fresh start
            inventory = new InventoryService();
            bookings = new ArrayList<>();

            inventory.addRoom("Single", 2);
            inventory.addRoom("Double", 1);

            bookings.add(new Reservation("Alice", "Single", "Single-1"));
            bookings.add(new Reservation("Bob", "Double", "Double-1"));

        } else {
            // Restore state
            inventory = state.inventory;
            bookings = state.bookings;
        }

        // Display recovered data
        System.out.println("\nRecovered Data:");
        inventory.displayInventory();

        System.out.println("\nBooking History:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }

        // Save state before exit
        SystemState newState = new SystemState(inventory, bookings);
        PersistenceService.save(newState);
    }
}