package eventswipe;

import eventswipe.models.Event;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wildmanm
 * Final variables and dynamic booking data and validation
 */
public class EventSwipeData {

    public static final int MAX_ENTRY_SLOTS = 5;

    public EventSwipeData() {
        allFileBookedList = new ArrayList<String>();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void registerEvent(int slot, Event event) {
        events.add(slot, event);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void clearData() {
        setLocalAttendeeCount(0);
        setWaitingListFlag(false);
        setSlots(0);
        setEventTitle("");
        clearList(allFileBookedList);
        events.clear();
    }

    public int incrementAttendeesCount() {
        setLocalAttendeeCount(getLocalAttendeeCount() + 1);
        return getLocalAttendeeCount();
    }

    public int getLocalAttendeeCount() {
        return localAttendeeCount;
    }

    public void setLocalAttendeeCount(int count) {
        this.localAttendeeCount = count;
    }

    public int getGlobalAttendeeCount() {
        return localAttendeeCount;
    }

    public void setGlobalAttendeeCount(int count) {
        this.localAttendeeCount = count;
    }

    public List<String> getAllRecordedList() {
        return allFileBookedList;
    }

    public void setAllBookedList(ArrayList<String> allBookedList) {
        this.allFileBookedList = allBookedList;
    }

    public boolean getNetFlag() {
        return netFlag;
    }
    
    public void setNetFlag(boolean b) {
        this.netFlag = b;
    }

    public boolean isOnlineMode() {
        return onlineMode;
    }

    public void setOnlineMode(boolean onlineMode) {
        this.onlineMode = onlineMode;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public void setSingleSlot(boolean b) {
        this.singleSlotFlag = b;
    }

    public boolean isSingleSlot() {
        return singleSlotFlag;
    }

    public boolean isWaitingListFlag() {
        return waitingListFlag;
    }

    public void setWaitingListFlag(boolean waitingListFlag) {
        this.waitingListFlag = waitingListFlag;
    }

    public boolean isBookingFlag() {
        return bookingFlag;
    }

    public void setBookingFlag(boolean bookingFlag) {
        this.bookingFlag = bookingFlag;
    }

    public boolean getSavedFlag() {
        return savedFlag;
    }

    public void setSavedFlag(boolean savedFlag) {
        this.savedFlag = savedFlag;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public Integer getCount() {
        return count;
    }

    public int incrementCount() {
        count++;
        return count;
    }

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
    
    private int count = 0;

}
