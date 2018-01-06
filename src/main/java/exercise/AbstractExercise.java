package exercise;

import frame.MainFrame;
import piano.Piano;
import piano.PianoKeyListener;

import javax.swing.*;

abstract class AbstractExercise {
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

    public boolean isConfigurable() {
        return false;
    }
}
