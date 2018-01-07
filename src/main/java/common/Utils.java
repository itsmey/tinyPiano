package common;

import java.awt.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class.getName());
    private static final String BUNDLE = "bundle.lang";

    static String getMatch(Pattern p, String s) {
        Matcher m = p.matcher(s);
        m.find();
        return m.group();
    }

    public static boolean isWithinRectangle(int x, int y, Rectangle r) {
        return (x > r.x) && (x < r.x + r.width) && (y > r.y) && (y < r.y + r.height);
    }

    public static String getLocalizedText(String fieldName) {
        return ResourceBundle.getBundle(BUNDLE).getString(fieldName);
    }
}