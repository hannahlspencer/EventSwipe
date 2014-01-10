package eventswipe;

import java.io.File;
import java.io.FileWriter;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author wildmanm
 */
public class EventSwipeLogger {

    private ResourceMap resourceMap = Application.getInstance(eventswipe.EventSwipeApp.class)
                              .getContext().getResourceMap(EventSwipeLogger.class);

    private String logFileLocation = resourceMap.getString("logFileLocation");
    private String sessionTitle = "";

    private File logDir = new File(logFileLocation);
    private File logFile;
    
    public void createLog(String title) {
        sessionTitle = title + Utils.getDate("dd-MM-yyyy HHmmss");
        if(!logDir.exists()) {
            logDir.mkdir();
        }
        try {
            logFile = new File(logFileLocation, sessionTitle);
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

}
