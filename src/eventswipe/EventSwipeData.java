package eventswipe;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @author wildmanm
 * Final variables and dynamic booking data and validation
 */
public class EventSwipeData {
    
    public static final int MAX_ENTRY_SLOTS = 3;
    public static final String titleInputDefault = "Please enter the name of the event";
    public static final String fileInputDefault = "Enter file path";

    public EventSwipeData() {
        attendeesList = new ArrayList<String>();
        allBookedList = new ArrayList<String>();
    }

    public enum FileFunction {
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

    private void clearList(List<String> list) {
        if (!list.isEmpty())
            list.clear();
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

    public boolean isSavedFlag() {
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

    public boolean setFile(FileFunction fileFunction, File file) {
        if (!file.exists()) {
            JOptionPane.showMessageDialog(EventSwipeApp.getApplication().getMainFrame(),
                                          "File '" + file.getName() + "' not found. Please ensure correct path has been entered.",
                                          "File not found",
                                          JOptionPane.ERROR_MESSAGE);
            return false;
        }
        switch (fileFunction) {
            case BOOKING_2:
                bookingFile2 = file;
                bookingList2 = makeBookingList(bookingFile2);
                break;
            case BOOKING_3:
                bookingFile3 = file;
                bookingList3 = makeBookingList(bookingFile3);
                break;
            case WAITING_LIST:
                waitingListFile = file;
                waitingList = makeBookingList(waitingListFile);
                break;
            default:
                bookingFile1 = file;
                bookingList1 = makeBookingList(bookingFile1);
                break;
        }
        return true;
    }

    public List<String> getBookingList(FileFunction fileFunction) {
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

    private ArrayList<String> makeBookingList(File file) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine = null;
            while ((strLine = br.readLine()) != null)   {
                list.add(strLine);
            }
            in.close();
        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        return list;
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
    private boolean savedFlag = false;

}
