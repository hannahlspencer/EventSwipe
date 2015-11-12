package eventswipe.APIs;

import eventswipe.models.Event;
import eventswipe.models.Booking;
import eventswipe.models.Student;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface BookingSystemAPI {

    public boolean logIn(String username, char[] password) throws MalformedURLException, IOException;
    public List<Booking> getBookingList(String eventKey) throws MalformedURLException, IOException;
    public List<Student> getWaitingList(String eventKey) throws MalformedURLException, IOException;
    public List<String> getUnspecified(String eventKey) throws MalformedURLException, IOException;
    public int getAttendeeCount(String eventKey) throws MalformedURLException, IOException;
    public void markStatus(STATUS status, String studentKey, String eventKey) throws MalformedURLException, IOException;
    public void markStatus(STATUS status, List<String> studentKeys, String eventKey) throws MalformedURLException, IOException;
    public void markAbsent(List<String> studentKeys, String eventKey, Boolean notify) throws MalformedURLException, IOException;
    public void markAllUnspecifiedAbsent(String eventKey, Boolean notify) throws MalformedURLException, IOException;
    public void cancelBooking(String studentKey, String eventKey) throws MalformedURLException, IOException;
    public Booking bookStudent(String studentKey, String eventKey) throws MalformedURLException, IOException;
    public Student getStudentData(String stuNumber) throws MalformedURLException, IOException;
    public List<Student> getStudents(String search) throws MalformedURLException, IOException ;
    public List<Event> getEvents(String searchTerm) throws MalformedURLException, IOException;
    public Event getEvent(String eventKey) throws IOException;
    public String getEventTitle(String eventKey) throws IOException;
    public String getAdminEventURL(String eventKey) throws IOException;
    public String getCharset();
    public String getEmptyStuNumString();
    public int getATTENDED_STATUS();
    public int getEVENT_FULL_STATUS();

    public enum STATUS {
        UNSPECIFIED, ATTENDED, ABSENT, NOT_BOOKED, EVENT_FULL
    }
    
}
