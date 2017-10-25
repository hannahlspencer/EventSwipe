/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author wildmanm
 */
public class SoundTest {

    public SoundTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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
    public void toolkitTest() {
        try {
            Toolkit.getDefaultToolkit().beep();
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SoundTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void windowsSoundsTest() {
        try {
            Runnable fail = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand");
            if (fail != null) {
                fail.run();
            }
            Thread.sleep(1000);
            Runnable success = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.asterisk");
            if (success != null) {
                success.run();
            }
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SoundTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}