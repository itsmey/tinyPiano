package exercise;

import common.Key;
import common.Utils;
import frame.MainFrame;
import piano.PianoKeyListener;

public class KeyByLabelExercise extends AbstractExercise implements Exercise {
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
        taskLabel.setText("Задание: нажмите клавишу " + Key.title(keyToGuess));
    }

    @Override
    public String toString() {
        return "Определить клавишу по обозначению";
    }
}
