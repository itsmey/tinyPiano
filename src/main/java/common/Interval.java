package common;

import java.util.Random;

public class Interval {
    public static int random() {
        return new Random().nextInt(13);
    }

    public static String title(int interval) {
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
