package exercise;

import frame.MainFrame;
import impl.tinyPiano.Utils;

import javax.swing.*;

public class LabelByKeyExercise extends AbstractExercise implements Exercise {
    private String keyToGuess;
    private MainFrame frame;
    private JButton answerButton = new JButton("Ответить");
    private JTextField answerField = new JTextField(4);

    LabelByKeyExercise(MainFrame frame) {
        super(frame);

        this.frame = frame;

        answerButton.addActionListener(e -> {
            if (answerField.getText().equals(keyToGuess)) {
                stats.correct();
            } else {
                stats.mistake();
            }
            statusLabel.setText(stats.toString());
            next();
        });

        frame.addExercise(this);
    }

    @Override
    public void start() {
        stats = new Stats();
        piano.setShowKeyLabels(false);
        next();
        taskLabel.setText("Задание: напишите обозначение выделенной клавиши");
        statusLabel.setText("");
        frame.addExerciseComponent(answerField, true);
        frame.addExerciseComponent(answerButton, true);
    }

    @Override
    public void stop() {
        piano.setShowKeyLabels(true);
        frame.addExerciseComponent(answerField, false);
        frame.addExerciseComponent(answerButton, false);
    }

    @Override
    public void next() {
        piano.cancelHighlight(keyToGuess);
        keyToGuess = Utils.getRandomKey(piano);
        piano.highlight(keyToGuess);
    }

    @Override
    public String toString() {
        return "Определить обозначение по клавише";
    }
}
