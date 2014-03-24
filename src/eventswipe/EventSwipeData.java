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
    
    public static final int MAX_ENTRY_SLOTS = 3;
    public static String BOOKING_1_ENCODING, BOOKING_2_ENCODING, 
                         BOOKING_3_ENCODING, WAITING_LIST_ENCODING = Utils.ANSI;

    public EventSwipeData() {
        attendeesList = new ArrayList<String>();
        allBookedList = new ArrayList<String>();
    }

    public enum BookingList {
        BOOKING_1, BOOKING_2, BOOKING_3, WAITING_LIST
    }

    public void clearData() {
        setAttendeesCount(0);
        setWaitingListFlag(false);
        setSlots(0);
        setEventTitle("");
        clearList(attendeesList);
        clearList(allBookedList);
    }

    public void incrementAttendeesCount() {
        setAttendeesCount(getAttendeesCount() + 1);
    }

    public int getAttendeesCount() {
        return attendeesCount;
    }

    public void setAttendeesCount(int attendeesCount) {
        this.attendeesCount = attendeesCount;
    }

    public List<String> getAllBookedList() {
        return allBookedList;
    }

    public void setAllBookedList(ArrayList<String> allBookedList) {
        this.allBookedList = allBookedList;
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
                bookingList2 = makeBookingList(bookingFile2, BOOKING_2_ENCODING);
                break;
            case BOOKING_3:
                bookingFile3 = file;
                BOOKING_3_ENCODING = Utils.getEncoding(bookingFile3);
                bookingList3 = makeBookingList(bookingFile3, BOOKING_3_ENCODING);
                break;
            case WAITING_LIST:
                waitingListFile = file;
                WAITING_LIST_ENCODING = Utils.getEncoding(waitingListFile);
                waitingList = makeBookingList(waitingListFile, WAITING_LIST_ENCODING);
                break;
            default:
                bookingFile1 = file;
                BOOKING_1_ENCODING = Utils.getEncoding(bookingFile1);
                bookingList1 = makeBookingList(bookingFile1, BOOKING_1_ENCODING);
                break;
        }
        return true;
    }

    public List<String> getBookingList(BookingList fileFunction) {
        switch (fileFunction) {
            case BOOKING_2:
                return bookingList2;
            case BOOKING_3:
                return bookingList3;
            case WAITING_LIST:
                return waitingList;
            default:
                return bookingList1;
        }
    }

    private void clearList(List<String> list) {
        if (!list.isEmpty())
            list.clear();
    }

    private ArrayList<String> makeBookingList(File file, String encoding) {
        return (ArrayList<String>) Utils.readAllLines(file, encoding);
    }

    private File bookingFile1;
    private File bookingFile2;
    private File bookingFile3;
    private File waitingListFile;
    private List<String> allBookedList;
    private List<String> bookingList1;
    private List<String> bookingList2;
    private List<String> bookingList3;
    private List<String> waitingList;
    private List<String> attendeesList;
    private String eventTitle;
    private int attendeesCount = 0;
    private int slots;
    private boolean netFlag;
    private boolean waitingListFlag;
    private boolean bookingFlag;
    private boolean savedFlag = true;

}
