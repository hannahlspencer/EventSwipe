package eventswipe;

/**
 * @author wildmanm
 *
 * Comprises booking status and student number
 *
 */
public class Booking {

    public Booking(String stuNumber) {
        setStuNumber(stuNumber);
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public Integer getEntrySlot() {
        return entrySlot;
    }

    public void setEntrySlot(Integer entrySlot) {
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

    public boolean isOnWaitingList() {
        return onWaitingList;
    }

    public void setOnWaitingList(boolean waitingList) {
        this.onWaitingList = waitingList;
    }

    private void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    private String stuNumber;
    private Integer entrySlot;
    private boolean booked;
    private boolean alreadyRecorded;
    private boolean onWaitingList;
    
}
