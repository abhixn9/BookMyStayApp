import java.util.*;

// Reservation class (simplified)
class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {

    // List maintains insertion order
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {

        System.out.println("Booking History:\n");

        for (Reservation r : reservations) {
            System.out.println(
                    "Guest: " + r.getGuestName() +
                            " | Room Type: " + r.getRoomType() +
                            " | Room ID: " + r.getRoomId()
            );
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        Map<String, Integer> countByRoomType = new HashMap<>();

        for (Reservation r : reservations) {
            countByRoomType.put(
                    r.getRoomType(),
                    countByRoomType.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        System.out.println("\nBooking Summary Report:\n");

        for (Map.Entry<String, Integer> entry : countByRoomType.entrySet()) {
            System.out.println(
                    "Room Type: " + entry.getKey() +
                            " | Total Bookings: " + entry.getValue()
            );
        }
    }
}

// Main class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        // Booking history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from UC6)
        history.addReservation(new Reservation("Alice", "Single", "Single-1"));
        history.addReservation(new Reservation("Bob", "Double", "Double-1"));
        history.addReservation(new Reservation("Charlie", "Single", "Single-2"));

        // Reporting service
        BookingReportService reportService = new BookingReportService();

        // Display all bookings
        reportService.displayAllBookings(history.getAllReservations());

        // Generate summary
        reportService.generateSummary(history.getAllReservations());
    }
}