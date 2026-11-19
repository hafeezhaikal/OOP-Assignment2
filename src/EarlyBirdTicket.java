/**
 * EarlyBirdTicket: DERIVED CLASS 3. Discounted ticket for early buyers.
 * Price = base price - discount%. Unique attribute: discountPercent.
 * Unique method: isStillValid() checks the promo quota.
 */
public class EarlyBirdTicket extends Ticket {
    private double discountPercent;   // unique to EarlyBirdTicket (0 - 100)

    public EarlyBirdTicket(String eventName, String eventDate, int seatNumber,
                           double basePrice, double discountPercent) {
        super(eventName, eventDate, seatNumber, basePrice);
        setDiscountPercent(discountPercent);
    }

    public double getDiscountPercent() { return discountPercent; }

    public void setDiscountPercent(double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100.");
        }
        this.discountPercent = discountPercent;
    }

    /** Unique method: early-bird promo is only valid for the first 10 seats. */
    public boolean isStillValid() { return getSeatNumber() <= 10; }

    /** Polymorphism: price after discount. */
    @Override
    public double calculatePrice() {
        return getBasePrice() * (1 - discountPercent / 100.0);
    }

    @Override
    public String getTicketType() { return "EarlyBird"; }

    @Override
    public String getBenefits() {
        return super.getBenefits() + ", " + discountPercent + "% early-bird discount";
    }
}
