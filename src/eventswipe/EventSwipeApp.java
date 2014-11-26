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
    @Override public void startup() {
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

    private Booking getBooking(String stuNumber) throws MalformedURLException, IOException {
        for (Event event : data.getEvents()) {
            for (Booking booking : event.getBookingList()) {
                if (booking.getStuNumber().equals(stuNumber)) {
                    booking.setEntrySlot(event.getSlot());
                    return booking;
                }
            }
        }
        Booking newBooking = new Booking(stuNumber);
        return bookStudent(stuNumber, newBooking);
    }

    public Booking checkBooking(String stuNumber) throws MalformedURLException, IOException {
        Booking bookingResult = new Booking(stuNumber);
        int slot = 0;
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
                        bookingResult = booking;
                        break;
                    }
                }
            }
            if (!booked && data.isWaitingListFlag()) {
                for (Event event : data.getEvents()) {
                    if (!event.getWaitingList().isEmpty()) {
                        for (Student student : event.getWaitingList()) {
                            try {
                                if (student.getStuNumber().equals(stuNumber)) {
                                    waitingList = true;
                                    slot = event.getSlot();
                                }
                            } catch (NullPointerException np) {
                                System.err.println("Waiting list student " +
                                                   student.getId() +
                                                   " has no student number");
                            }
                        }
                    }
                }
            }
        }
        else if (data.isOnlineMode()) {
            bookingResult = getBooking(stuNumber);
            slot = bookingResult.getEntrySlot();
        }
        bookingResult.setBooked(booked);
        slot = (slot == 0) ? 1 : slot;
        bookingResult.setEntrySlot(slot);
        bookingResult.setAlreadyRecorded(alreadyRecorded);
        bookingResult.setOnWaitingList(waitingList);
        if(booked && !alreadyRecorded) {
            recordAttendance(bookingResult);
        }
        return bookingResult;
    }

    public Booking bookStudent(String stuNumber, Booking booking) throws MalformedURLException, IOException {
        if (data.isOnlineMode()) {
            String studId = "";
            try {
                studId = api.getStudentData(stuNumber).getId().toString();
            } catch (NoStudentFoundException nsf) {
                throw nsf;
            }
            Event freeEvent = new Event();
            int freeSlot = 0;
            for (Event event : data.getEvents()) {
                int slot = event.getSlot();
                event = api.getEvent(event.getId());
                List<Booking> bookings = api.getBookingList(event.getId());
                event.setSlot(slot);
                event.setBookingList(bookings);
                if (event.isUnlimited() || (event.getBookingLimit() > event.getBookingList().size())) {
                    freeSlot = freeSlot == 0 ? event.getSlot() : freeSlot;
                }
            }
            if (freeSlot > 0) {
                freeEvent = data.getEvents().get(freeSlot - 1);
                String eventId = freeEvent.getId();
                Integer newId = api.bookStudent(studId, eventId).getBookingId();
                booking.setEntrySlot(freeSlot);
                booking.setBookingId(newId);
            }
            else {
                throw new EventFullException(stuNumber);
            }
        }
        else {
            booking.setEntrySlot(1);
        }
        return booking;
    }

    public void recordAttendance(Booking booking) throws MalformedURLException, IOException {
        Event event = data.getEvents().get(booking.getEntrySlot() - 1);
        if (data.isOnlineMode()) {
            api.markStatus(STATUS.ATTENDED, booking.getBookingId().toString(), event.getId());
        }
        else {
            event.getUnsavedList().add(booking.getStuNumber());
            data.setSavedFlag(false);
        }
        data.incrementAttendeesCount();
        data.getAllRecordedList().add(booking.getStuNumber());
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
            for (Event event : data.getEvents()) {
                writeToFile(saveFile, event.getTitle() + System.getProperty("line.separator"));
                for (String stuNum : event.getUnsavedList()) {
                    writeToFile(saveFile, stuNum + System.getProperty("line.separator"));
                }
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

    public boolean logIn(String username, char[] password) throws MalformedURLException, IOException {
        boolean success = api.logIn(username, password);
        Arrays.fill(password, '0');
        return success;
    }

    public Event getEvent(String key) throws MalformedURLException, IOException {
        return api.getEvent(key);
    }

    public List<Event> getEvents(String term) throws MalformedURLException, IOException {
        return api.getEvents(term);
    }

    public String getAdminEventURL(String eventKey) throws IOException {
        return api.getAdminEventURL(eventKey);
    }

    public void createWaitingList(String path) {
        File file = new File(path);
        List<String> numberList = Utils.readAllLines(file, Utils.getEncoding(file));
        List<Student> waitingList = new ArrayList<Student>();
        for (String number : numberList) {
            Student student = new Student();
            student.setStuNumber(number);
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

    public void addEvent(Event event) {
        data.addEvent(event);
    }

    public void setEvents(List<String> paths) {
        for (int i = 0; i < paths.size(); i++) {
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
            addEvent(event);
        }
    }

    public Event loadEvent(String eventKey, int slot, Boolean useWaitingList) throws MalformedURLException, IOException {
        Event event = this.getEvent(eventKey);
        event.setSlot(slot);
        event.setBookingList(api.getBookingList(eventKey));
        if(useWaitingList) {
            List<Student> waitingList = api.getWaitingList(eventKey);
            event.setWaitingList(waitingList);
            if (!waitingList.isEmpty()) {
                data.setWaitingListFlag(true);
            }
        }
        data.addEvent(event);
        return event;
    }

    public void goToOnlineMode() {
        setOnlineModeFlag(true);
        try {
            bookUnsavedRecords();
        } catch (EventFullException ef) {
            throw ef;
        }
    }

    public int getBookedCount() {
        Integer b = 0;
        for (Event event : data.getEvents()) {
            b += event.getBookingList().size();
        }
        return b;
    }

    public String getCharset() {
        return api.getCharset();
    }

    public String getEmptyStuNumString() {
        return api.getEmptyStuNumString();
    }

    public Integer getEventFullStatus() {
        return api.getEVENT_FULL_STATUS();
    }

    public Event getEvent(int slot) {
        return data.getEvents().get(slot);
    }

    public void finish(Boolean markAbsent) throws MalformedURLException, IOException {
        if (data.isOnlineMode()) {
            if (!data.getSavedFlag()) {
                try {
                    this.bookUnsavedRecords();
                } catch (Exception e) {
                    this.saveAttendeesToFile();
                }
            }
            if (markAbsent && data.getSavedFlag()) {
                for (Event event : data.getEvents()) {
                    api.markAllUnspecifiedAbsent(event.getId(), false);
                }
            }
        }
        else if (!data.getSavedFlag()) {
            this.saveAttendeesToFile();
        }
        if (data.getSavedFlag()) {
            System.exit(0);
        }
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(EventSwipeApp.class, args);
    }

    private void bookUnsavedRecords() throws EventFullException {
        for (Event event : data.getEvents()) {
            List<String> saveErrors = new ArrayList<String>();
            for (int i = 0; i < event.getUnsavedList().size(); i++) {
                String stuNum = event.getUnsavedList().get(i);
                System.out.println(stuNum);
                try {
                    Booking booking = getBooking(stuNum);
                    booking.setEntrySlot(event.getSlot());
                    System.out.println("slot: " + booking.getEntrySlot());
                    System.out.println("booking id: " + booking.getBookingId());
                    recordAttendance(booking);
                } catch (EventFullException ef) {
                    for (int j = i; j < event.getUnsavedList().size(); j++) {
                        saveErrors.add(event.getUnsavedList().get(j));
                    }
                    event.setUnsavedList(saveErrors);
                    throw ef;
                } catch (Exception ex) {
                    Logger.getLogger(EventSwipeApp.class.getName()).log(Level.SEVERE, null, ex);
                    saveErrors.add(stuNum);
                } 
            }
            if (saveErrors.isEmpty()) {
                event.getUnsavedList().clear();
                data.setSavedFlag(true);
            }
            else {
                event.setUnsavedList(saveErrors);
            }
        }
    }

    private EventSwipeLogger logger;
    private EventSwipeData data;
    private BookingSystemAPI api;

}
