import java.util.*;

// Reservation class
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

// Thread-safe Inventory Service
class InventoryService {
    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    // synchronized critical section
    public synchronized boolean allocateRoom(String type) {
        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void displayInventory() {
        System.out.println("Final Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {

    private Queue<Reservation> queue;
    private InventoryService inventory;

    public BookingProcessor(Queue<Reservation> queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            // synchronized queue access
            synchronized (queue) {
                if (queue.isEmpty()) {
                    break;
                }
                reservation = queue.poll();
            }

            // critical section for allocation
            boolean success = inventory.allocateRoom(reservation.getRoomType());

            if (success) {
                System.out.println(Thread.currentThread().getName()
                        + " booked for " + reservation.getGuestName()
                        + " (" + reservation.getRoomType() + ")");
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " failed for " + reservation.getGuestName()
                        + " (No availability)");
            }
        }
    }
}

// Main class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        // Shared inventory
        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 2);

        // Shared queue
        Queue<Reservation> queue = new LinkedList<>();

        // Simulate concurrent booking requests
        queue.offer(new Reservation("Alice", "Single"));
        queue.offer(new Reservation("Bob", "Single"));
        queue.offer(new Reservation("Charlie", "Single")); // should fail

        // Create multiple threads
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        // Start threads
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory
        System.out.println();
        inventory.displayInventory();
    }
}