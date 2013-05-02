package eventswipe;

import eventswipe.EventSwipeData.FileFunction;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Task;

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

    public boolean isBooked(String stuNumber, FileFunction fileFunction) {
        if (eventSwipeData.getBookingList(fileFunction).contains(stuNumber)) {
            if (!eventSwipeData.getAttendeesList().contains(stuNumber))
                recordAttendance(stuNumber, fileFunction);
            return true;
        }
        return false;
    }

    public String addAttendee() {
        eventSwipeData.incrementAttendeesCount();
        Integer a = eventSwipeData.getAttendeesCount();
        return a.toString();
    }

    public void recordAttendance(String stuNumber, FileFunction fileFunction) {
        String record = stuNumber;
        switch (fileFunction) {
            case BOOKING_1:
                record += ", entry slot 1";
                break;
            case BOOKING_2:
                record += ", entry slot 2";
                break;
            case BOOKING_3:
                record += ", entry slot 3";
                break;
            default: 
                break; //don't record waiting list students
        }
        eventSwipeData.getAttendeesList().add(record);
    }

    public void recordAttendance(String stuNumber) {
        eventSwipeData.getAttendeesList().add(stuNumber);
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
        int session = 0;
        String header = eventSwipeData.getEventTitle();
        String filename = eventSwipeData.getEventTitle() + " - attendees";
        String ext = "." + TextCSVFilter.txt;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Date eventDate = new Date();
	header += " - " + dateFormat.format(eventDate) + "  \n\n";
        File file = new File(filename + ext);
        String testName = filename;
        while (file.exists()) { //prevents an attendees file from an earlier session being overwritten
            testName += "(" + ++session + ")";
            file = new File(testName + ext);
            testName = filename;
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        writeToFile(file, header);
        for (int i = 0; i < eventSwipeData.getAttendeesList().size(); i++) {
            writeToFile(file, eventSwipeData.getAttendeesList().get(i) + "\n");
        }
        Desktop dk = Desktop.getDesktop();
        try {
            dk.open(file);
        } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
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
