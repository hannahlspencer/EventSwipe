import java.io.File;
import eventswipe.Utils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matt Wildman
 */
public class FileEncodingTest {

    private static String testFileLocation, messageBase;
    private static File ansiFile, unicodeFile, utf8File, unicodeBeFile;

    public FileEncodingTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        testFileLocation = "c:/users/wildmanm/desktop/";
        messageBase = "This is a test file encoded in ";
        ansiFile = new File(testFileLocation + "ansi.txt");
        unicodeFile = new File(testFileLocation + "unicode.txt");
        unicodeBeFile = new File(testFileLocation + "unicode_be.txt");
        utf8File = new File(testFileLocation + "utf-8.txt");
        String testStr = Utils.readLine(ansiFile, "Cp1252");
        System.out.println(testStr);
        testStr = Utils.readLine(unicodeFile, "Cp1252");
        System.out.println(testStr);
        testStr = Utils.readLine(unicodeBeFile, "Cp1252");
        System.out.println(testStr);
        testStr = Utils.readLine(utf8File, "Cp1252");
        System.out.println(testStr);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {}

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}

    @Test
    public void detectCorrectEncodingTest() {
        String ansiEncoding = Utils.getEncoding(ansiFile);
        String unicodeEncoding = Utils.getEncoding(unicodeFile);
        String utf8Encoding = Utils.getEncoding(utf8File);
        assertEquals("Incorrect encoding detected", "Cp1252", ansiEncoding);
        assertEquals("Incorrect encoding detected", "UTF-16", unicodeEncoding);
        assertEquals("Incorrect encoding detected", "UTF8", utf8Encoding);
    }

    @Test
    public void ansiReadTest() {
        String filePath = testFileLocation + "ansi.txt";
        File file = new File(filePath);
        String testStr = Utils.readLine(file, "Cp1252");
        System.out.println(testStr);
        assertEquals("File was read incorrectly", messageBase + "ANSI", testStr);
    }

    @Test
    public void unicodeReadTest() {
        String filePath = testFileLocation + "unicode.txt";
        File file = new File(filePath);
        String testStr = Utils.readLine(file, "UTF-16");
        System.out.println(testStr);
        assertEquals("File was read incorrectly", messageBase + "Unicode", testStr);
    }

    @Test
    public void unicodeBeReadTest() {
        String filePath = testFileLocation + "unicode_be.txt";
        File file = new File(filePath);
        String testStr = Utils.readLine(file, "UTF-16");
        System.out.println(testStr);
        assertEquals("File was read incorrectly", messageBase + "Unicode big endian", testStr);
    }

    @Test
    public void utf8ReadTest() {
        String filePath = testFileLocation + "utf-8.txt";
        File file = new File(filePath);
        String testStr = Utils.readLine(file, "UTF8");
        System.out.println(testStr);
        assertEquals("File was read incorrectly", messageBase + "UTF-8", testStr);
    }

    @Test
    public void detectAndDecodeTest() {
        String encoding1 = Utils.getEncoding(ansiFile);
        String line1 = Utils.readLine(ansiFile, encoding1);
        assertEquals("File decoded incorrectly", messageBase + "ANSI", line1);

        String encoding2 = Utils.getEncoding(utf8File);
        String line2 = Utils.readLine(utf8File, encoding2);
        assertEquals("File decoded incorrectly", messageBase + "UTF-8", line2);

        String encoding3 = Utils.getEncoding(unicodeFile);
        String line3 = Utils.readLine(unicodeFile, encoding3);
        assertEquals("File decoded incorrectly", messageBase + "Unicode", line3);

        String encoding4 = Utils.getEncoding(unicodeBeFile);
        String line4 = Utils.readLine(unicodeBeFile, encoding4);
        assertEquals("File decoded incorrectly", messageBase + "Unicode big endian", line4);
    }

}