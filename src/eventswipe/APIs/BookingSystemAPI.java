package eventswipe.APIs;

import eventswipe.models.Event;
import eventswipe.models.Booking;
import eventswipe.models.Student;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public abstract class BookingSystemAPI {

    public BookingSystemAPI() {}

    public abstract boolean logIn(String username, char[] password) throws MalformedURLException, IOException;
    public abstract List<Booking> getBookingList(String eventKey) throws MalformedURLException, IOException;
    public abstract List<Student> getWaitingList(String eventKey) throws MalformedURLException, IOException;
    public abstract List<String> getUnspecified(String eventKey) throws MalformedURLException, IOException;
    public abstract int getAttendeeCount(String eventKey) throws MalformedURLException, IOException;
    public abstract void markStatus(STATUS status, String studentKey, String eventKey) throws MalformedURLException, IOException;
    public abstract void markStatus(STATUS status, List<String> studentKeys, String eventKey) throws MalformedURLException, IOException;
    public abstract void markAbsent(List<String> studentKeys, String eventKey, Boolean notify) throws MalformedURLException, IOException;
    public abstract void markAllUnspecifiedAbsent(String eventKey, Boolean notify) throws MalformedURLException, IOException;
    public abstract void cancelBooking(String studentKey, String eventKey) throws MalformedURLException, IOException;
    public abstract Booking bookStudent(String studentKey, String eventKey) throws MalformedURLException, IOException;
    public abstract Student getStudentData(String stuNumber) throws MalformedURLException, IOException;
    public abstract List<Student> getStudents(String search) throws MalformedURLException, IOException ;
    public abstract List<Event> getEvents(String searchTerm) throws MalformedURLException, IOException;
    public abstract Event getEvent(String eventKey) throws IOException;
    public abstract String getEventTitle(String eventKey) throws IOException;
    public abstract String getAdminEventURL(String eventKey) throws IOException;
    public abstract String getCharset();
    public abstract String getEmptyStuNumString();
    public abstract int getATTENDED_STATUS();
    public abstract int getEVENT_FULL_STATUS();

    public enum STATUS {
        UNSPECIFIED, ATTENDED, ABSENT, NOT_BOOKED, EVENT_FULL
    }

}
