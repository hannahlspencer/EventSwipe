package eventswipe;

public interface BookingSystemAPI {
    public void logIn(String username, String password);
    public String getBookingList(String eventKey);
    public int getAttendeeCount(String eventKey);
    public void markAttended(String studentKey, String eventKey);
    public void cancelBooking(String studentKey, String eventKey);
    public boolean bookStudent(String studentKey, String eventKey);
}
