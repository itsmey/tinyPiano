package impl.tinyPiano;

import piano.Piano;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
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

    static int compareKeys(String key1, String key2) {
        if (key1.equals(key2))
            return 0;

        String octave1 = getMatch(OCTAVE_PATTERN, key1);
        String octave2 = getMatch(OCTAVE_PATTERN, key2);
        if (!octave1.equals(octave2))
            return octave1.compareTo(octave2);

        String note1 = getMatch(NOTE_PATTERN, key1);
        String note2 = getMatch(NOTE_PATTERN, key2);
        final List<String> order = Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B");
            return order.indexOf(note1) - order.indexOf(note2);
    }

    public static int getInterval(String key1, String key2) {
        int compareResult = compareKeys(key1, key2);
        if (compareResult == 0)
            return 0;

        String lowerKey = compareResult > 0 ? key2 : key1;
        String higherKey = compareResult < 0 ? key2 : key1;

        int semitones = 0;
        while (!lowerKey.equals(higherKey)) {
            lowerKey = next(lowerKey);
            semitones++;
        }

        return semitones;
    }
}
