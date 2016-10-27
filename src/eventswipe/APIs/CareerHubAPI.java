package eventswipe.APIs;

import eventswipe.EventSwipeData;
import eventswipe.exceptions.*;
import eventswipe.utils.*;
import eventswipe.models.*;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CareerHubAPI extends BookingSystemAPI {

    public static BookingSystemAPI getInstance() {
        if (instance == null) {
            instance = new CareerHubAPI();
        }
        return instance;
    }
    
    protected CareerHubAPI() {}

    public void init() {
        Map<String, String> p = EventSwipeData.getInstance().getCustomProperties();

        HOST = p.get(EventSwipeData.HOST_KEY);
        API_ID = p.get(EventSwipeData.API_ID_KEY);
        SECRET = p.get(EventSwipeData.API_SECRET_KEY);
        STU_NUM_PATTERN = p.get(EventSwipeData.STUDENT_ID_PATTERN_KEY);

        ADMIN_URL = HOST + "admin/";

        LOGIN_URL =            ADMIN_URL + "login/";
        QUERY_URL =            ADMIN_URL + "events/bookings/query/";
        BOOKING_URL =          ADMIN_URL + "events/bookings/create/";
        MARK_ATTENDED_URL =    ADMIN_URL + "events/bookings/markattended/";
        MARK_UNSPECIFIED_URL = ADMIN_URL + "events/bookings/markunspecified/";
        MARK_ABSENT_URL =      ADMIN_URL + "events/bookings/markabsent/";
        CANCEL_URL =           ADMIN_URL + "events/bookings/cancel/";
        WAITING_LIST_BASE =    ADMIN_URL + "eventwaitinglist.aspx?id=";
        STUDENT_SEARCH_BASE =  ADMIN_URL + "suggest/JobSeeker";
        EVENT_ADMIN_URL_BASE = ADMIN_URL + "event.aspx?id=";
        EVENT_API_SEARCH_URL = HOST + "api/public/v1/events/";
        EVENT_API_URL =        HOST + "api/integrations/v1/events/";
        EVENT_API_LIST_URL =   EVENT_API_URL + "?filterIds=82&filterIds=83";
    }

    public boolean logIn(String username, char[] password) throws MalformedURLException, IOException {
        String dateStr = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date());
        Map<String,String> requestHeaders = new HashMap<String,String>();
        requestHeaders.put("Content-Type", "application/x-www-form-urlencoded;charset=" + getCharset());
        requestHeaders.put("Cookie", "CareerHubCookieCheck=" + dateStr);
        String loginData = "__RequestVerificationToken=" + getVerificationToken() +
                          "&username=" + username +
                          "&password=" + String.valueOf(password) +
                          "&isPersistent=true&isPersistent=false";
        HttpUtils.sendDataToURL(LOGIN_URL, "POST", loginData, getCharset(), requestHeaders);
        CookieManager manager = (CookieManager)CookieHandler.getDefault();
        CookieStore cookieJar =  manager.getCookieStore();
        List <HttpCookie> cookies = cookieJar.getCookies();
        for(HttpCookie cookie : cookies) {
            if(cookie.getName().equals(AUTH_COOKIE_NAME)) {
                return true;
            }
        }
        return false;
    }

    public String getAPIToken(String scope) throws MalformedURLException, IOException {
        AccessToken t = tokens.get(scope);
        if (t == null || t.getExpiryDate().before(new Date())) {
            t = new AccessToken();
            String apiURL = HOST + "oauth/token";
            String postdata = "grant_type=client_credentials" +
                              "&client_id=" + URLEncoder.encode(API_ID, charset) +
                              "&client_secret=" + URLEncoder.encode(SECRET, charset) +
                              "&scope=" + scope;
            Map<String,String> requestHeaders = new HashMap<String,String>();
            requestHeaders.put("Content-Type", "application/x-www-form-urlencoded");
            String response = HttpUtils.sendDataToURL(apiURL, "POST", postdata, charset, requestHeaders);
            JSONObject apiData = new JSONObject(response);
            t.setScope(scope);
            t.setToken(apiData.getString("access_token"));
            t.setTokenType(apiData.getString("token_type"));
            t.setExpiryDate(Utils.addMins(new Date(), apiData.getInt("expires_in")));
            tokens.put(scope, t);
        }
        return t.getToken();
    }

    public List<Booking> getBookingList(String eventKey) throws MalformedURLException, IOException {
        List<Booking> bookings = new ArrayList<Booking>();
        String response = HttpUtils.getDataFromURL(QUERY_URL + eventKey);
        JSONObject bookingData = new JSONObject(response);
        JSONArray jsonBookings = bookingData.getJSONArray("bookings");
        for (int i=0; i < jsonBookings.length(); i++) {
            JSONObject jsonBooking = jsonBookings.getJSONObject(i);
            Booking booking = null;
            try {
                booking = new Booking(jsonBooking.getString("externalId"));
                booking.setFirstName(jsonBooking.getString("firstName"));
                booking.setLastName(jsonBooking.getString("lastName"));
                booking.setId(jsonBooking.getInt("jobSeekerId"));
                booking.setBookingId(jsonBooking.getInt("id"));
                booking.setStatus(jsonBooking.getInt("status"));
                bookings.add(booking);
            } catch (org.json.JSONException je) {
                System.err.println("Empty student number error. Id: " +
                                   jsonBooking.getInt("jobSeekerId"));
            }
        }
        return bookings;
    }

    public List<String> getUnspecified(String eventKey) throws MalformedURLException, IOException {
        List<String> unspecifiedNumbers = new ArrayList<String>();
        JSONObject bookingData = new JSONObject(HttpUtils.getDataFromURL(QUERY_URL + eventKey));
        JSONArray jsonBookings = bookingData.getJSONArray("bookings");
        for (int i=0; i < jsonBookings.length(); i++) {
            JSONObject jsonBooking = jsonBookings.getJSONObject(i);
            int status = jsonBooking.getInt("status");
            if (status == UNSPECIFIED_STATUS) {
                String stuNumber = Integer.toString(jsonBooking.getInt("id"));
                unspecifiedNumbers.add(stuNumber);
            }
        }
        return unspecifiedNumbers;
    }

    public List<Student> getWaitingList(String eventKey) throws MalformedURLException, IOException {
        List<Student> waitingList = new ArrayList<Student>();
        Document doc = Jsoup.connect(WAITING_LIST_BASE + eventKey).timeout(0).get();
        Elements linkElems = doc.select("#ctl00_ctl00_mainContent_mainContent_grid > tbody > tr > td:nth-child(2) > a");
        for (Element link : linkElems) {
            String url = link.attr("href");
            int id = Integer.parseInt(url.split("id=")[1]);
            Student waiting = new Student();
            waiting.setId(id);
            waitingList.add(waiting);
        }
        return waitingList;
    }

    public int getAttendeeCount(String eventKey) throws MalformedURLException, IOException {
        int count = 0;
        JSONObject bookingData = new JSONObject(HttpUtils.getDataFromURL(QUERY_URL + eventKey));
        JSONArray jsonBookings = bookingData.getJSONArray("bookings");
        for (int i=0; i < jsonBookings.length(); i++) {
            JSONObject jsonBooking = jsonBookings.getJSONObject(i);
            count = jsonBooking.getInt("status") == getATTENDED_STATUS() ? count + 1 : count;
        }
        return count;
    }

    public void markStatus(STATUS status, String studentKey, String eventKey) throws MalformedURLException, IOException {
        List<String> key = Arrays.asList(studentKey);
        markStatus(status, key, eventKey);
    }

    public void markStatus(STATUS status, List<String> studentKeys, String eventKey) throws MalformedURLException, IOException {
        Map<String,String> requestHeaders = new HashMap<String,String>();
        requestHeaders.put("Content-Type", "application/json;charset=" + this.getCharset());
        String postData = "{\"eventID\":" + eventKey + "," +
                          "\"ids\":" + studentKeys;
        String url = "";
        switch (status) {
            case ATTENDED:
                url = MARK_ATTENDED_URL;
                break;
            case ABSENT:
                url = MARK_ABSENT_URL;
                postData += ",\"notify\":false";
                break;
            default:
                url = MARK_UNSPECIFIED_URL;
                break;
        }
        postData += "}";
        try {
            HttpUtils.sendDataToURL(url, "POST", postData, this.getCharset(), requestHeaders);
        }
        catch (IOException ioe) {
            if (ioe.getMessage().equals("Server returned HTTP response code: 400 for URL: " +
                                        MARK_ATTENDED_URL)) {
                throw new EarlyRegistrationException();
            }
        }
    }

    public void markAbsent(List<String> studentKeys, String eventKey, Boolean notify) throws MalformedURLException, IOException {
        Map<String,String> requestHeaders = new HashMap<String,String>();
        requestHeaders.put("Content-Type", "application/json;charset=" + this.getCharset());
        String postData = "{\"eventID\":" + eventKey + "," +
                          "\"ids\":" + studentKeys + "," +
                          "\"notify\":" + notify + "}";
        String url = MARK_ABSENT_URL;
        HttpUtils.sendDataToURL(url, "POST", postData, this.getCharset(), requestHeaders);
    }

    public void markAllUnspecifiedAbsent(String eventKey, Boolean notify) throws MalformedURLException, IOException {
        List<String> unspecifiedKeys = this.getUnspecified(eventKey);
        if (!unspecifiedKeys.isEmpty())
            this.markAbsent(unspecifiedKeys, eventKey, notify);
    }

    public void cancelBooking(String studentKey, String eventKey) throws MalformedURLException, IOException {
        Map<String,String> requestHeaders = new HashMap<String,String>();
        requestHeaders.put("Content-Type", "application/json;charset=" + getCharset());
        String postData = "{\"eventID\":" + eventKey + "," +
                           "\"ids\":[" + studentKey + "]," +
                           "\"notify\":false}";
        HttpUtils.sendDataToURL(CANCEL_URL, "POST", postData, getCharset(), requestHeaders);
    }

    public Booking bookStudent(String studentID, String eventKey) throws MalformedURLException, IOException {
        Map<String,String> requestHeaders = new HashMap<String,String>();
        requestHeaders.put("Content-Type", "application/json;charset=" + getCharset());
        String putData = "{\"eventID\":" + eventKey + "," +
                          "\"jobSeekerID\":" + studentID + "," +
                          "\"notify\":false}";
        String bookingDetails = HttpUtils.sendDataToURL(BOOKING_URL, "PUT", putData, getCharset(), requestHeaders);
        JSONObject jsonBooking = (JSONObject) new JSONObject(bookingDetails).get("booking");
        Booking booking = new Booking(jsonBooking.getString("externalId"));
        booking.setFirstName(jsonBooking.getString("firstName"));
        booking.setLastName(jsonBooking.getString("lastName"));
        booking.setId(jsonBooking.getInt("jobSeekerId"));
        booking.setBookingId(jsonBooking.getInt("id"));
        booking.setStatus(jsonBooking.getInt("status"));
        return booking;
    }

    public Student getStudent(String stuNumber) throws MalformedURLException, IOException {
        String query = "?s=" + stuNumber +
                       "&type=JobSeeker&maxResults=1&current=Current&active=true";
        String stuData = HttpUtils.getDataFromURL(STUDENT_SEARCH_BASE + query);
        JSONObject jsonStudent = null;
        try {
            jsonStudent = new JSONArray(stuData).getJSONObject(0);
        } catch (org.json.JSONException je) {
            throw new NoStudentFoundException(stuNumber);
        }
        Student student = new Student();
        student.setStuNumber(stuNumber.toString());
        student.setFirstName(jsonStudent.getString("FirstName"));
        student.setLastName(jsonStudent.getString("LastName"));
        student.setId(jsonStudent.getInt("Id"));
        return student;
    }

    public List<Student> getStudents(String search) throws MalformedURLException, IOException {
        List<Student> students = new ArrayList<Student>();
        String query = "?s=" + search +
                       "&maxResults=100&current=Current&active=true";
        String stuData = HttpUtils.getDataFromURL(STUDENT_SEARCH_BASE + query);
        JSONArray jsonStudents = new JSONArray(stuData);
        for (int i=0; i < jsonStudents.length(); i++) {
            JSONObject jsonStudent = jsonStudents.getJSONObject(i);
            Student student = new Student();
            student.setFirstName(jsonStudent.getString("FirstName"));
            student.setLastName(jsonStudent.getString("LastName"));
            student.setId(jsonStudent.getInt("Id"));
            String stuNum = getEmptyStuNumString();
            try {
                stuNum = (String) jsonStudent.get("ExternalId");
            } catch (java.lang.ClassCastException ex) {
                System.err.println("Empty student number error. Student id: " + student.getId());
            }
            student.setStuNumber(stuNum);
            students.add(student);
        }
        return students;
    }

    public List<Event> getEventsList() throws MalformedURLException, IOException {
        List<Event> events = new ArrayList<Event>();
        Map<String,String> requestHeaders = new HashMap<String,String>();
        requestHeaders.put("Authorization", "Bearer " + this.getAPIToken("Integrations.Events"));
        String response = HttpUtils.getDataFromURL(EVENT_API_LIST_URL, requestHeaders);
        JSONArray jsonEvents = new JSONArray(response);
        for (int i=0; i < jsonEvents.length(); i++) {
            JSONObject jsonEvent = jsonEvents.getJSONObject(i);
            String title = jsonEvent.getString("name");
            String startDate = jsonEvent.getString("start");
            String id = JSONObject.numberToString(jsonEvent.getInt("entityId"));
            Event event = new Event(title, startDate, id);
            event.setVenue(jsonEvent.getString("venue"));
            events.add(event);
        }
        return events;
    }

    public Event getEvent(String eventKey) throws IOException {
        Map<String,String> requestHeaders = new HashMap<String,String>();
        requestHeaders.put("Authorization", "Bearer " + this.getAPIToken("Integrations.Events"));
        String response = "";
        Event event = new Event();
        response = HttpUtils.getDataFromURL(EVENT_API_URL + eventKey, requestHeaders);
        JSONObject jsonEvent = new JSONObject(response);
        String title = jsonEvent.getString("name");
        String startDate = jsonEvent.getString("start");
        String venue = jsonEvent.getString("venue");
        event = new Event(title, startDate, eventKey);
        startDate = this.prepareActiveDateStr(startDate);
        event.setStartDate(Utils.strToDate(startDate, ACTIVE_DATE_FORMAT));
        event.setVenue(venue);
        int regPeriod = Calendar.getInstance().getTimeZone().useDaylightTime() ?
            120 : 60;
        event.setRegStart(Utils.subtractMins(event.getStartDate(), regPeriod));
        event.setBookingLimit(0);
        Integer bookingType = jsonEvent.getInt("bookingType");
        if (bookingType == 1) { //CH booking
            JSONObject settings = jsonEvent.getJSONObject("bookingSettings");
            if (settings.isNull("bookingLimit")) {
                event.setUnlimited(true);
            }
            else {
                event.setUnlimited(false);
                event.setBookingLimit(settings.getInt("bookingLimit"));
            }
        }
        else {
            event.setDropIn(true);
        }
        JSONObject attendance = jsonEvent.getJSONObject("attendance");
        event.setAttendeeCount(attendance.getInt("attended"));
        return event;
    }

    private String getVerificationToken() throws IOException {
        String token = "";
        Document doc = Jsoup.connect(LOGIN_URL).timeout(0).get();
        Element tokenInput = doc.select("input[name=__RequestVerificationToken]").get(0);
        token = tokenInput.val();
        return token;
    }

    public String getAdminEventURL(String eventKey) {
        return EVENT_ADMIN_URL_BASE + eventKey;
    }

    public String getCharset() {
        return charset;
    }

    public String getEmptyStuNumString() {
        return "";
    }

    public int getATTENDED_STATUS() {
        return ATTENDED_STATUS;
    }

    public int getEVENT_FULL_STATUS() {
        return EVENT_FULL_STATUS;
    }

    public boolean isValidStuNum(String stuNum) {
        return stuNum.matches(STU_NUM_PATTERN);
    }

    public boolean isValidId(String id) {
        return Utils.isNumeric(id);
    }

    public final int ATTENDED_STATUS = 1;
    public final int UNSPECIFIED_STATUS = 0;
    public final int EVENT_FULL_STATUS = -1;

    private String prepareActiveDateStr(String str) {
        return str.replaceAll(":(\\d\\d)$", "$1");
    }

    private String HOST;
    private String API_ID;
    private String SECRET;
    private String STU_NUM_PATTERN;

    public String ADMIN_URL;

    public String LOGIN_URL;
    public String QUERY_URL;
    public String BOOKING_URL;
    public String MARK_ATTENDED_URL;
    public String MARK_UNSPECIFIED_URL;
    public String MARK_ABSENT_URL;
    public String CANCEL_URL;
    public String WAITING_LIST_BASE;
    public String STUDENT_SEARCH_BASE;
    public String EVENT_API_URL;
    public String EVENT_API_LIST_URL;
    public String EVENT_API_SEARCH_URL;
    public String EVENT_ADMIN_URL_BASE;

    private final String ACTIVE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    private final String charset = "UTF-8";
    private final String AUTH_COOKIE_NAME = ".CHAUTH";

    private Map<String,AccessToken> tokens = new HashMap<String,AccessToken>();
    private static BookingSystemAPI instance = null;

}