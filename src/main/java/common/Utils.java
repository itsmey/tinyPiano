package common;

import java.awt.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    static String getMatch(Pattern p, String s) {
        Matcher m = p.matcher(s);
        m.find();
        return m.group();
    }

    public static boolean isWithinRectangle(int x, int y, Rectangle r) {
        return (x > r.x) && (x < r.x + r.width) && (y > r.y) && (y < r.y + r.height);
    }
}