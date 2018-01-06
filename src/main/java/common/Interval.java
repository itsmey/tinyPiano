package common;

import piano.Piano;

import java.util.*;
import java.util.stream.Collectors;

public class Interval {
    private static final int N_INTERVALS = 13;

    public static Interval UNISON = new Interval("C3", "C3");
    public static Interval MINOR_SECOND = new Interval("C3", "C#3");
    public static Interval MAJOR_SECOND = new Interval("C3", "D3");
    public static Interval MINOR_THIRD = new Interval("C3", "D#3");
    public static Interval MAJOR_THIRD = new Interval("C3", "E3");
    public static Interval FOURTH = new Interval("C3", "F3");
    public static Interval TRITONE = new Interval("C3", "F#3");
    public static Interval FIFTH = new Interval("C3", "G3");
    public static Interval MINOR_SIXTH = new Interval("C3", "G#3");
    public static Interval MAJOR_SIXTH = new Interval("C3", "A3");
    public static Interval MINOR_SEVENTH = new Interval("C3", "A#3");
    public static Interval MAJOR_SEVENTH = new Interval("C3", "B3");
    public static Interval OCTAVE = new Interval("C3", "C4");

    public static List<Interval> getAll() {
        List<Interval> list = new ArrayList<>();
        list.add(UNISON);
        list.add(MINOR_SECOND);
        list.add(MAJOR_SECOND);
        list.add(MINOR_THIRD);
        list.add(MAJOR_THIRD);
        list.add(FOURTH);
        list.add(TRITONE);
        list.add(FIFTH);
        list.add(MINOR_SIXTH);
        list.add(MAJOR_SIXTH);
        list.add(MINOR_SEVENTH);
        list.add(MAJOR_SEVENTH);
        list.add(OCTAVE);
        return list;
    }

    private String lowerKey;
    private String higherKey;

    private Interval(String key1, String key2) {
        if (Key.compareKeys(key1, key2) > 0) {
            higherKey = key1;
            lowerKey = key2;
        } else {
            higherKey = key2;
            lowerKey = key1;
        }
    }

    private static Interval get(Piano piano, int distance) {
        String key1 = Key.random(piano);
        if (new Random().nextBoolean())
            distance = -distance;

        if ((distance < 0) && (Key.distance(piano.getFirstKey(), key1) < Math.abs(distance)))
            distance = -distance;

        if ((distance > 0) && (Key.distance(piano.getLastKey(), key1) < distance))
            distance = -distance;

        String key2 = Key.transpose(key1, distance);

        return new Interval(key1, key2);
    }

    public static Interval random(Piano piano) {
        return get(piano, new Random().nextInt(N_INTERVALS));
    }

    public static Interval randomButNotUnison(Piano piano) {
        return randomButNotUnison(piano, getAll());
    }

    public static Interval randomButNotUnison(Piano piano, List<Interval> settings) {
        List<Interval> list = settings;
        list.remove(UNISON);
        return random(piano, list);
    }

    public static Interval random(Piano piano, List<Interval> settings) {
        List<Integer> distances = settings.stream().map(Interval::distance).collect(Collectors.toList());
        int randomDistance = distances.get(new Random().nextInt(distances.size()));
        return get(piano, randomDistance);
    }

    public int distance() {
        return Key.distance(lowerKey, higherKey);
    }

    public String getLowerKey() {
        return lowerKey;
    }

    public String getHigherKey() {
        return higherKey;
    }

    public String title() {
        switch (distance()) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interval interval = (Interval) o;

        if (lowerKey != null ? !lowerKey.equals(interval.lowerKey) : interval.lowerKey != null) return false;
        return higherKey != null ? higherKey.equals(interval.higherKey) : interval.higherKey == null;
    }

    @Override
    public int hashCode() {
        int result = lowerKey != null ? lowerKey.hashCode() : 0;
        result = 31 * result + (higherKey != null ? higherKey.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("from %s to %s (%d) semitones", lowerKey, higherKey, distance());
    }
}
