/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import eventswipe.utils.EventSwipeLogger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wildmanm
 */
public class EventSwipeTests {

    public EventSwipeTests() {
        testStr = "LSE Careers Banking and Financial Services Fair 2015, Night Two, 5:30-6:45pm";
        logger = EventSwipeLogger.getInstance();
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
    public void loggingTest() {
        logger.createLog(testStr);
    }

    @Test
    public void regexTest() {
        String result = testStr.replaceAll("\\W", "");
        assertEquals("Result is not in correct format", result, "LSECareersBankingandFinancialServicesFair2015NightTwo530645pm");
    }

    String testStr;
    EventSwipeLogger logger;

}