import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void decrement(String type) throws InvalidBookingException {
        if (!inventory.containsKey(type)) {
            throw new InvalidBookingException("Invalid Room Type: " + type);
        }

        int current = inventory.get(type);

        if (current <= 0) {
            throw new InvalidBookingException("No rooms available for: " + type);
        }

        inventory.put(type, current - 1);
    }
}

// Validator class
class BookingValidator {

    public static void validateRoomType(String type, InventoryService inventory)
            throws InvalidBookingException {

        if (inventory.getAvailability(type) == -1) {
            throw new InvalidBookingException("Room type does not exist: " + type);
        }
    }

    public static void validateAvailability(String type, InventoryService inventory)
            throws InvalidBookingException {

        if (inventory.getAvailability(type) <= 0) {
            throw new InvalidBookingException("Room not available: " + type);
        }
    }
}

// Booking Service
class BookingService {

    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(String guestName, String roomType) {

        try {
            // Validation (fail-fast)
            BookingValidator.validateRoomType(roomType, inventory);
            BookingValidator.validateAvailability(roomType, inventory);

            // Allocation (safe)
            inventory.decrement(roomType);

            System.out.println("Booking Successful for " + guestName
                    + " | Room Type: " + roomType);

        } catch (InvalidBookingException e) {
            // Graceful error handling
            System.out.println("Booking Failed for " + guestName
                    + " | Reason: " + e.getMessage());
        }
    }
}

// Main class
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();

        // Setup inventory
        inventory.addRoom("Single", 1);
        inventory.addRoom("Double", 0);

        BookingService bookingService = new BookingService(inventory);

        // Valid booking
        bookingService.bookRoom("Alice", "Single");

        // Invalid room type
        bookingService.bookRoom("Bob", "Suite");

        // No availability
        bookingService.bookRoom("Charlie", "Double");

        // Again booking same Single (now unavailable)
        bookingService.bookRoom("David", "Single");
    }
}