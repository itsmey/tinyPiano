package exercise;

import common.Key;
import common.L10n;
import common.Utils;
import frame.MainFrame;
import piano.PianoKeyListener;

import javax.swing.*;
import java.util.logging.Logger;

public class PitchExercise extends AbstractExercise implements Exercise {
    private static final Logger logger = Logger.getLogger(PitchExercise.class.getName());
    private String keyToGuess;
    private String lastKey;
    private JButton repeatButton = new JButton("Звук");
    private JButton nextButton = new JButton("Следующий");

    private final PianoKeyListener LISTENER = new PianoKeyListener() {
        @Override
        public void onKeyPressed(String key) {
            if (keyToGuess == null)
                return;
            if (key.equals(keyToGuess)) {
                stats.correct();
                messageLabel.setText("Правильно!");
            } else {
                stats.mistake();
                int difference = Key.distance(keyToGuess, key);
                messageLabel.setText("Вы ошиблись на " + Math.abs(difference) + " полутонов");
            }
            statusLabel.setText(stats.toString());
            nextButton.setEnabled(true);
            lastKey = keyToGuess;
            keyToGuess = null;
        }
    };

    PitchExercise(MainFrame frame) {
        super(frame);
        repeatButton.addActionListener(e -> {
            if (keyToGuess != null) piano.play(keyToGuess); else piano.play(lastKey);
        });
        nextButton.addActionListener(e -> next());
        frame.addExercise(this);
    }

    @Override
    public void start() {
        stats = new Stats();
        piano.setShowKeyLabels(false);
        piano.addKeyListener(LISTENER);
        next();
        taskLabel.setText("Задание: определите клавишу");
        statusLabel.setText("");
        messageLabel.setText("");
        frame.addExerciseComponent(repeatButton, true);
        frame.addExerciseComponent(nextButton, true);
    }

    @Override
    public void stop() {
        piano.setShowKeyLabels(true);
        piano.removeKeyListener(LISTENER);
        frame.addExerciseComponent(repeatButton, false);
        frame.addExerciseComponent(nextButton, false);
    }

    @Override
    public void next() {
        nextButton.setEnabled(false);
        keyToGuess = Key.random(piano);
        piano.play(keyToGuess);
        logger.info("next: " + keyToGuess);
    }

    @Override
    public String toString() {
        return Utils.getLocalizedText(L10n.PITCH_EXERCISE);
    }
}
