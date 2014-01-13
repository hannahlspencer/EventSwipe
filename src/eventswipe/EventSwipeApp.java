package eventswipe;

import eventswipe.EventSwipeData.FileFunction;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.EventObject;
import javax.swing.JOptionPane;
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
        logger = new EventSwipeLogger();
        if (Utils.isInternetReachable()) {
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

        this.addExitListener(new org.jdesktop.application.Application.ExitListener() {

            public boolean canExit(EventObject arg0) {
                return eventSwipeData.getSavedFlag();
            }

            public void willExit(EventObject arg0) {
                System.exit(0);
            }

        });

        root.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                if (e.getID() == WindowEvent.WINDOW_CLOSING && !eventSwipeData.getSavedFlag()) {
                    Utils.pressAlt();
                    int exit = JOptionPane.showConfirmDialog(EventSwipeApp.getApplication().getMainFrame(),
                                                     "You have recorded unsaved records. " +
                                                     "Are you sure you want to exit?",
                                                     "Exit warning",
                                                     JOptionPane.YES_NO_OPTION);
                    Utils.releaseAlt();
                    if (exit == JOptionPane.YES_OPTION) {
                        eventSwipeData.setSavedFlag(true);
                    }
                    else {
                        eventSwipeData.setSavedFlag(false);
                    }
                }
                else {
                    exit();
                }
            }
            
        });
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of EventSwipeApp
     */
    public static EventSwipeApp getApplication() {
        return Application.getInstance(EventSwipeApp.class);
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

    public void setEventTitle(String title) {
        eventSwipeData.setEventTitle(title);
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
                if (eventSwipeData.isWaitingListFlag())
                    waitingList = eventSwipeData.getBookingList(FileFunction.WAITING_LIST)
                                  .contains(stuNumber);
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
        eventSwipeData.setSavedFlag(false);
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
	header += " - " + Utils.getDate("dd/MM/yyyy HH:mm:ss") + System.getProperty("line.separator");
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
                writeToFile(saveFile, eventSwipeData.getAttendeesList().get(i) + 
                                      System.getProperty("line.separator"));
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

    public void createLog(String title) {
        logger.createLog(title);
    }

    public void log(String message) {
        logger.log(message);
    }

    public void clearData() {
        eventSwipeData.clearData();
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(EventSwipeApp.class, args);
    }

    private EventSwipeLogger logger;
    private EventSwipeData eventSwipeData;

}
