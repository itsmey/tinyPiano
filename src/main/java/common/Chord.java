package common;

import piano.Piano;

import java.util.*;

public class Chord {
    public enum ChordType {
        MAJOR("maj", 0, 4, 7),
        MINOR("min", 0, 3, 7),
        AUGMENTED("aug", 0, 4, 8),
        DIMINISHED("dim", 0, 3, 6),
        DOMINANT_7TH("dom7", 0, 4, 7, 10),
        MAJOR_7TH("maj7", 0, 4, 7, 11),
        MINOR_7TH("min7", 0, 3, 7, 10),
        SUSPENDED_4TH("sus4", 0, 5, 7),
        SUSPENDED_2TH("sus2", 0, 2, 7);

        private String symbol;
        private int[] intervals;

        ChordType(String symbol, int... intervals) {
            this.symbol = symbol;
            this.intervals = intervals;
        }

        public String getSymbol() {
            return symbol;
        }

        public int[] getIntervals() {
            return intervals;
        }

        public int getInversionsNumber() {
            return intervals.length - 1;
        }

        public int getLength() {
            return intervals[intervals.length - 1];
        }
    }

    private String root;
    private ChordType type;
    private int inversion;
    private List<String> keys = new ArrayList<>();

    public Chord(String root, ChordType type, int inversion) {
        this.root = root;
        this.type = type;
        this.inversion = inversion;

        keys.add(root);
        for(int i = 1; i < type.intervals.length; i++) {
            keys.add(Key.transpose(root, type.intervals[i]));
        }

        for(int i = 1; i <= inversion; i++) {
            String key = keys.remove(0);
            keys.add(key);
        }
    }

    public String getRoot() {
        return root;
    }

    public ChordType getType() {
        return type;
    }

    public int getInversion() {
        return inversion;
    }

    public List<String> getKeys() {
        return keys;
    }

    public static Chord random(Piano piano, List<ChordType> settings, boolean inversionsAllowed) {
        ChordType type = settings.get(new Random().nextInt(settings.size()));

        int inversion = 0;
        if (inversionsAllowed) {
            inversion = new Random().nextInt(type.getIntervals().length);
        }

        Chord chord = new Chord(Key.random(piano), type, inversion);

        while (Key.distance(chord.keys.get(0), piano.getLastKey()) < type.getLength()) {
            chord = new Chord(Key.random(piano), type, inversion);
        }

        return chord;
    }

    public static Chord random(Piano piano, boolean inversionsAllowed) {
        return random(piano, Arrays.asList(ChordType.values()), inversionsAllowed);
    }

    public void highlight(Piano piano) {
        piano.setHighlighted(new HashSet<>());
        for (String key : keys) {
            piano.highlight(key);
        }
    }

    @Override
    public String toString() {
        String inversion = (getInversion() == 0) ? "" : "/" + Key.getNote(getKeys().get(0));
        return root + type.symbol + inversion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chord chord = (Chord) o;

        if (inversion != chord.inversion) return false;
        if (!root.equals(chord.root)) return false;
        return type == chord.type;
    }

    @Override
    public int hashCode() {
        int result = root.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + inversion;
        return result;
    }
}
