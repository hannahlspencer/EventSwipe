package eventswipe;

import eventswipe.BookingSystemAPI.STATUS;
import eventswipe.EventSwipeData.BookingList;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public Booking checkBooking(String stuNumber) throws MalformedURLException, IOException {
        Booking bookingResult = new Booking(stuNumber);
        int slot = 0;
        String bookingId = "";
        boolean booked = true;
        boolean waitingList = false;
        boolean alreadyRecorded = false;

        if (data.getAllRecordedList().contains(stuNumber)) {
            alreadyRecorded = true;
        }
        else if(data.isBookingFlag()) {
            booked = false;
            for (Event event : data.getEvents()) {
                for (Booking booking : event.getBookingList()) {
                    if (booking.getStuNumber().equals(stuNumber)) {
                        booked = true;
                        slot = event.getSlot();
                        bookingId = booking.getBookingId().toString();
                        break;
                    }
                }
            }
            if (!booked && data.isWaitingListFlag()) {
                for (Event event : data.getEvents()) {
                    for (Student student : event.getWaitingList()) {
                        if (student.getStuNumber() == Integer.parseInt(stuNumber)) {
                            waitingList = true;
                            slot = event.getSlot();
                        }
                    }
                }
            }
        }
        else if (data.isOnlineMode()) {
            api.bookStudent(stuNumber, data.getMasterEvent().getId());
            slot = EventSwipeData.MASTER_SLOT;
        }

        if(booked && !alreadyRecorded) {
            String key = data.isOnlineMode() ? bookingId : stuNumber;
            recordAttendance(key, slot);
        }

        bookingResult.setBooked(booked);
        bookingResult.setEntrySlot(slot);
        bookingResult.setAlreadyRecorded(alreadyRecorded);
        bookingResult.setOnWaitingList(waitingList);
        return bookingResult;
    }

    public void recordAttendance(String stuNumber, int slot) throws MalformedURLException, IOException {
        Event event = slot == EventSwipeData.MASTER_SLOT ? data.getMasterEvent() :
                                                           data.getEvents().get(slot - 1);
        if (data.isOnlineMode()) {
            api.markStatus(STATUS.ATTENDED, stuNumber, event.getId());
        }
        else {
            event.getUnsavedList().add(stuNumber);
            data.setSavedFlag(false);
        }
        data.incrementAttendeesCount();
        data.getAllRecordedList().add(stuNumber);
    }
    
    public String getLocalAttendeeCount() {
        Integer a = data.getLocalAttendeeCount();
        return a.toString();
    }

    public String getAttendeeCount() throws MalformedURLException, IOException {
        Integer a = 0;
        for (Event event : data.getEvents()) {
            a += api.getAttendeeCount(event.getId());
        }
        return a.toString();
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

    public void createLog() {
        logger.createLog(data.getEventTitle());
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

    public Event getEvent(String key) throws MalformedURLException, IOException {
        return api.getEvent(key);
    }

    public List<Event> getEvents(String term) throws MalformedURLException, IOException {
        return api.getEvents(term);
    }

    public void setApiBookingList(BookingList bookingList, String eventKey) throws MalformedURLException, IOException {
        List<Booking> bookings = api.getBookingList(eventKey);
        data.setBookingList(bookingList, bookings);
    }

    public void setApiWaitingList(String eventKey) throws MalformedURLException, IOException {
        List<Student> waitingList = api.getWaitingList(eventKey);
        data.setApiWaitingList(waitingList);
    }

    public void createWaitingList(String path) {
        File file = new File(path);
        List<String> numberList = Utils.readAllLines(file, Utils.getEncoding(file));
        List<Student> waitingList = new ArrayList<Student>();
        for (String number : numberList) {
            Student student = new Student();
            student.setStuNumber(Integer.parseInt(number));
            waitingList.add(student);
        }
        for (Event event : data.getEvents()) {
            event.setWaitingList(waitingList);
        }
    }

    public Booking processSearchInput(String input) throws MalformedURLException, IOException {
        return checkBooking(input);
    }

    public List<Student> getStudents(String input) throws MalformedURLException, IOException {
        return api.getStudents(input);
    }

    public void setEvents(List<String> paths) {
        for (int i = 0; i < data.getSlots(); i++) {
            File file = new File(paths.get(i));
            List<String> numberList = Utils.readAllLines(file, Utils.getEncoding(file));
            List<Booking> bookingList = new ArrayList<Booking>();
            for (String number : numberList) {
                Booking booking = new Booking(number);
                bookingList.add(booking);
            }
            Event event = new Event();
            event.setBookingList(bookingList);
            event.setSlot(i+1);
            data.addEvent(event);
        }
    }

    public Event loadEvent(String eventKey, int slot, Boolean waitingList) throws MalformedURLException, IOException {
        Event event = this.getEvent(eventKey);
        event.setSlot(slot);
        event.setBookingList(api.getBookingList(eventKey));
        if(waitingList) {
            event.setWaitingList(api.getWaitingList(eventKey));
        }
        data.addEvent(event);
        return event;
    }

    public int getBookedCount() {
        Integer b = 0;
        for (Event event : data.getEvents()) {
            b += event.getBookingList().size();
        }
        return b;
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
