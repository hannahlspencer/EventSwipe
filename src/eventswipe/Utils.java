package eventswipe;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mozilla.universalchardet.UniversalDetector;

/**
 *
 * @author Matt Wildman
 */
public class Utils {

    public static void pressAlt() {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_ALT);
        } catch (AWTException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void releaseAlt() {
        try {
            Robot r = new Robot();
            r.keyRelease(KeyEvent.VK_ALT);
        } catch (AWTException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static String readLine(File file, String encoding) {
        String line = "";
        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fstream);
            InputStreamReader isr = new InputStreamReader(dis, encoding);
            BufferedReader br = new BufferedReader(isr);
            line = br.readLine();
            line = encoding.equals("UTF8") ? Utils.removeUTF8BOM(line) : line;
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line;
    }

    public static String getEncoding(File file) {
        byte[] data = new byte[4096];
        UniversalDetector detector = new UniversalDetector(null);
        int readCount;
        try {
            FileInputStream fstream = new FileInputStream(file);
            while ((readCount = fstream.read(data)) > 0 && !detector.isDone()) {
                detector.handleData(data, 0, readCount);
            }
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        detector.reset();
        return encoding;
    }

     private static String removeUTF8BOM(String s) {
        if (s.startsWith(UTF8_BOM)) {
            s = s.substring(1);
        }
        return s;
    }

     private static final String UTF8_BOM = "\uFEFF";

}
