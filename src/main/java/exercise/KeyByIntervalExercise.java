package exercise;

import frame.MainFrame;
import impl.tinyPiano.Utils;
import piano.PianoKeyListener;

import java.util.Random;

public class KeyByIntervalExercise extends AbstractExercise implements Exercise {
    private String keyToGuess;

    private final PianoKeyListener LISTENER = new PianoKeyListener() {
        @Override
        public void onKeyPressed(String key) {
            if (key.equals(keyToGuess)) {
                stats.correct();
                messageLabel.setText("Правильно!");
            } else {
                stats.mistake();
                int difference = Utils.getInterval(keyToGuess, key);
                messageLabel.setText("Вы ошиблись на " + Math.abs(difference) + " полутонов");
            }
            statusLabel.setText(stats.toString());
            next();
        }
    };

    KeyByIntervalExercise(MainFrame frame) {
        super(frame);
        frame.addExercise(this);
    }

    @Override
    public void start() {
        stats = new Stats();
        piano.addKeyListener(LISTENER);
        piano.setShowKeyLabels(false);
        statusLabel.setText("");
        messageLabel.setText("");
        next();
    }

    @Override
    public void stop() {
        piano.removeKeyListener(LISTENER);
        piano.setShowKeyLabels(true);
    }

    @Override
    public void next() {
        String baseKey = Utils.getRandomKey(piano);
        int interval = Utils.getRandomInterval();
        if (new Random().nextBoolean()) interval = -interval;

        if ((interval < 0) && (Utils.getInterval(piano.getFirstKey(), baseKey) < Math.abs(interval)))
            interval = -interval;

        if ((interval > 0) && (Utils.getInterval(piano.getLastKey(), baseKey) < interval))
            interval = -interval;

        keyToGuess = Utils.addInterval(baseKey, interval);
        String higherOrLower = interval >= 0 ? "выше" : "ниже";
        taskLabel.setText("Задание: сыграйте ноту, которая " + higherOrLower + " ноты " +
                Utils.keyToString(baseKey) + " на " + Utils.intervalToString(interval) + " (" + Math.abs(interval) + " полутонов)");
    }


    @Override
    public String toString() {
        return "Определение недостающей ноты в интервале";
    }
}
