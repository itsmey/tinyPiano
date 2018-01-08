package exercise;

import common.L10n;
import common.Utils;

public class Stats {
    private int total;
    private int correct;

    public int getTotal() {
        return total;
    }

    public int getCorrect() {
        return correct;
    }

    public int getMistakes() {
        return total - correct;
    }

    public void correct() {
        total++;
        correct++;
    }

    public void mistake() {
        total++;
    }

    public int getPercentage() {
        if (total == 0)
            return 0;
        return (int)(correct / (float)total * 100);
    }

    @Override
    public String toString() {
        return L10n.construct(Utils.getLocalizedText(L10n.STATS), correct, total - correct, getPercentage());
    }
}
