/**
 * Event: ADDITIONAL CLASS 2. Represents the event the tickets are sold for.
 * It keeps track of seat capacity so the same seat cannot be sold twice.
 */
public class Event {
    private String name;
    private String date;
    private String venue;
    private boolean[] seatTaken;   // index 0 = seat 1, etc.

    public Event(String name, String date, String venue, int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1.");
        }
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.seatTaken = new boolean[capacity];
    }

    public String getName()  { return name; }
    public String getDate()  { return date; }
    public String getVenue() { return venue; }
    public int getCapacity() { return seatTaken.length; }

    /** Checks if a seat exists and is still free. */
    public boolean isSeatAvailable(int seat) {
        return seat >= 1 && seat <= seatTaken.length && !seatTaken[seat - 1];
    }

    /** Reserves a seat. Returns false if the seat is invalid or already taken. */
    public boolean reserveSeat(int seat) {
        if (!isSeatAvailable(seat)) {
            return false;
        }
        seatTaken[seat - 1] = true;
        return true;
    }

    /** Frees a seat (used when a booking is cancelled). */
    public void releaseSeat(int seat) {
        if (seat >= 1 && seat <= seatTaken.length) {
            seatTaken[seat - 1] = false;
        }
    }

    /** Counts remaining seats using a loop. */
    public int seatsLeft() {
        int count = 0;
        for (boolean taken : seatTaken) {
            if (!taken) count++;
        }
        return count;
    }
}
