import java.util.ArrayList;

/**
 * BookingManager: ADDITIONAL CLASS 3. Manages all tickets for one event.
 * Stores every ticket in ONE list of the base type (Ticket), which is what
 * makes polymorphism possible: each call to calculatePrice() runs the
 * version that belongs to the real ticket type.
 */
public class BookingManager {
    private final Event event;
    private final ArrayList<Ticket> tickets = new ArrayList<>();

    public BookingManager(Event event) { this.event = event; }

    public Event getEvent() { return event; }
    public int getTicketCount() { return tickets.size(); }

    /**
     * Books a ticket: checks the seat is free, books the ticket and stores it.
     * @return true if successful.
     */
    public boolean addBooking(Ticket ticket, Customer customer) {
        if (!event.reserveSeat(ticket.getSeatNumber())) {
            return false;                      // seat invalid or already taken
        }
        if (!ticket.book(customer)) {
            event.releaseSeat(ticket.getSeatNumber());   // undo reservation
            return false;
        }
        tickets.add(ticket);
        return true;
    }

    /** Finds a ticket by ID using a loop. Returns null if not found. */
    public Ticket findByID(String id) {
        for (Ticket t : tickets) {
            if (t.getTicketID().equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }

    /** Cancels a booking by ticket ID and frees the seat. */
    public boolean cancelBooking(String id) {
        Ticket t = findByID(id);
        if (t == null) {
            return false;
        }
        int seat = t.getSeatNumber();
        if (t.cancel()) {
            event.releaseSeat(seat);
            tickets.remove(t);
            return true;
        }
        return false;
    }

    /** POLYMORPHISM: same call, different result for each ticket type. */
    public double calculateGrandTotal() {
        double total = 0;
        for (Ticket t : tickets) {
            total += t.calculatePrice();
        }
        return total;
    }

    /** Prints every ticket, with its benefits (overridden per type). */
    public void printAllTickets() {
        if (tickets.isEmpty()) {
            System.out.println("No tickets booked yet.");
            return;
        }
        for (Ticket t : tickets) {
            System.out.println(t);
            System.out.println("     Benefits: " + t.getBenefits());
        }
    }
}
