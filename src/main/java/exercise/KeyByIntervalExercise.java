package exercise;

import common.Interval;
import common.Key;
import common.L10n;
import common.Utils;
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
                messageLabel.setText(Utils.getLocalizedText(L10n.CORRECT));
            } else {
                stats.mistake();
                int difference = Key.distance(keyToGuess, key);
                messageLabel.setText(L10n.construct(Utils.getLocalizedText(L10n.YOU_ARE_MISTAKEN), Math.abs(difference)));
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
        Interval interval = Interval.random(piano, frame.getIntervalsList());
        String baseKey;
        String higherOrLower;
        if (new Random().nextBoolean()) {
            baseKey = interval.getHigherKey();
            keyToGuess = interval.getLowerKey();
            higherOrLower = Utils.getLocalizedText(L10n.LOWER);
        } else {
            baseKey = interval.getLowerKey();
            keyToGuess = interval.getHigherKey();
            higherOrLower = Utils.getLocalizedText(L10n.HIGHER);
        }

        taskLabel.setText(L10n.construct(Utils.getLocalizedText(L10n.PLAY_NOTE_WHICH),
                higherOrLower, Key.title(baseKey), interval.title(), Math.abs(interval.distance())));

        logger.info("next: base key - " + baseKey + ", key to guess - " + keyToGuess + ", interval - " + interval);
    }

    @Override
    public String toString() {
        return Utils.getLocalizedText(L10n.KEY_BY_INTERVAL_EXERCISE);
    }
}
