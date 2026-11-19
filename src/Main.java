import java.util.Scanner;

/**
 * Main: the program the user runs. It creates the Event, BookingManager,
 * Customer and the three ticket types, then shows a menu so the user can
 * book, list and cancel tickets (see the control structures marked below).
 */
public class Main {

    public static void main(String[] args) {
        // ---------- PART 1: automatic demo (fixed data) ----------
        System.out.println("=== PART 1: Polymorphism demo ===");
        Event demoEvent = new Event("Music Fest", "10/12/2026", "Axiata Arena", 50);
        BookingManager demo = new BookingManager(demoEvent);
        Customer ali = new Customer("Ali", "ali@mail.com", "0123456789");

        // One list type (Ticket) holding three different ticket types
        Ticket t1 = new StandardTicket("Music Fest", "10/12/2026", 1, 200.0, "A");
        VIPTicket t2 = new VIPTicket("Music Fest", "10/12/2026", 2, 200.0, 300.0, true);
        Ticket t3 = new EarlyBirdTicket("Music Fest", "10/12/2026", 3, 200.0, 20.0);
        t2.upgradeMeal();   // method that only exists in VIPTicket

        demo.addBooking(t1, ali);
        demo.addBooking(t2, ali);
        demo.addBooking(t3, ali);
        demo.printAllTickets();
        System.out.printf("Grand total: RM%.2f%n", demo.calculateGrandTotal());
        System.out.println("Seats left : " + demoEvent.seatsLeft());

        // Encapsulation demo: invalid data is rejected by the setters
        try {
            t1.setBasePrice(-50);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected by setter: " + e.getMessage());
        }
        // Duplicate seat is rejected by Event / BookingManager
        Ticket dup = new StandardTicket("Music Fest", "10/12/2026", 1, 200.0, "A");
        System.out.println("Book seat 1 again? " + demo.addBooking(dup, ali));

        // ---------- PART 2: interactive menu ----------
        System.out.println("\n=== PART 2: Interactive booking ===");
        Event event = new Event("F1 Grand Prix", "10/10/2026", "Sepang Circuit", 20);
        BookingManager manager = new BookingManager(event);

        try (Scanner sc = new Scanner(System.in)) {
            int choice;
            do {                                   // LOOP: menu repeats until Exit
                System.out.println("\n--- " + event.getName() + " (" + event.getDate()
                        + ") | seats left: " + event.seatsLeft() + " ---");
                System.out.println("1. Book ticket\n2. View tickets\n3. Cancel booking\n4. Total & exit");
                System.out.print("Choice: ");
                choice = readInt(sc);

                switch (choice) {                  // SELECTION: switch on the menu
                    case 1:
                        bookTicket(sc, manager, event);
                        break;
                    case 2:
                        manager.printAllTickets();
                        break;
                    case 3:
                        System.out.print("Enter ticket ID: ");
                        String id = sc.nextLine().trim();
                        if (manager.cancelBooking(id)) {   // SELECTION: if/else
                            System.out.println("Booking cancelled.");
                        } else {
                            System.out.println("Ticket ID not found.");
                        }
                        break;
                    case 4:
                        System.out.printf("Total to pay: RM%.2f for %d ticket(s).%n",
                                manager.calculateGrandTotal(), manager.getTicketCount());
                        System.out.println("Thank you!");
                        break;
                    default:
                        System.out.println("Please choose 1-4.");
                }
            } while (choice != 4);
        }
    }

    /** Handles option 1: asks for details, builds the right ticket type, books it. */
    private static void bookTicket(Scanner sc, BookingManager manager, Event event) {
        try {
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Phone: ");
            String phone = sc.nextLine();
            Customer c = new Customer(name, email, phone);   // validated in constructor

            System.out.print("Seat number (1-" + event.getCapacity() + "): ");
            int seat = readInt(sc);
            if (!event.isSeatAvailable(seat)) {
                System.out.println("Seat not available.");
                return;
            }

            System.out.print("Type (1=Standard RM200, 2=VIP RM500, 3=EarlyBird RM160): ");
            int type = readInt(sc);
            Ticket t;
            if (type == 1) {
                t = new StandardTicket(event.getName(), event.getDate(), seat, 200.0, "A");
            } else if (type == 2) {
                VIPTicket vip = new VIPTicket(event.getName(), event.getDate(), seat, 200.0, 300.0, true);
                System.out.print("Add meal package (+RM50)? (y/n): ");
                if (sc.nextLine().equalsIgnoreCase("y")) {
                    vip.upgradeMeal();
                }
                t = vip;
            } else if (type == 3) {
                t = new EarlyBirdTicket(event.getName(), event.getDate(), seat, 200.0, 20.0);
            } else {
                System.out.println("Invalid ticket type.");
                return;
            }

            if (manager.addBooking(t, c)) {
                System.out.println("Booked! ID: " + t.getTicketID() + " | Price: RM"
                        + String.format("%.2f", t.calculatePrice()));
            } else {
                System.out.println("Booking failed.");
            }
        } catch (IllegalArgumentException e) {   // validation errors from setters
            System.out.println("Error: " + e.getMessage());
        }
    }

    /** Reads an integer safely; returns -1 if the input is not a number. */
    private static int readInt(Scanner sc) {
        String line = sc.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
