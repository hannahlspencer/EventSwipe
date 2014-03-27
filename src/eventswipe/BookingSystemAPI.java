package eventswipe;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface BookingSystemAPI {

    public void logIn(String username, char[] password) throws MalformedURLException, IOException;
    public List<Booking> getBookingList(String eventKey) throws MalformedURLException, IOException;
    public List<Booking> getWaitingList(String eventKey) throws MalformedURLException, IOException;
    public int getAttendeeCount(String eventKey) throws MalformedURLException, IOException;
    public void markStatus(STATUS status, String studentKey, String eventKey) throws MalformedURLException, IOException;
    public void markStatus(STATUS status, List<String> studentKeys, String eventKey) throws MalformedURLException, IOException;
    public void cancelBooking(String studentKey, String eventKey) throws MalformedURLException, IOException;
    public void bookStudent(String studentKey, String eventKey) throws MalformedURLException, IOException;
    public Student getStudentData(Integer stuNumber) throws MalformedURLException, IOException;
    public List<Event> getEvents(String searchTerm) throws MalformedURLException, IOException;;
    public String getEventTitle(String eventKey) throws IOException;

    public enum STATUS {
        UNSPECIFIED, ATTENDED, ABSENT, NOT_BOOKED
    }
    
}
