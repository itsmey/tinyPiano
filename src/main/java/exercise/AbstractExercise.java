package exercise;

import common.L10n;
import common.Utils;
import frame.MainFrame;
import piano.Piano;
import piano.PianoKeyListener;

import javax.swing.*;
import java.awt.*;

abstract class AbstractExercise {
    private static int DELAY = 1500;

    JLabel taskLabel;
    JLabel statusLabel;
    JLabel messageLabel;
    Piano piano;
    Stats stats;
    MainFrame frame;

    AbstractExercise(MainFrame frame) {
        this.taskLabel = frame.getTaskLabel();
        this.statusLabel = frame.getStatusLabel();
        this.messageLabel = frame.getMessageLabel();
        this.piano = frame.getPiano();
        this.frame = frame;

        stats = new Stats();
    }

    private void answerHelper(String text, Color color) {
        messageLabel.setForeground(color);
        messageLabel.setText(text);
        Timer t = new Timer(DELAY, e -> messageLabel.setText(""));
        t.setRepeats(false);
        t.start();
    }

    void correct(String text) {
        stats.correct();
        answerHelper(text, Color.GREEN);
    }

    void correct() {
        correct(Utils.getLocalizedText(L10n.CORRECT));
    }

    void incorrect(String text) {
        stats.mistake();
        answerHelper(text, Color.RED);
    }

    void incorrect() {
        incorrect(Utils.getLocalizedText(L10n.MISTAKE));
    }
}
