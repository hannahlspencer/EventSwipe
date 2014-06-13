package eventswipe;

import java.util.ArrayList;
import java.util.List;

public class Event {

    public Event() { }

    public Event(String title, String date, String id) {
        this.title = title;
        this.startDate = date;
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public List<Student> getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(List<Student> waitingList) {
        this.waitingList = waitingList;
    }

    public List<String> getUnsavedList() {
        return unsavedList;
    }

    public void setUnsavedList(List<String> unsavedList) {
        this.unsavedList = unsavedList;
    }

    public int getBookingLimit() {
        return bookingLimit;
    }

    public void setBookingLimit(int bookingLimit) {
        this.bookingLimit = bookingLimit;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getAttendeeCount() {
        return attendeeCount;
    }

    public void setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    public boolean isUnlimited() {
        return unlimited;
    }

    public void setUnlimited(boolean unlimited) {
        this.unlimited = unlimited;
    }

    public boolean isDropIn() {
        return dropIn;
    }

    public void setDropIn(boolean dropIn) {
        this.dropIn = dropIn;
    }

    private String startDate;
    private String title;
    private String id;
    private String venue;

    private List<Booking> bookingList;
    private List<Student> waitingList;
    private List<String> unsavedList = new ArrayList<String>();

    private int bookingLimit;
    private int slot;
    private int attendeeCount;

    private boolean unlimited = false;
    private boolean dropIn = false;

}
