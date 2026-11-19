/**
 * StandardTicket: DERIVED CLASS 1. Normal ticket; price = base price.
 * Unique attribute: zone (which seating zone the ticket is in).
 */
public class StandardTicket extends Ticket {
    private String zone;   // unique to StandardTicket

    public StandardTicket(String eventName, String eventDate, int seatNumber,
                          double basePrice, String zone) {
        super(eventName, eventDate, seatNumber, basePrice); // call base constructor
        setZone(zone);
    }

    public String getZone() { return zone; }

    public void setZone(String zone) {
        if (zone == null || zone.trim().isEmpty()) {
            throw new IllegalArgumentException("Zone cannot be empty.");
        }
        this.zone = zone.trim();
    }

    /** Polymorphism: standard price has no extra charge or discount. */
    @Override
    public double calculatePrice() { return getBasePrice(); }

    @Override
    public String getTicketType() { return "Standard"; }

    @Override
    public String getBenefits() { return super.getBenefits() + " (Zone " + zone + ")"; }
}
