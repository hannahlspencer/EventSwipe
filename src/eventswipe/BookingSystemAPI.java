package eventswipe;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

public interface BookingSystemAPI {

    public void logIn(String username, String password) throws MalformedURLException, IOException;
    public String getBookingList(String eventKey) throws MalformedURLException, IOException;
    public int getAttendeeCount(String eventKey);
    public void markAttended(String studentKey, String eventKey) throws MalformedURLException, IOException;
    public void cancelBooking(String studentKey, String eventKey) throws MalformedURLException, IOException;
    public void bookStudent(String studentKey, String eventKey) throws MalformedURLException, IOException;

    public Map createURLMap();

    public enum BookingSystemServices {
        LOGIN, MARK_ATTENDED, CANCEL, BOOK, GET_BOOKINGS, GET_BOOKINGS_1, GET_BOOKINGS_2,
        GET_BOOKINGS_3, GET_BOOKINGS_4, GET_WAITING_LIST
    }
    
}
