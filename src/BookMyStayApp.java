public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay - Hotel Booking App");
        System.out.println("Version 3.1");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        System.out.println("\nChecking availability...");
        System.out.println("Single Room Available: "
                + inventory.getAvailability("Single Room"));

        System.out.println("\nUpdating availability...");
        inventory.updateAvailability("Single Room", 8);

        inventory.displayInventory();
    }
}