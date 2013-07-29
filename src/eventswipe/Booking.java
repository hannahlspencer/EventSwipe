package eventswipe;

/**
 *
 * @author wildmanm
 *
 * Comprises a booking status and an entry slot
 *
 */
public class Booking {

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public int getEntrySlot() {
        return entrySlot;
    }

    public void setEntrySlot(int entrySlot) {
        this.entrySlot = entrySlot;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public boolean isAlreadyRecorded() {
        return alreadyRecorded;
    }

    public void setAlreadyRecorded(boolean alreadyRecorded) {
        this.alreadyRecorded = alreadyRecorded;
    }

    private String bookingType;
    private int entrySlot;
    private boolean booked;
    private boolean alreadyRecorded;
    
}
