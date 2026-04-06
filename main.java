import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
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

    public void increment(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Cancellation Service
class CancellationService {

    // Track active bookings
    private Map<String, Reservation> activeBookings;

    // Stack for rollback (LIFO)
    private Stack<String> releasedRoomIds;

    private InventoryService inventory;

    public CancellationService(InventoryService inventory) {
        this.inventory = inventory;
        this.activeBookings = new HashMap<>();
        this.releasedRoomIds = new Stack<>();
    }

    // Add booking (simulate confirmed booking)
    public void addBooking(Reservation reservation) {
        activeBookings.put(reservation.getReservationId(), reservation);
    }

    // Cancel booking
    public void cancelBooking(String reservationId) {

        if (!activeBookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found -> " + reservationId);
            return;
        }

        Reservation res = activeBookings.get(reservationId);

        // Push room ID to rollback stack
        releasedRoomIds.push(reservationId);

        // Restore inventory
        inventory.increment(res.getRoomType());

        // Remove booking
        activeBookings.remove(reservationId);

        System.out.println("Cancellation Successful for: " + reservationId);
    }

    // Show rollback stack
    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (LIFO): " + releasedRoomIds);
    }
}

// Main class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();

        // Initial inventory
        inventory.addRoom("Single", 1);
        inventory.addRoom("Double", 0);

        CancellationService cancellationService = new CancellationService(inventory);

        // Simulate confirmed bookings
        cancellationService.addBooking(new Reservation("Single-1", "Single"));
        cancellationService.addBooking(new Reservation("Double-1", "Double"));

        // Cancel bookings
        cancellationService.cancelBooking("Single-1");
        cancellationService.cancelBooking("Double-1");

        // Invalid cancellation
        cancellationService.cancelBooking("Suite-1");

        // Show rollback stack
        cancellationService.displayRollbackStack();

        // Show updated inventory
        System.out.println();
        inventory.displayInventory();
    }
}