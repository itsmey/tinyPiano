package exercise;

import common.Key;
import common.L10n;
import common.Utils;
import frame.MainFrame;

import javax.swing.*;
import java.util.logging.Logger;

public class LabelByKeyExercise extends AbstractExercise implements Exercise {
    private static final Logger logger = Logger.getLogger(LabelByKeyExercise.class.getName());
    private String keyToGuess;
    private JButton answerButton = new JButton("Ответить");
    private JTextField answerField = new JTextField(4);

    LabelByKeyExercise(MainFrame frame) {
        super(frame);

        answerButton.addActionListener(e -> {
            if (Key.validate(answerField.getText().toUpperCase())) {
                String answer = Key.normalize(answerField.getText().toUpperCase());
                logger.info("checking answer. normalized answer - " + answer + ", right answer - " + keyToGuess);
                if (answer.equals(Key.normalize(keyToGuess))) {
                    stats.correct();
                    messageLabel.setText("Правильно!");
                } else {
                    stats.mistake();
                    int difference = Key.distance(keyToGuess, answer);
                    messageLabel.setText("Вы ошиблись на " + Math.abs(difference) + " полутонов");
                }
            } else {
                stats.mistake();
                messageLabel.setText("Введенное не является обозначением клавиши!");
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
        answerField.requestFocus();
        logger.info("next: " + keyToGuess);
    }

    @Override
    public String toString() {
        return Utils.getLocalizedText(L10n.LABEL_BY_KEY_EXERCISE);
    }
}
