package eventswipe;

import eventswipe.EventSwipeData.FileFunction;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class EventSwipeApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        eventSwipeData = new EventSwipeData();
        if (isInternetReachable()) {
            eventSwipeData.setNetFlag(true);
        }
        show(new EventSwipeView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of EventSwipeApp
     */
    public static EventSwipeApp getApplication() {
        return Application.getInstance(EventSwipeApp.class);
    }

    public static boolean isInternetReachable() {
        try {
            InetAddress address = InetAddress.getByName("java.sun.com");
            if(address == null) {
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }

    public void setBookingFlag(boolean selected) {
        eventSwipeData.setBookingFlag(selected);
    }

    public void setWaitingListFlag(boolean selected) {
        eventSwipeData.setWaitingListFlag(selected);
    }

    public boolean getBookingFlag() {
        return eventSwipeData.isBookingFlag();
    }

    public boolean getWaitingListFlag() {
        return eventSwipeData.isWaitingListFlag();
    }

    public void setSlots(int slots) {
        eventSwipeData.setSlots(slots);
    }

    public int getSlots() {
        return eventSwipeData.getSlots();
    }

    public boolean setFile(FileFunction fileFunction, File file) {
        return eventSwipeData.setFile(fileFunction, file);
    }

    public boolean setEventTitle(String title) {
        return eventSwipeData.setEventTitle(title);
    }

    public Booking checkBooking(String stuNumber) {
        Booking booking = new Booking(stuNumber);
        int slots = getSlots();
        int slot = 0;
        boolean booked = true;
        boolean waitingList = false;
        boolean alreadyRecorded = false;
        if(eventSwipeData.isBookingFlag()) {
            if (slots > 0 && eventSwipeData.getBookingList(FileFunction.BOOKING_1).contains(stuNumber)) {
                slot = 1;
            }
            else if(slots > 1 && eventSwipeData.getBookingList(FileFunction.BOOKING_2).contains(stuNumber)) {
                slot = 2;
            }
            else if(slots > 2 && eventSwipeData.getBookingList(FileFunction.BOOKING_3).contains(stuNumber)) {
                slot = 3;
            }
            else {
                booked = false;
                waitingList = eventSwipeData.getBookingList(FileFunction.WAITING_LIST).contains(stuNumber);
            }
        }
        if (eventSwipeData.getAllBookedList().contains(stuNumber)) {
            alreadyRecorded = true;
        }
        else if(booked) {
            recordAttendance(stuNumber, slot);
        }
        booking.setBooked(booked);
        booking.setEntrySlot(slot);
        booking.setAlreadyRecorded(alreadyRecorded);
        booking.setOnWaitingList(waitingList);
        return booking;
    }
    
    public String getAttendeeCount() {
        Integer a = eventSwipeData.getAttendeesCount();
        return a.toString();
    }

    public void recordAttendance(String stuNumber, int slot) {
        String record = stuNumber;
        switch (slot) {
            case 1:
                record += ", entry slot 1";
                break;
            case 2:
                record += ", entry slot 2";
                break;
            case 3:
                record += ", entry slot 3";
                break;
            default: 
                break;
        }
        eventSwipeData.getAttendeesList().add(record);
        eventSwipeData.incrementAttendeesCount();
        eventSwipeData.getAllBookedList().add(stuNumber);
    }

    public void writeToFile(File file, String content) {
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            fw.write(content);
            fw.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Action
    public void saveAttendeesToFile() {
        String header = eventSwipeData.getEventTitle();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Date eventDate = new Date();
	header += " - " + dateFormat.format(eventDate) + "\n\n";
        FileDialog fDialog = new FileDialog(this.getMainFrame(), 
                        "Save attendees list", FileDialog.SAVE);
        fDialog.setVisible(true);
        String path = fDialog.getDirectory() + fDialog.getFile();
        if (!path.equals("nullnull")) {
            if (!path.endsWith(".txt")) {
                path += ".txt";
            }
            File saveFile = new File(path);
            try {
                saveFile.createNewFile();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            writeToFile(saveFile, header);
            for (int i = 0; i < eventSwipeData.getAttendeesList().size(); i++) {
                writeToFile(saveFile, eventSwipeData.getAttendeesList().get(i) + "\n");
            }
            eventSwipeData.setSavedFlag(true);
            Desktop dk = Desktop.getDesktop();
            try {
                dk.open(saveFile);
            } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(EventSwipeApp.class, args);
    }

    private EventSwipeData eventSwipeData;

}
