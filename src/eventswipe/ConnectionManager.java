package eventswipe;

import eventswipe.BookingSystemAPI.BookingSystemServices;
import java.net.HttpURLConnection;
import java.util.EnumMap;
import java.util.Map;

public class ConnectionManager {

    public static void clearConnections() {
        connections = new EnumMap<BookingSystemServices, HttpURLConnection>(BookingSystemServices.class);
        statuses.put(BookingSystemServices.LOGIN, false);
        statuses.put(BookingSystemServices.CANCEL, false);
        statuses.put(BookingSystemServices.GET_BOOKINGS_1, false);
        statuses.put(BookingSystemServices.GET_BOOKINGS_2, false);
        statuses.put(BookingSystemServices.GET_BOOKINGS_3, false);
        statuses.put(BookingSystemServices.GET_BOOKINGS_4, false);
        statuses.put(BookingSystemServices.GET_WAITING_LIST, false);
        statuses.put(BookingSystemServices.BOOK, false);
        statuses.put(BookingSystemServices.MARK_ATTENDED, false);
    }

    public static boolean isConnected(BookingSystemServices serviceKey) {
        return statuses.get(serviceKey);
    }

    public static void setConnection(BookingSystemServices serviceKey, boolean connected) {
        statuses.put(serviceKey, connected);
    }
    
    public static void addConnection(BookingSystemServices serviceKey, HttpURLConnection connection) {
        connections.put(serviceKey, connection);
        statuses.put(serviceKey, true);
    }

    public static HttpURLConnection getConnection(BookingSystemServices serviceKey) {
        return connections.get(serviceKey);
    }

    private static Map<BookingSystemServices, Boolean> statuses = new EnumMap<BookingSystemServices, Boolean>(BookingSystemServices.class);
    static {
        statuses.put(BookingSystemServices.LOGIN, false);
        statuses.put(BookingSystemServices.CANCEL, false);
        statuses.put(BookingSystemServices.GET_BOOKINGS_1, false);
        statuses.put(BookingSystemServices.GET_BOOKINGS_2, false);
        statuses.put(BookingSystemServices.GET_BOOKINGS_3, false);
        statuses.put(BookingSystemServices.GET_BOOKINGS_4, false);
        statuses.put(BookingSystemServices.GET_WAITING_LIST, false);
        statuses.put(BookingSystemServices.BOOK, false);
        statuses.put(BookingSystemServices.MARK_ATTENDED, false);
    }

    private static Map<BookingSystemServices, HttpURLConnection> connections = new EnumMap<BookingSystemServices, HttpURLConnection>(BookingSystemServices.class);

}