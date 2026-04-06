import java.util.LinkedList;
import java.util.Queue;

// Reservation class (represents booking request)
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

// Booking Request Queue (FIFO)
class BookingQueue {
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request (enqueue)
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added: "
                + reservation.getGuestName() + " -> "
                + reservation.getRoomType());
    }

    // View all requests in order
    public void displayQueue() {
        System.out.println("\nBooking Requests in Queue:");

        for (Reservation r : queue) {
            System.out.println(
                    "Guest: " + r.getGuestName() +
                            " | Room Type: " + r.getRoomType()
            );
        }
    }
}

// Main class
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        // Initialize queue
        BookingQueue bookingQueue = new BookingQueue();

        // Simulate booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single"));
        bookingQueue.addRequest(new Reservation("Bob", "Double"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite"));

        // Display queue (FIFO order)
        bookingQueue.displayQueue();
    }
}