import java.util.Map;
import java.util.HashMap;
import eventswipe.EventSwipeData;
import eventswipe.EventSwipeApp;
import java.util.Properties;
import eventswipe.exceptions.NoStudentFoundException;
import eventswipe.models.Event;
import eventswipe.models.Student;
import eventswipe.models.Booking;
import java.util.ArrayList;
import eventswipe.APIs.BookingSystemAPI.STATUS;
import java.util.List;
import eventswipe.APIs.CareerHubAPI;
import eventswipe.APIs.BookingSystemAPI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CareerHubAPITest {

    //Start tests with Emma booked into the event as unspecified only
    private static BookingSystemAPI api;
    private static EventSwipeApp app;
    private static List<Booking>  bookings;
    private static EventSwipeData data;
    private static Student student;
    private static String eventKey = "203802";
    private static String username = "eventswipe";
    private static char[] password = {'m','i','k','e','t','i','l','e','y'};

    public CareerHubAPITest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        api = CareerHubAPI.getInstance();
        app = new EventSwipeApp();
        data = EventSwipeData.getInstance();

        Properties p = app.getProperties(EventSwipeData.API_PROPERITES_PATH);

        @SuppressWarnings({"unchecked", "rawtypes"})
        Map<String, String> pMap = new HashMap(p);

        data.setCustomProperties(pMap);
        api.init();

        bookings = new ArrayList<Booking>();
        student = new Student();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void apiLoginTest() throws Exception {
        try {
            assert(api.logIn(username, password));
        } catch (Exception ex) {
            Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void apiGetStudentTest() {
        try {
            student = api.getStudent("349154");
        } catch (Exception ex) {
            Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals("Incorrect name", "Matt", student.getFirstName());
        assert(student.getId()== 38);
    }

    @Test
    public void apiBookTest() {
        try {
            student = api.getStudent("349154");
            Booking booking = api.bookStudent(student.getId().toString(), eventKey);
        } catch (Exception ex) {
            Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void apiGetTest() {
        try {
            bookings = api.getBookingList(eventKey);
        } catch (Exception ex) {
            Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals("Bookings ArrayList is the wrong size", 2, bookings.size());
        assertEquals("Incorrect name", "Emma", bookings.get(0).getFirstName());
    }
 
    @Test
    public void apiGetNumberOfAttendees1() {
        int count = 0;
        try {
            count = api.getAttendeeCount(eventKey);
        } catch (Exception ex) {
            Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals("Incorrect attendee count", 0, count);
    }

    @Test
    public void apiMarkMultipleAttendedTest() {
        List<String> keys = new ArrayList<String>();
        for (int i=0; i < bookings.size(); i++) {
            keys.add(bookings.get(i).getBookingId().toString());
        }
        try {
            api.markStatus(STATUS.ATTENDED, keys, eventKey);
        } catch (Exception ex) {
           Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
    @Test
    public void apiGetNumberOfAttendees2() {
        int count = 0;
        try {
            count = api.getAttendeeCount(eventKey);
        } catch (Exception ex) {
           Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        assertEquals("Incorrect attendee count", 2, count);
    }


    @Test
    public void apiMarkMultipleUnspecifiedTest() {
        try {
            api.markStatus(STATUS.UNSPECIFIED, bookings.get(0).getBookingId().toString(), eventKey);
            api.markStatus(STATUS.UNSPECIFIED, bookings.get(1).getBookingId().toString(), eventKey);
        } catch (Exception ex) {
           Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void apiCancelBookingTest() {
        try {
            api.cancelBooking(bookings.get(1).getBookingId().toString(), eventKey);
        } catch (Exception ex) {
           Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void apiGetEventTitleTest() {
        String title = "";
        try {
            title = api.getEvent(eventKey).getTitle();
        } catch (Exception ex) {
          Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        assertEquals("Incorrect title", "Test event", title);
    }

    @Test
    public void apiGetWaitingListTest() {
        List<Student> waitingList = new ArrayList<Student>();
        try {
            waitingList = api.getWaitingList(eventKey);
        } catch (Exception ex) {
           Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        Student waitingStudent = waitingList.get(0);
        assertEquals("Incorrect id", 32, (int)waitingStudent.getId());
        waitingStudent = waitingList.get(1);
        assertEquals("Incorrect id", 80514, (int)waitingStudent.getId());
    }

    @Test
    public void getStudentsTest() {
        List<Student> students = new ArrayList<Student>();
        try {
            students = api.getStudents("wildman");
        } catch (Exception ex) {
           Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Student me = students.get(0);
        Student otherStudent = students.get(1);
        assertEquals("Result set is the wrong size", 5, students.size());
        assertEquals("My details incorrect", "Matt", me.getFirstName());
        assertEquals("Other student's details incorrect", "ANIKE", otherStudent.getFirstName());
    }

    @Test
    public void getMoreStudentsTest() {
        List <Student> students = new ArrayList<Student>();
        try {
            students = api.getStudents("emma");
        } catch (Exception ex) {
          Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertFalse("No students retrieved", students.isEmpty());
    }

    @Test
    public void getStudentTest() {
        Student me = new Student(), notFound = new Student();
        try {
            me = api.getStudent("349154");
            notFound = api.getStudent("999999");
        } catch (NoStudentFoundException nsf) {
            assertEquals("Error catch failed", "999999", nsf.getStuNum());
        } catch (Exception ex) {
           Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals("Incorrect student", "Matt", me.getFirstName());
    }

    @Test
    public void getBookingLimitTest() {
        Event event = new Event();
        Event event2 = new Event();
        Event event3 = new Event();
        Event event4 = new Event();
        try {
            event = api.getEvent(eventKey); //regular CareerHub booking
            event2 = api.getEvent("212627"); //no booking
            event3 = api.getEvent("240665"); //custom booking limit
            event4 = api.getEvent("240667"); //external booking
        }
        catch (Exception ex) {
         Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals("Incorrect booking limit", 5, event.getBookingLimit());
        assert(event2.isDropIn());
        assert(event4.isDropIn());
        assertFalse(event2.isUnlimited());
        assertEquals("Incorrect booking limit", 120, event3.getBookingLimit());
    }

    @Test
    public void getUnspecifiedTest() {
        List<String> unspecifieds = new ArrayList<String>();
        try {
            unspecifieds = api.getUnspecified(eventKey);
        } catch (Exception ex) {
           Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals("Incorrect number of unspecified students", 1, unspecifieds.size());
    }

    @Test
    public void markAbsentTest() {
        try {
            api.markAllUnspecifiedAbsent(eventKey, false);
        } catch (Exception ex) {
           Logger.getLogger(CareerHubAPITest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void getStudentWithNoLastName() {
        try {
            Student stu = new Student();
            stu = api.getStudent("201610493");
            System.out.println(stu.getFirstName() + " " + stu.getLastName());
        } catch (Exception ex) {
            System.out.println("Fetching student failed");
            ex.printStackTrace();
        }

    }
}