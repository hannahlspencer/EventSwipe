package eventswipe;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

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

}
