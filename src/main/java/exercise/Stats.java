package exercise;

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
        return String.format("Правильных: %d, ошибок: %d, процент правильных: %d",
                correct, total - correct, getPercentage());
    }
}
