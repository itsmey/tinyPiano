package exercise;

import common.Interval;
import common.Key;
import frame.MainFrame;
import piano.PianoKeyListener;

import java.util.Random;
import java.util.logging.Logger;

public class KeyByIntervalExercise extends AbstractExercise implements Exercise {
    private static final Logger logger = Logger.getLogger(KeyByIntervalExercise.class.getName());
    private String keyToGuess;

    private final PianoKeyListener LISTENER = new PianoKeyListener() {
        @Override
        public void onKeyPressed(String key) {
            if (key.equals(keyToGuess)) {
                stats.correct();
                messageLabel.setText("Правильно!");
            } else {
                stats.mistake();
                int difference = Key.distance(keyToGuess, key);
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
        String baseKey = Key.random(piano);
        int interval = Interval.random();
        if (new Random().nextBoolean()) interval = -interval;

        if ((interval < 0) && (Key.distance(piano.getFirstKey(), baseKey) < Math.abs(interval)))
            interval = -interval;

        if ((interval > 0) && (Key.distance(piano.getLastKey(), baseKey) < interval))
            interval = -interval;

        keyToGuess = Key.transpose(baseKey, interval);
        String higherOrLower = interval >= 0 ? "выше" : "ниже";
        taskLabel.setText("Задание: сыграйте ноту, которая " + higherOrLower + " ноты " +
                Key.title(baseKey) + " на " + Interval.title(interval) + " (" + Math.abs(interval) + " полутонов)");

        logger.info("next: base key - " + baseKey + ", key to guess - " + keyToGuess + ", interval - " + interval);
    }


    @Override
    public String toString() {
        return "Определение недостающей ноты в интервале";
    }
}
