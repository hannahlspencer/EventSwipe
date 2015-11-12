package eventswipe.utils;

import java.io.File;
import javax.swing.filechooser.*;

/**
 *
 * @author wildmanm
 */
public class TextCSVFilter extends FileFilter {

    public final static String csv = "csv";
    public final static String txt = "txt";

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals(txt) ||
                extension.equals(csv)) {
                    return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "Text and CSV files";
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

}
