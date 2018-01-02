package exercise;

import frame.MainFrame;
import impl.tinyPiano.Utils;
import piano.PianoKeyListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PitchExercise extends AbstractExercise implements Exercise {
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
                int difference = Utils.getInterval(keyToGuess, key);
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
        keyToGuess = Utils.getRandomKey(piano);
        piano.play(keyToGuess);
    }

    @Override
    public String toString() {
        return "Определить клавишу по ее звучанию";
    }
}