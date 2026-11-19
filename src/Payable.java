public interface Payable {
    /** @return the final price of ONE ticket (each ticket type has its own formula). */
    double calculatePrice();

    /** @return a short name for the ticket type, e.g. "Standard". */
    String getTicketType();
}
