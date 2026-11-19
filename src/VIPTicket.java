/**
 * VIPTicket: DERIVED CLASS 2. Premium ticket; price = base price + surcharge.
 * Unique attributes: loungeAccess, surcharge. Unique method: upgradeMeal().
 */
public class VIPTicket extends Ticket {
    private boolean loungeAccess;   // unique to VIPTicket
    private double surcharge;       // fixed extra amount charged for VIP
    private boolean mealIncluded;   // unique to VIPTicket

    public VIPTicket(String eventName, String eventDate, int seatNumber,
                     double basePrice, double surcharge, boolean loungeAccess) {
        super(eventName, eventDate, seatNumber, basePrice);
        setSurcharge(surcharge);
        this.loungeAccess = loungeAccess;
        this.mealIncluded = false;
    }

    public double getSurcharge()      { return surcharge; }
    public boolean hasLoungeAccess()  { return loungeAccess; }
    public boolean isMealIncluded()   { return mealIncluded; }

    public void setSurcharge(double surcharge) {
        if (surcharge < 0) {
            throw new IllegalArgumentException("Surcharge cannot be negative.");
        }
        this.surcharge = surcharge;
    }

    public void setLoungeAccess(boolean loungeAccess) { this.loungeAccess = loungeAccess; }

    /** Unique method: adds a meal package to the VIP ticket (costs RM50 extra). */
    public void upgradeMeal() { this.mealIncluded = true; }

    /** Polymorphism: VIP price = base + surcharge (+ RM50 if meal included). */
    @Override
    public double calculatePrice() {
        double total = getBasePrice() + surcharge;
        if (mealIncluded) {
            total += 50.0;
        }
        return total;
    }

    @Override
    public String getTicketType() { return "VIP"; }

    @Override
    public String getBenefits() {
        String text = super.getBenefits() + ", priority entrance";
        if (loungeAccess) text += ", lounge access";
        if (mealIncluded) text += ", meal package";
        return text;
    }
}
