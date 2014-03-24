package eventswipe;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

public interface BookingSystemAPI {

    public void logIn(String username, String password) throws MalformedURLException, IOException;
    public String getBookingList(String eventKey) throws MalformedURLException, IOException;
    public int getAttendeeCount(String eventKey);
    public void markStatus(STATUS status, String studentKey, String eventKey) throws MalformedURLException, IOException;
    public void markStatus(STATUS status, List<String> studentKeys, String eventKey) throws MalformedURLException, IOException;
    public void cancelBooking(String studentKey, String eventKey) throws MalformedURLException, IOException;
    public void bookStudent(String studentKey, String eventKey) throws MalformedURLException, IOException;
    public String getStudentData(Integer stuNumber) throws MalformedURLException, IOException;

    public enum STATUS {
        UNSPECIFIED, ATTENDED, ABSENT, NOT_BOOKED
    }
    
}
