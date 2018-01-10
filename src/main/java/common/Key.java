package common;

import piano.Piano;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Key implements Comparable<Key>{
    private static final Logger logger = Logger.getLogger(Key.class.getName());

    private static String SHARP = "#";
    private static String FLAT = "b";
    private static List<String> NOTE_ORDER = Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B");

    private static Pattern NOTE_PATTERN = Pattern.compile("^[CDEFGAB][#b]?");
    private static Pattern OCTAVE_PATTERN = Pattern.compile("[0-8]$");

    private String note;
    private int octave;

    private Key(String note, int octave) {
        this.note = note;
        this.octave = octave;
    }

    private Key(String key) {
        note = Utils.getMatch(NOTE_PATTERN, key);
        octave = Integer.valueOf(Utils.getMatch(OCTAVE_PATTERN, key));
    }

    private static Key fromString(String key) {
        return new Key(key);
    }

    public static String getNote(String keyString) {
        return fromString(keyString).normalize().note;
    }

    public static boolean validate(String keyString) {
        try {
            fromString(keyString);
        } catch (IllegalStateException e) {
            return false;
        }
        return true;
    }

    private Key normalize() {
        switch (note) {
            case "Db": note = "C#"; break;
            case "Eb": note = "D#"; break;
            case "Gb": note = "F#"; break;
            case "Ab": note = "G#"; break;
            case "Bb": note = "A#"; break;
        }

        return this;
    }

    public static String normalize(String keyString) {
        return fromString(keyString).normalize().toString();
    }

    private boolean isBlack() { return normalize().note.contains(SHARP); }
    private boolean isSharp() { return note.contains(SHARP); }
    private boolean isFlat() { return note.contains(FLAT); }

    public static boolean isSharp(String keyString) {return fromString(keyString).isSharp(); }
    public static boolean isBlack(String keyString) {return fromString(keyString).isBlack(); }

    private static String nextNote(String note) {
        if ("B".equals(note)) return "C";
        return NOTE_ORDER.get(NOTE_ORDER.indexOf(note) + 1);
    }

    private static String previousNote(String note) {
        if ("C".equals(note)) return "B";
        return NOTE_ORDER.get(NOTE_ORDER.indexOf(note) - 1);
    }

    private Key next() {
        return new Key(nextNote(note), "B".equals(note) ? octave + 1 : octave);
    }

    private Key previous() {
        return new Key(previousNote(note), "C".equals(note) ? octave - 1 : octave);
    }

    public static String next(String keyString) {
        Key key = fromString(keyString).normalize();
        return key.next().toString();
    }

    public static String previous(String keyString) {
        Key key = fromString(keyString).normalize();
        return key.previous().toString();
    }

    private Key sharpToFlat() {
        switch (note) {
            case "C#": return new Key("Db", octave);
            case "D#": return new Key("Eb", octave);
            case "F#": return new Key("Gb", octave);
            case "G#": return new Key("Ab", octave);
            case "A#": return new Key("Bb", octave);
        }
        return this;
    }

    public static String transpose(String base, int semitones) {
        logger.info("transposing " + base + " to " + semitones + " semitones");
        Key key = fromString(base).normalize();

        boolean plus = semitones > 0;
        semitones = Math.abs(semitones);

        while (semitones > 0) {
            logger.info("loop: " + key + ", semitones = " + semitones);
            key = plus ? key.next() : key.previous();
            semitones--;
        }

        logger.info("transpose result: " + key.toString());
        return key.toString();
    }

    public static int distance(String key1String, String key2String) {
        Key key1 = fromString(key1String).normalize();
        Key key2 = fromString(key2String).normalize();

        int semitones = 0;

        if (key1.compareTo(key2) >= 0) {
            while (!key1.equals(key2)) {
                key2 = key2.next();
                semitones++;
            }
        } else {
            while (!key1.equals(key2)) {
                key1 = key1.next();
                semitones++;
            }
        }

        return semitones;
    }

    public static int compareKeys(String key1String, String key2String) {
        return fromString(key1String).normalize().compareTo(fromString(key2String).normalize());
    }

    public static String random(Piano piano) {
        int n = new Random().nextInt(piano.getNumberOfKeys());

        Key key = fromString(piano.getFirstKey()).normalize();

        for(int i = 0; i < n; i++) {
            key = key.next();
        }

        if (key.isSharp()) {
            if (new Random().nextInt(100) < 50)
                key = key.sharpToFlat();
        }

        return key.toString();
    }

    public static String title(String keyString) {
        Key key = fromString(keyString);
        return String.format("%s (%s)", Utils.getLocalizedText(L10n.NOTES.get(key.note)),
                Utils.getLocalizedText(L10n.OCTAVES.get(key.octave)));
    }

    @Override
    public int compareTo(Key k) {
        if (octave != k.octave)
            return octave - k.octave;

        return NOTE_ORDER.indexOf(normalize().note) - NOTE_ORDER.indexOf(k.normalize().note);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;

        if (octave != key.octave) return false;
        return note.equals(key.note);
    }

    @Override
    public int hashCode() {
        int result = note.hashCode();
        result = 31 * result + octave;
        return result;
    }

    @Override
    public String toString() {
        return note + String.valueOf(octave);
    }
}
