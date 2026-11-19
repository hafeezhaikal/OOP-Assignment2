import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Ticket: the BASE CLASS (continued from TicketSystem in Assignment 1).
 * It is abstract because a "general" ticket never exists on its own - every
 * ticket must be a Standard, VIP or EarlyBird ticket.
 *
 * Design improvement over Assignment 1: one Ticket object = ONE ticket.
 * (Assignment 1 reused one object for many seats, which overwrote the data.)
 * Status and ID can only be changed by book() and cancel() so they stay safe.
 */
public abstract class Ticket implements Payable {

    // ---- Private attributes (encapsulation) ----
    private String ticketID;          // unique ID, created when the ticket is booked
    private String eventName;         // name of the event
    private String eventDate;         // date of the event
    private int seatNumber;           // seat assigned to this ticket
    private double basePrice;         // starting price before any extra/discount
    private boolean booked;           // true if the ticket is currently booked
    private LocalDateTime bookedAt;   // time of booking, used to build the ticket ID
    private Customer customer;        // who owns the ticket (null until booked)

    /**
     * Constructor: initialises the base attributes. Setters are used so that
     * the validation rules are applied even when the object is first created.
     */
    public Ticket(String eventName, String eventDate, int seatNumber, double basePrice) {
        setEventName(eventName);
        setEventDate(eventDate);
        setSeatNumber(seatNumber);
        setBasePrice(basePrice);
        this.ticketID = "N/A";   // no ID until booked
        this.booked = false;     // new tickets start as not booked
        this.customer = null;
    }

    // ---- Getters: read the private attributes ----
    public String getTicketID()      { return ticketID; }
    public String getEventName()     { return eventName; }
    public String getEventDate()     { return eventDate; }
    public int getSeatNumber()       { return seatNumber; }
    public double getBasePrice()     { return basePrice; }
    public boolean isBooked()        { return booked; }
    public Customer getCustomer()    { return customer; }

    // ---- Setters: change attributes, with validation ----
    public void setEventName(String eventName) {
        if (eventName == null || eventName.trim().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty.");
        }
        this.eventName = eventName.trim();
    }

    public void setEventDate(String eventDate) {
        if (eventDate == null || eventDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Event date cannot be empty.");
        }
        this.eventDate = eventDate.trim();
    }

    public void setSeatNumber(int seatNumber) {
        if (seatNumber < 1) {
            throw new IllegalArgumentException("Seat number must be at least 1.");
        }
        this.seatNumber = seatNumber;
    }

    public void setBasePrice(double basePrice) {
        if (basePrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.basePrice = basePrice;
    }

    // NOTE: there is deliberately NO setTicketID() or setBooked().
    // Only book() and cancel() may change them (data protection).

    /**
     * Books this ticket for a customer.
     * @return true if booked; false if already booked or the customer is invalid.
     */
    public boolean book(Customer customer) {
        if (booked || customer == null) {
            return false;
        }
        this.customer = customer;
        this.booked = true;
        this.bookedAt = LocalDateTime.now();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        // ID format: TCK-date-time-seat (same format as Assignment 1)
        this.ticketID = "TCK-" + bookedAt.format(f) + "-" + seatNumber;
        return true;
    }

    /** Cancels the booking and clears the customer and ID. Returns true if cancelled. */
    public boolean cancel() {
        if (!booked) {
            return false;   // nothing to cancel
        }
        this.booked = false;
        this.customer = null;
        this.ticketID = "N/A";
        return true;
    }

    /**
     * Extra benefits of the ticket. Subclasses may OVERRIDE this method.
     * The base version gives the default text.
     */
    public String getBenefits() {
        return "Entry to the event";
    }

    /** One-line summary shown in lists. Uses polymorphic calls to the subclass. */
    @Override
    public String toString() {
        return String.format("%-12s | Seat %-3d | %-20s | RM%8.2f | %s",
                getTicketType(), seatNumber, ticketID, calculatePrice(),
                booked ? customer.getName() : "Available");
    }
}
