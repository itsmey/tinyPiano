package impl.tinyPiano;

import piano.Piano;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static Pattern NOTE_PATTERN = Pattern.compile("^[CDEFGAB][#b]?");
    private static Pattern OCTAVE_PATTERN = Pattern.compile("[0-8]$");

    static boolean isBlack(String key) {
        return normalize(key).contains(Constants.SHARP);
    }

    static boolean isSharp(String key) {
        return key.contains(Constants.SHARP);
    }

    static boolean isFlat(String key) {
        return key.contains(Constants.FLAT);
    }

    private static String getMatch(Pattern p, String s) {
        Matcher m = p.matcher(s);
        m.find();
        return m.group();
    }

    public static String next(String key) {
        key = normalize(key);

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

    public static String previous(String key) {
        key = normalize(key);

        String note = getMatch(NOTE_PATTERN, key);
        String octave = getMatch(OCTAVE_PATTERN, key);
        if ("C".equals(note)) {
            octave = String.valueOf(Integer.valueOf(octave) - 1);
        }
        String previousNote = null;
        switch (note) {
            case "C#":
            case "D#":
            case "F#":
            case "G#":
            case "A#": previousNote = note.substring(0,1); break;
            case "E": previousNote = "D#"; break;
            case "B": previousNote = "A#"; break;
            case "C": previousNote = "B"; break;
            case "D": previousNote = "C#"; break;
            case "F": previousNote = "E"; break;
            case "G": previousNote = "F#"; break;
            case "A": previousNote = "G#"; break;
        }
        return previousNote + octave;
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

        if (isSharp(key)) {
            if (new Random().nextInt(100) < 50)
                key = sharpToFlat(key);
        }

        return key;
    }

    static int compareKeys(String key1, String key2) {
        key1 = normalize(key1);
        key2 = normalize(key2);

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
        key1 = normalize(key1);
        key2 = normalize(key2);

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

    public static String sharpToFlat(String key) {
        if (!isSharp(key))
            throw new IllegalArgumentException();

        String note = getMatch(NOTE_PATTERN, key);
        String octave = getMatch(OCTAVE_PATTERN, key);

        switch (note) {
            case "C#": return "Db" + octave;
            case "D#": return "Eb" + octave;
            case "F#": return "Gb" + octave;
            case "G#": return "Ab" + octave;
            case "A#": return "Bb" + octave;
        }

        throw new IllegalStateException();
    }

    public static String normalize(String key) {
        String note = getMatch(NOTE_PATTERN, key);
        String octave = getMatch(OCTAVE_PATTERN, key);

        switch (note) {
            case "Db": return "C#" + octave;
            case "Eb": return "D#" + octave;
            case "Gb": return "F#" + octave;
            case "Ab": return "G#" + octave;
            case "Bb": return "A#" + octave;
        }

        return key;
    }

    public static String keyToString(String key) {
        String note = getMatch(NOTE_PATTERN, key);
        String octave = getMatch(OCTAVE_PATTERN, key);

        String text = "";

        switch (note) {
            case "C": text += "до "; break;
            case "D": text += "ре "; break;
            case "F": text += "фа "; break;
            case "G": text += "соль "; break;
            case "A": text += "ля "; break;
            case "E": text += "ми "; break;
            case "B": text += "си "; break;
            case "C#": text += "до-диез "; break;
            case "D#": text += "ре-диез "; break;
            case "F#": text += "фа-диез "; break;
            case "G#": text += "соль-диез "; break;
            case "A#": text += "ля-диез "; break;
            case "Db": text += "ре-бемоль "; break;
            case "Eb": text += "ми-бемоль "; break;
            case "Gb": text += "соль-бемоль "; break;
            case "Ab": text += "ля-бемоль "; break;
            case "Bb": text += "си-бемоль "; break;
        }

        switch (octave) {
            case "0": text += "субконтроктавы "; break;
            case "1": text += "контроктавы "; break;
            case "2": text += "большой октавы "; break;
            case "3": text += "малой октавы "; break;
            case "4": text += "первой октавы "; break;
            case "5": text += "второй октавы "; break;
            case "6": text += "третьей октавы "; break;
            case "7": text += "четвертой октавы "; break;
            case "8": text += "пятой октавы "; break;
        }

        return text += "(" + key + ")";
    }

    public static int getRandomInterval() {
        return new Random().nextInt(13);
    }

    public static String addInterval(String key, int semitones) {
        boolean plus = semitones > 0;
        semitones = Math.abs(semitones);
        while (semitones > 0) {
            if (plus)
                key = next(key);
            else
                key = previous(key);
            semitones--;
        }

        return key;
    }

    public static String intervalToString(int interval) {
        switch (Math.abs(interval)) {
            case 0: return "чистую приму";
            case 1: return "малую секунду";
            case 2: return "большую секунду";
            case 3: return "малую терцию";
            case 4: return "большую терцию";
            case 5: return "чистую кварту";
            case 6: return "тритон";
            case 7: return "чистую квинту";
            case 8: return "малую сексту";
            case 9: return "большую сексту";
            case 10: return "малую септиму";
            case 11: return "большую септиму";
            case 12: return "читую октаву";
        }

        throw new IllegalStateException();
    }
}
