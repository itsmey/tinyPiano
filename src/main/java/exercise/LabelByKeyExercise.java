package exercise;

import common.Key;
import frame.MainFrame;

import javax.swing.*;

public class LabelByKeyExercise extends AbstractExercise implements Exercise {
    private String keyToGuess;
    private JButton answerButton = new JButton("Ответить");
    private JTextField answerField = new JTextField(4);

    LabelByKeyExercise(MainFrame frame) {
        super(frame);

        answerButton.addActionListener(e -> {
            String answer = Key.normalize(answerField.getText());
            if (answer.equals(Key.normalize(keyToGuess))) {
                stats.correct();
                messageLabel.setText("Правильно!");
            } else {
                stats.mistake();
                int difference = Key.distance(keyToGuess, answer);
                messageLabel.setText("Вы ошиблись на " + Math.abs(difference) + " полутонов");
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
        messageLabel.setText("");
        answerField.setText("");
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
        answerField.setText("");
        piano.cancelHighlight(keyToGuess);
        keyToGuess = Key.random(piano);
        piano.highlight(keyToGuess);
    }

    @Override
    public String toString() {
        return "Определить обозначение по клавише";
    }
}
