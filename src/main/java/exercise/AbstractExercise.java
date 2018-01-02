package exercise;

import frame.MainFrame;
import piano.Piano;

import javax.swing.*;

abstract class AbstractExercise {
    JLabel taskLabel;
    JLabel statusLabel;
    Piano piano;
    Stats stats;

    AbstractExercise(MainFrame frame) {
        this.taskLabel = frame.getTaskLabel();
        this.statusLabel = frame.getStatusLabel();
        this.piano = frame.getPiano();

        stats = new Stats();
    }
}
