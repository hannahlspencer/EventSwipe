package eventswipe;

import eventswipe.EventSwipeData.BookingList;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;
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
        data = new EventSwipeData();
        logger = new EventSwipeLogger();
        api = new CareerHubAPI();
        HttpUtils.setCookiePolicy();
        if (Utils.isInternetReachable()) {
            data.setNetFlag(true);
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
                return data.getSavedFlag();
            }

            public void willExit(EventObject arg0) {
                System.exit(0);
            }

        });

        root.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                if (e.getID() == WindowEvent.WINDOW_CLOSING && !data.getSavedFlag()) {
                    Utils.pressAlt();
                    int exit = JOptionPane.showConfirmDialog(EventSwipeApp.getApplication().getMainFrame(),
                                                     "You have recorded unsaved records. " +
                                                     "Are you sure you want to exit?",
                                                     "Exit warning",
                                                     JOptionPane.YES_NO_OPTION);
                    Utils.releaseAlt();
                    if (exit == JOptionPane.YES_OPTION) {
                        data.setSavedFlag(true);
                    }
                    else {
                        data.setSavedFlag(false);
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
        data.setBookingFlag(selected);
    }

    public void setWaitingListFlag(boolean selected) {
        data.setWaitingListFlag(selected);
    }

    public boolean getBookingFlag() {
        return data.isBookingFlag();
    }

    public boolean getWaitingListFlag() {
        return data.isWaitingListFlag();
    }

    public void setSlots(int slots) {
        data.setSlots(slots);
    }

    public int getSlots() {
        return data.getSlots();
    }

    public boolean setFile(BookingList fileFunction, File file) {
        return data.setFile(fileFunction, file);
    }

    public void setEventTitle(String title) {
        data.setEventTitle(title);
    }

    public void setOnlineModeFlag(boolean flag) {
        data.setOnlineMode(flag);
    }

    public boolean isOnlineMode() {
        return data.isOnlineMode();
    }

    public void setId(BookingList bookingList, String id) {
        data.setId(bookingList, id);
    }

    public Booking checkBooking(String stuNumber) {
        Booking booking = new Booking(stuNumber);
        int slots = getSlots();
        int slot = 0;
        boolean booked = true;
        boolean waitingList = false;
        boolean alreadyRecorded = false;
        if(data.isBookingFlag()) {
            if (slots > 0 && data.getBookingList(BookingList.BOOKING_1).contains(stuNumber)) {
                slot = 1;
            }
            else if(slots > 1 && data.getBookingList(BookingList.BOOKING_2).contains(stuNumber)) {
                slot = 2;
            }
            else if(slots > 2 && data.getBookingList(BookingList.BOOKING_3).contains(stuNumber)) {
                slot = 3;
            }
            else {
                booked = false;
                if (data.isWaitingListFlag())
                    waitingList = data.getBookingList(BookingList.WAITING_LIST)
                                  .contains(stuNumber);
            }
        }
        if (data.getAllBookedList().contains(stuNumber)) {
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
    
    public String getLocalAttendeeCount() {
        Integer a = data.getLocalAttendeeCount();
        return a.toString();
    }

    public String getAttendeeCount() throws MalformedURLException, IOException {
        int slots = data.getSlots();
        int a1 = api.getAttendeeCount(data.getId1()), a2 = 0, a3 = 0;
        if (slots > 1)
            a2 = api.getAttendeeCount(data.getId2());
        if (slots > 2)
        a3 = api.getAttendeeCount(data.getId3());
        Integer i = a1 + a2 + a3;
        return i.toString();
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
        data.getAttendeesList().add(record);
        data.incrementAttendeesCount();
        data.getAllBookedList().add(stuNumber);
        data.setSavedFlag(false);
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
        String header = data.getEventTitle();
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
            for (int i = 0; i < data.getAttendeesList().size(); i++) {
                writeToFile(saveFile, data.getAttendeesList().get(i) +
                                      System.getProperty("line.separator"));
            }
            data.setSavedFlag(true);
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
        data.clearData();
    }

    public void logIn(String username, char[] password) throws MalformedURLException, IOException {
        api.logIn(username, password);
        Arrays.fill(password, '0');
    }

    public String getSlotTitle(String id) throws IOException {
        return api.getEventTitle(id);
    }

    public List<Event> getEvents(String term) throws MalformedURLException, IOException {
        return api.getEvents(term);
    }

    public void setApiBookingList(BookingList bookingList, String eventKey) throws MalformedURLException, IOException {
        List<Booking> bookings = api.getBookingList(eventKey);
        data.setBookingList(bookingList, bookings);
    }

    public void setApiWaitingList(String eventKey) throws MalformedURLException, IOException {
        List<Booking> waitingList = api.getWaitingList(eventKey);
        data.setApiWaitingList(waitingList);
    }

    public Booking processSearchInput(String input) {
        //TODO
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<Student> getStudents(String input) throws MalformedURLException, IOException {
        return api.getStudents(input);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(EventSwipeApp.class, args);
    }

    private EventSwipeLogger logger;
    private EventSwipeData data;
    private BookingSystemAPI api;

}
