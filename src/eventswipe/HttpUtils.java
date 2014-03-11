package eventswipe;

import eventswipe.BookingSystemAPI.BookingSystemServices;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtils {

    public static CookieManager cm;

    public static void setCookiePolicy() {
        cm = new CookieManager();
        cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cm);
    }

    public static HttpURLConnection connectToService(BookingSystemServices serviceKey,
                                                     String method,
                                                     Map<String, String> headers) throws MalformedURLException, IOException {
        if (ConnectionManager.isConnected(serviceKey)) {
            return ConnectionManager.getConnection(serviceKey);
        }
        else {
            HttpURLConnection connection = connectToURL(EventSwipeData.getURL(serviceKey), method, headers);
            ConnectionManager.addConnection(serviceKey, connection);
            return connection;
        }
    }

    public static HttpURLConnection connectToURL(String url, 
                                                 String method,
                                                 Map<String, String> headers) throws MalformedURLException, IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        if (!headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        connection.connect();
        return connection;
    }

    public static HttpURLConnection sendDataToService(BookingSystemServices serviceKey,
                                         String method,
                                         String data,
                                         String charset,
                                         Map<String,String> headers) throws MalformedURLException, IOException {
        HttpURLConnection connection = connectToService(serviceKey, method, headers);
        OutputStream output = connection.getOutputStream();
        output.write(data.getBytes(charset));
        output.close();
        //need to explicitly request headers to set session cookie
        Map responseHeaders = connection.getHeaderFields();        
        return connection;
    }

    public static String getDataFromURL(String url) throws MalformedURLException, IOException {
        HttpURLConnection connection = connectToURL(url, "GET", new HashMap());
        InputStream response = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(response));
        String inputLine;
        String responseData = "";
        while ((inputLine = in.readLine()) != null) {
            responseData += inputLine;
        }
        in.close();
        return responseData;
    }

}
