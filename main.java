import java.util.*;

// Add-On Service class
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map reservation ID → list of services
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, Service service) {
        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Service added: " + service.getName()
                + " for Reservation: " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        System.out.println("\nServices for Reservation: " + reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (Service s : services) {
            System.out.println("Service: " + s.getName()
                    + " | Cost: " + s.getCost());
        }
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        double total = 0;

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// Main class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Sample reservation IDs (from UC6)
        String res1 = "Single-1";
        String res2 = "Suite-1";

        // Add services
        manager.addService(res1, new Service("Breakfast", 500));
        manager.addService(res1, new Service("Airport Pickup", 800));
        manager.addService(res2, new Service("Spa", 1500));

        // Display services
        manager.displayServices(res1);
        manager.displayServices(res2);

        // Show total cost
        System.out.println("\nTotal Add-On Cost for " + res1 + ": "
                + manager.calculateTotalCost(res1));

        System.out.println("Total Add-On Cost for " + res2 + ": "
                + manager.calculateTotalCost(res2));
    }
}