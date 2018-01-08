package exercise;

import common.Key;
import common.L10n;
import common.Utils;
import frame.MainFrame;
import piano.PianoKeyListener;

import java.util.logging.Logger;

public class KeyByLabelExercise extends AbstractExercise implements Exercise {
    private static final Logger logger = Logger.getLogger(KeyByLabelExercise.class.getName());
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

    KeyByLabelExercise(MainFrame frame) {
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
        keyToGuess = Key.random(piano);
        taskLabel.setText(L10n.construct(Utils.getLocalizedText(L10n.PLAY_NOTE), Key.title(keyToGuess)));
        logger.info("next: " + keyToGuess);
    }

    @Override
    public String toString() {
        return Utils.getLocalizedText(L10n.KEY_BY_LABEL_EXERCISE);
    }
}
