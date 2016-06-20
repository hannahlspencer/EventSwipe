package eventswipe;

import eventswipe.models.Event;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the data for an attendance recording session.
 *
 * @author Matt Wildman http://bitbucket.com/mattwildman
 */
public class EventSwipeData {

    /**
     * The maximum entry slots of a multi-slot event.
     */
    public static final int MAX_ENTRY_SLOTS = 5;

    /**
     * Default constructor for EventSwipeData
     */
    public EventSwipeData() {
        allFileBookedList = new ArrayList<String>();
    }

    /**
     * @return The List of Events running (multiple when recording attendance for a multi-slot event)
     * @see Event
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Sets the List of Events running (multiple when recording attendance for a multi-slot event)
     *
     * @param events A List of Events
     * @see Event
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * Adds an Event (or entry slot in a multi-slot event) to start recording attendance
     *
     * @param event An Event
     * @see Event
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Clears all data in current attendance recording session.
     */
    public void clearData() {
        setLocalAttendeeCount(0);
        setWaitingListFlag(false);
        setSlots(0);
        setEventTitle("");
        clearList(allFileBookedList);
        events.clear();
    }

    /**
     * Adds 1 to the number of attendees recorded on the
     * machine where EventSwipe is running then returns this number.
     *
     * @return The number of locally recorded attendees, after incrementation
     */
    public int incrementAttendeesCount() {
        setLocalAttendeeCount(getLocalAttendeeCount() + 1);
        return getLocalAttendeeCount();
    }

    /**
     * @return The number of attendees recorded on the machine where EventSwipe is running
     */
    public int getLocalAttendeeCount() {
        return localAttendeeCount;
    }

    /**
     * Sets the number of attendees recorded on the machine where EventSwipe is running.
     *
     * @param count The number of locally recorded attendees
     */
    public void setLocalAttendeeCount(int count) {
        this.localAttendeeCount = count;
    }

    /**
     * @return The total number of attendees recorded for the Event(s)
     */
    public int getGlobalAttendeeCount() {
        return localAttendeeCount;
    }

    /**
     * Sets the total number of attendees recorded for the Event(s)
     *
     * @param count The total number of attendees recorded
     */
    public void setGlobalAttendeeCount(int count) {
        this.localAttendeeCount = count;
    }

    /**
     * @return A List of all the student number Strings that have been recorded
     */
    public List<String> getAllRecordedList() {
        return allFileBookedList;
    }

    /**
     * Sets the List of everyone who has booked for the Event(s)
     *
     * @param allBookedList A List of student number Strings
     */
    public void setAllBookedList(ArrayList<String> allBookedList) {
        this.allFileBookedList = allBookedList;
    }

    /**
     * @return True if connected to the internet, false if not
     */
    public boolean getNetFlag() {
        return netFlag;
    }
    
    /**
     * Sets whether or not EventSwipe can access the internet.
     *
     * @param b True/false depending on whether internet access is possible
     */
    public void setNetFlag(boolean b) {
        this.netFlag = b;
    }

    /**
     * @return True if EventSwipe is in 'online mode', false if not
     */
    public boolean isOnlineMode() {
        return onlineMode;
    }

    /**
     * Sets whether EventSwipe is in online mode or not.
     *
     * @param onlineMode True/false to set whether or not EventSwipe is in online mode
     */
    public void setOnlineMode(boolean onlineMode) {
        this.onlineMode = onlineMode;
    }

    /**
     * @return The number of entry slots of the event
     */
    public int getSlots() {
        return slots;
    }

    /**
     * Sets the number of entry slots of the event.
     *
     * @param slots The number of entry slots
     */
    public void setSlots(int slots) {
        this.slots = slots;
    }

    /**
     * Sets whether or not the event is a single entry slot event.
     *
     * @param b True/false depending on whether the event is a single slot event or not
     */
    public void setSingleSlot(boolean b) {
        this.singleSlotFlag = b;
    }

    /**
     * @return True/false depending on whether the event has a single entry slot or not
     */
    public boolean isSingleSlot() {
        return singleSlotFlag;
    }

    /**
     * @return True/false depending on whether EventSwipe is checking waiting lists or not
     */
    public boolean isWaitingListFlag() {
        return waitingListFlag;
    }

    /**
     * Sets whether or not EventSwipe is checking waiting lists.
     *
     * @param waitingListFlag True/false to check waiting lists or not
     */
    public void setWaitingListFlag(boolean waitingListFlag) {
        this.waitingListFlag = waitingListFlag;
    }

    /**
     * @return True/false depending on whether EventSwipe is checking booking lists
     */
    public boolean isCheckingBookingLists() {
        return bookingFlag;
    }

    /**
     * Sets whether EventSwipe checks booking lists during attendance recording or not.
     *
     * @param bookingFlag True/false to checking booking lists or not
     */
    public void setBookingFlag(boolean bookingFlag) {
        this.bookingFlag = bookingFlag;
    }

    /**
     *
     * @return
     */
    public boolean getSavedFlag() {
        return savedFlag;
    }

    /**
     *
     * @param savedFlag
     */
    public void setSavedFlag(boolean savedFlag) {
        this.savedFlag = savedFlag;
    }

    /**
     *
     * @return
     */
    public boolean isLoggedInFlag() {
        return loggedInFlag;
    }

    /**
     *
     * @param success
     */
    public void setLoggedInFlag(boolean success) {
        this.loggedInFlag = success;
    }

    /**
     *
     * @return
     */
    public String getEventTitle() {
        return eventTitle;
    }

    /**
     *
     * @param eventTitle
     */
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    /**
     *
     * @return
     */
    public Integer getBookingCount() {
        int c = 0;
        for (Event e : events) {
            c += e.getBookingList().size();
        }
        return c;
    }

    /**
     *
     * @return
     */
    public Integer getAttendeeCount() {
        return this.getAllRecordedList().size();
    }

    /**
     *
     * @return
     */
    public Integer getSavedCount() {
        int c = 0;
        for (Event e : events) {
            c += e.getAttendeeCount();
        }
        return c;
    }

    /**
     *
     * @return
     */
    public Integer getUnsavedCount() {
        int c = 0;
        for (Event e : events) {
            c += e.getUnsavedList().size();
        }
        return c;
    }

    /**
     *
     * @return
     */
    public Integer getNotAttendedCount() {
        int diff = this.getBookingCount() - this.getAttendeeCount();
        return diff < 0 ? 0 : diff;
    }

    /**
     *
     * @return
     */
    public Integer getCurrentNumberOfPlaces() {
        int limit = 0;
        for (Event e : events) {
            if (e.isUnlimited() || e.isDropIn()) {
                return -1;
            }
            limit += e.getBookingLimit();
        }
        return limit - this.getAttendeeCount();
    }

    /**
     *
     * @return
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @return
     */
    public int incrementCount() {
        count++;
        return count;
    }

    /**
     *
     * @param c
     */
    public void setCount(int c) {
        count = c;
    }

    private void clearList(List<String> list) {
        if (!list.isEmpty())
            list.clear();
    }

    private List<Event> events = new ArrayList<Event>();

    private List<String> allFileBookedList;

    private String eventTitle;
    private int localAttendeeCount = 0;
    private int slots;
    private boolean netFlag;
    private boolean onlineMode;
    private boolean waitingListFlag;
    private boolean bookingFlag;
    private boolean savedFlag = true;
    private boolean singleSlotFlag = false;
    private boolean loggedInFlag = false;
    
    private int count = 0;

}
