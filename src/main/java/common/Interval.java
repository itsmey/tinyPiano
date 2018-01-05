package common;

import piano.Piano;

import java.util.Random;

public class Interval {
    private static final int N_INTERVALS = 13;

    private String lowerKey;
    private String higherKey;

    private Interval() {}

    private Interval(String key1, String key2) {
        if (Key.compareKeys(key1, key2) > 0) {
            higherKey = key1;
            lowerKey = key2;
        } else {
            higherKey = key2;
            lowerKey = key1;
        }
    }

    public static Interval random(Piano piano) {
        String key1 = Key.random(piano);
        int distance = new Random().nextInt(N_INTERVALS);
        if (new Random().nextBoolean()) distance = -distance;

        if ((distance < 0) && (Key.distance(piano.getFirstKey(), key1) < Math.abs(distance)))
            distance = -distance;

        if ((distance > 0) && (Key.distance(piano.getLastKey(), key1) < distance))
            distance = -distance;

        String key2 = Key.transpose(key1, distance);

        return new Interval(key1, key2);
    }

    public static Interval randomButNotUnison(Piano piano) {
        while (true) {
            Interval interval = random(piano);
            if (Key.compareKeys(interval.higherKey, interval.lowerKey) != 0)
                return interval;
        }
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
    public String toString() {
        return String.format("from %s to %s (%d) semitones", lowerKey, higherKey, distance());
    }
}
