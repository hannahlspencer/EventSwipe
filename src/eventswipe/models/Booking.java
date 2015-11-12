package eventswipe.models;

public class Booking {

    public static final Integer EARLY_STATUS = 3;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    private String stuNumber = "";
    private Integer bookingId = 0;
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer status;
    
    private Integer entrySlot;

    private boolean booked;
    private boolean alreadyRecorded;
    private boolean onWaitingList;

}
