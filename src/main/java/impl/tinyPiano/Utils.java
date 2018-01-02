package impl.tinyPiano;

import piano.Piano;

import java.awt.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static Pattern NOTE_PATTERN = Pattern.compile("^[CDEFGAB]#?");
    private static Pattern OCTAVE_PATTERN = Pattern.compile("[0-8]$");

    static boolean isBlack(String key) {
        return key.contains(Constants.SHARP);
    }

    private static String getMatch(Pattern p, String s) {
        Matcher m = p.matcher(s);
        m.find();
        return m.group();
    }

    public static String next(String key) {
        String note = getMatch(NOTE_PATTERN, key);
        String octave = getMatch(OCTAVE_PATTERN, key);
        if ("B".equals(note)) {
            octave = String.valueOf(Integer.valueOf(octave) + 1);
        }
        String nextNote = null;
        switch (note) {
            case "C":
            case "D":
            case "F":
            case "G":
            case "A": nextNote = note + Constants.SHARP; break;
            case "E": nextNote = "F"; break;
            case "B": nextNote = "C"; break;
            case "C#": nextNote = "D"; break;
            case "D#": nextNote = "E"; break;
            case "F#": nextNote = "G"; break;
            case "G#": nextNote = "A"; break;
            case "A#": nextNote = "B"; break;
        }
        return nextNote + octave;
    }

    static boolean isWithinRectangle(int x, int y, Rectangle r) {
        return (x > r.x) && (x < r.x + r.width) && (y > r.y) && (y < r.y + r.height);
    }

    public static String getRandomKey(Piano piano) {
        int n = new Random().nextInt(piano.getNumberOfKeys());
        String key = piano.getFirstKey();
        for(int i = 0; i < n; i++) {
            key = Utils.next(key);
        }

        return key;
    }
}
