package eventswipe.utils;

import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author wildmanm
 */
public class EventSwipeLogger {
    
   /**
     * Singleton constructor for EventSwipeLogger
    */
    public static EventSwipeLogger getInstance() {
        if (instance == null) {
            instance = new EventSwipeLogger();
        }
        return instance;
    }
    
    protected EventSwipeLogger() {}

    public void createLog(String title) {
        title = title.replaceAll("\\W", "");
        if (title.length() > MAX_FILE_NAME) {
            title = title.substring(0, MAX_FILE_NAME);
        }
        sessionTitle = title + Utils.getDate("dd-MM-yyyy HHmmss");
        if(!logDir.exists()) {
            logDir.mkdirs();
        }
        try {
            logFile = new File(LOG_DIR, sessionTitle);
            FileWriter fw = new FileWriter(logFile.getAbsoluteFile(), true);
            fw.write(sessionTitle + NL);
            fw.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void log(String message) {
        try {
            FileWriter fw = new FileWriter(logFile.getAbsoluteFile(), true);
            fw.write(Utils.getDate("HH:mm:ss dd/MM/yyyy ") + message + NL);
            fw.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public <T extends Exception> void logException(T ex) {
        String errorMsg = "***** ERROR: " + ex.getClass() + " *****" + NL;
        errorMsg += "    " + "\"" + ex.getMessage() + "\"" + NL;
        errorMsg += "    " + "STACK TRACE:" + NL;
        for (StackTraceElement ste : ex.getStackTrace()) {
           errorMsg += "        " + ste.toString() + NL;
        }
        errorMsg += "**********";
        this.log(errorMsg);
    }

    private final String LOG_DIR = System.getenv("USERPROFILE") + "\\My Documents\\EventSwipeLogs\\";
    private final int MAX_FILE_NAME = 100;
    private final String NL = System.getProperty("line.separator");

    private String sessionTitle = "";

    private File logDir = new File(LOG_DIR);
    private File logFile;

    private static EventSwipeLogger instance = null;

}
