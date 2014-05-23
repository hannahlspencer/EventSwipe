package eventswipe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @author wildmanm
 * Final variables and dynamic booking data and validation
 */
public class EventSwipeData {
    
    public static final int MAX_ENTRY_SLOTS = 3, MASTER_SLOT = 0;
    public static String BOOKING_1_ENCODING, BOOKING_2_ENCODING, 
                         BOOKING_3_ENCODING, WAITING_LIST_ENCODING = Utils.ANSI;

    public enum BookingList {
        BOOKING_1, BOOKING_2, BOOKING_3, WAITING_LIST
    }

    public EventSwipeData() {
        attendeesList = new ArrayList<String>();
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

    public Event getMasterEvent() {
        return masterEvent;
    }

    public void setMasterEvent(Event masterEvent) {
        this.masterEvent = masterEvent;
    }

    public void setId(BookingList bookingList, String id) {
         switch (bookingList) {
            case BOOKING_2:
                setId2(id);
                break;
            case BOOKING_3:
                setId3(id);
                break;
            default :
                setId1(id);
                break;
        }
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getId3() {
        return id3;
    }

    public void setId3(String id3) {
        this.id3 = id3;
    }

    public void setBookingList(BookingList bookingList, List<Booking> list) {
        switch (bookingList) {
            case BOOKING_2:
                setApiBookingList2(list);
                break;
            case BOOKING_3:
                setApiBookingList3(list);
                break;
            default :
                setApiBookingList1(list);
                break;
        }
    }

    public void clearData() {
        setLocalAttendeeCount(0);
        setWaitingListFlag(false);
        setSlots(0);
        setEventTitle("");
        clearList(attendeesList);
        clearList(allFileBookedList);
    }

    public void incrementAttendeesCount() {
        setLocalAttendeeCount(getLocalAttendeeCount() + 1);
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

    public List<Booking> getApiBookingList1() {
        return apiBookingList1;
    }

    public void setApiBookingList1(List<Booking> apiBookingList1) {
        this.apiBookingList1 = apiBookingList1;
    }

    public List<Booking> getApiBookingList2() {
        return apiBookingList2;
    }

    public void setApiBookingList2(List<Booking> apiBookingList2) {
        this.apiBookingList2 = apiBookingList2;
    }

    public List<Booking> getApiBookingList3() {
        return apiBookingList3;
    }

    public void setApiBookingList3(List<Booking> apiBookingList3) {
        this.apiBookingList3 = apiBookingList3;
    }

    public List<Student> getApiWaitingList() {
        return apiWaitingList;
    }

    public void setApiWaitingList(List<Student> apiWaitingList) {
        if (this.apiWaitingList.isEmpty()) {
            this.apiWaitingList = apiWaitingList;
        }
        else {
            this.apiWaitingList.addAll(apiWaitingList);
        }
    }

    public List<String> getAllRecordedList() {
        return allFileBookedList;
    }

    public void setAllBookedList(ArrayList<String> allBookedList) {
        this.allFileBookedList = allBookedList;
    }

    public List<String> getAttendeesList() {
        return attendeesList;
    }

    public void setAttendeesList(ArrayList<String> attendeesList) {
        this.attendeesList = attendeesList;
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

    public boolean setFile(BookingList fileFunction, File file) {
        if (!file.exists()) {
            JOptionPane.showMessageDialog(EventSwipeApp.getApplication().getMainFrame(),
                                          "File '" + file.getName() + "' not found. " +
                                          "Please ensure correct path has been entered.",
                                          "File not found",
                                          JOptionPane.ERROR_MESSAGE);
            return false;
        }
        switch (fileFunction) {
            case BOOKING_2:
                bookingFile2 = file;
                BOOKING_2_ENCODING = Utils.getEncoding(bookingFile2);
                fileBookingList2 = makeBookingList(bookingFile2, BOOKING_2_ENCODING);
                break;
            case BOOKING_3:
                bookingFile3 = file;
                BOOKING_3_ENCODING = Utils.getEncoding(bookingFile3);
                fileBookingList3 = makeBookingList(bookingFile3, BOOKING_3_ENCODING);
                break;
            case WAITING_LIST:
                waitingListFile = file;
                WAITING_LIST_ENCODING = Utils.getEncoding(waitingListFile);
                fileWaitingList = makeBookingList(waitingListFile, WAITING_LIST_ENCODING);
                break;
            default:
                bookingFile1 = file;
                BOOKING_1_ENCODING = Utils.getEncoding(bookingFile1);
                fileBookingList1 = makeBookingList(bookingFile1, BOOKING_1_ENCODING);
                break;
        }
        return true;
    }

    public List<String> getBookingList(BookingList fileFunction) {
        switch (fileFunction) {
            case BOOKING_2:
                return fileBookingList2;
            case BOOKING_3:
                return fileBookingList3;
            case WAITING_LIST:
                return fileWaitingList;
            default:
                return fileBookingList1;
        }
    }

    private void clearList(List<String> list) {
        if (!list.isEmpty())
            list.clear();
    }

    private ArrayList<String> makeBookingList(File file, String encoding) {
        return (ArrayList<String>) Utils.readAllLines(file, encoding);
    }

    private List<Event> events = new ArrayList<Event>();
    private Event masterEvent;

    private String id1;
    private String id2;
    private String id3;

    private List<Booking> apiBookingList1;
    private List<Booking> apiBookingList2;
    private List<Booking> apiBookingList3;
    private List<Student> apiWaitingList = new ArrayList<Student>();

    private List<String> attendeesList;

    private File bookingFile1;
    private File bookingFile2;
    private File bookingFile3;
    private File waitingListFile;
    private List<String> allFileBookedList;
    private List<String> fileBookingList1;
    private List<String> fileBookingList2;
    private List<String> fileBookingList3;
    private List<String> fileWaitingList;

    private String eventTitle;
    private int localAttendeeCount = 0;
    private int totalAttendeeCount = 0;
    private int slots;
    private boolean netFlag;
    private boolean onlineMode;
    private boolean waitingListFlag;
    private boolean bookingFlag;
    private boolean savedFlag = true;

}
