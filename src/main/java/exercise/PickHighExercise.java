package exercise;

import common.Interval;
import common.L10n;
import common.Utils;
import frame.MainFrame;
import frame.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

public class PickHighExercise extends AbstractExercise implements Exercise {
    private static final Logger logger = Logger.getLogger(PickHighExercise.class.getName());
    private JButton pickAButton = new JButton(" А ");
    private JButton pickBButton = new JButton(" B ");
    private JButton playAButton = new JButton(Utils.getLocalizedText(L10n.PLAY) + " А");
    private JButton playBButton = new JButton(Utils.getLocalizedText(L10n.PLAY) + " В");
    private JButton nextButton = new JButton(Utils.getLocalizedText(L10n.NEXT_BUTTON));

    private String keyA;
    private String keyB;
    private String higherKey;

    private Timer timer;
    
    PickHighExercise(MainFrame frame) {
        super(frame);

        pickAButton.addActionListener(e -> answer(keyA));
        pickBButton.addActionListener(e -> answer(keyB));
        playAButton.addActionListener(e -> piano.play(keyA));
        playBButton.addActionListener(e -> piano.play(keyB));
        nextButton.addActionListener(e -> next());
        
        frame.addExercise(this);
    }

    private void answer(String key) {
        if ((key.equals(higherKey))) {
            correct();
        } else {
            incorrect();
        }
        statusLabel.setText(stats.toString());
        pickAButton.setText(keyA);
        pickBButton.setText(keyB);
        pickAButton.setEnabled(false);
        pickBButton.setEnabled(false);
        playAButton.setText(Utils.getLocalizedText(L10n.PLAY) + " " + keyA);
        playBButton.setText(Utils.getLocalizedText(L10n.PLAY) + " " + keyB);
        pickBButton.setText(keyB);
        nextButton.setEnabled(true);
        nextButton.setText(Utils.getLocalizedText(L10n.NEXT_BUTTON));
        piano.highlight(keyA, "A");
        piano.highlight(keyB, "B");
    }
    
    @Override
    public void start() {
        stats = new Stats();
        piano.setShowKeyLabels(false);
        taskLabel.setText(Utils.getLocalizedText(L10n.PICK_HIGH));
        statusLabel.setText("");
        messageLabel.setText("");
        frame.addExerciseComponent(playAButton, true);
        frame.addExerciseComponent(pickAButton, true);
        frame.addExerciseComponent(pickBButton, true);
        frame.addExerciseComponent(playBButton, true);
        frame.addExerciseComponent(nextButton, true);
        next();
    }

    @Override
    public void stop() {
        piano.setShowKeyLabels(true);
        frame.addExerciseComponent(pickAButton, false);
        frame.addExerciseComponent(pickBButton, false);
        frame.addExerciseComponent(playAButton, false);
        frame.addExerciseComponent(playBButton, false);
        frame.addExerciseComponent(nextButton, false);
    }

    @Override
    public void next() {
        if (keyA != null)
            piano.cancelHighlight(keyA);
        if (keyB != null)
            piano.cancelHighlight(keyB);
        pickAButton.setText(" A ");
        pickBButton.setText(" B ");
        pickAButton.setEnabled(true);
        pickBButton.setEnabled(true);
        playAButton.setText(Utils.getLocalizedText(L10n.PLAY) + " А");
        playBButton.setText(Utils.getLocalizedText(L10n.PLAY) + " В");
        nextButton.setEnabled(false);
        Interval interval = Interval.randomButNotUnison(piano, frame.getIntervalsList());
        if (new Random().nextBoolean()) {
            keyA = interval.getHigherKey();
            keyB = interval.getLowerKey();
            higherKey = keyA;
        } else {
            keyA = interval.getLowerKey();
            keyB = interval.getHigherKey();
            higherKey = keyB;
        }
        piano.play(keyA);
        piano.play(keyB);
    }

    @Override
    public String toString() {
        return Utils.getLocalizedText(L10n.PICK_HIGH_EXERCISE);
    }
}
