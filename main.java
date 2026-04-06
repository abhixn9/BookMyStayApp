import java.util.*;

// Reservation (same as UC5)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
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

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        if (inventory.containsKey(type) && inventory.get(type) > 0) {
            inventory.put(type, inventory.get(type) - 1);
        }
    }
}

// Booking Service (Allocation logic)
class BookingService {

    private Queue<Reservation> queue;
    private InventoryService inventory;

    // Track all allocated room IDs (global uniqueness)
    private Set<String> allocatedRoomIds;

    // Map room type → assigned room IDs
    private Map<String, Set<String>> roomAllocations;

    private int roomCounter = 1;

    public BookingService(Queue<Reservation> queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocations = new HashMap<>();
    }

    // Process booking requests (FIFO)
    public void processBookings() {

        System.out.println("Processing Booking Requests...\n");

        while (!queue.isEmpty()) {

            Reservation r = queue.poll();
            String type = r.getRoomType();

            // Check availability
            if (inventory.getAvailability(type) > 0) {

                // Generate unique room ID
                String roomId = type + "-" + roomCounter++;

                // Ensure uniqueness using Set
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    // Map room type → allocated IDs
                    roomAllocations
                            .computeIfAbsent(type, k -> new HashSet<>())
                            .add(roomId);

                    // Update inventory immediately
                    inventory.decrement(type);

                    // Confirm booking
                    System.out.println("Booking Confirmed:");
                    System.out.println("Guest: " + r.getGuestName());
                    System.out.println("Room Type: " + type);
                    System.out.println("Room ID: " + roomId);
                    System.out.println("--------------------------");

                }
            } else {
                System.out.println("Booking Failed (No Availability): "
                        + r.getGuestName() + " -> " + type);
            }
        }
    }
}

// Main class
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        // Inventory setup
        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Double", 1);
        inventory.addRoom("Suite", 1);

        // Booking queue (FIFO)
        Queue<Reservation> queue = new LinkedList<>();
        queue.offer(new Reservation("Alice", "Single"));
        queue.offer(new Reservation("Bob", "Single"));
        queue.offer(new Reservation("Charlie", "Single")); // will fail
        queue.offer(new Reservation("David", "Suite"));

        // Booking service
        BookingService bookingService = new BookingService(queue, inventory);

        // Process bookings
        bookingService.processBookings();
    }
}