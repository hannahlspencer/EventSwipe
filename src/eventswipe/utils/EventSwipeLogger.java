package eventswipe.utils;

import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author wildmanm
 */
public class EventSwipeLogger {
    
   /**
     * Singleton constructor for EventSwipeData
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
        sessionTitle = title + Utils.getDate("dd-MM-yyyy HHmmss");
        if(!logDir.exists()) {
            logDir.mkdirs();
        }
        try {
            logFile = new File(LOG_DIR, sessionTitle);
            FileWriter fw = new FileWriter(logFile.getAbsoluteFile(), true);
            fw.write(sessionTitle + System.getProperty("line.separator"));
            fw.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void log(String message) {
        try {
            FileWriter fw = new FileWriter(logFile.getAbsoluteFile(), true);
            fw.write(message + " @ " + 
                     Utils.getDate("dd/MM/yyyy HH:mm:ss") +
                     System.getProperty("line.separator"));
            fw.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private final String LOG_DIR = System.getenv("USERPROFILE")+ "\\My Documents\\EventSwipeLogs\\";
    private String sessionTitle = "";

    private File logDir = new File(LOG_DIR);
    private File logFile;

    private static EventSwipeLogger instance = null;

}
