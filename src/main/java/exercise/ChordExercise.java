package exercise;

import common.Chord;
import common.L10n;
import common.Utils;
import frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.CheckedOutputStream;

public class ChordExercise extends AbstractExercise implements Exercise {
    private static final Logger logger = Logger.getLogger(ChordExercise.class.getName());

    private final static int N_VARIANTS = 4;
    private final String KEY = "Chord";

    private Chord chordToGuess;
    private Chord lastChord;
    private JButton repeatButton = new JButton(Utils.getLocalizedText(L10n.PLAY_BUTTON));
    private JButton nextButton = new JButton(Utils.getLocalizedText(L10n.NEXT_BUTTON));
    private JButton variantButtons[] = new JButton[N_VARIANTS];
    private JPanel splitter = new JPanel();

    ChordExercise(MainFrame frame) {
        super(frame);
        splitter.setPreferredSize(new Dimension(60, 10));
        splitter.setBackground(Color.WHITE);
        repeatButton.addActionListener(e -> {
            if (chordToGuess != null) piano.playChord(chordToGuess); else piano.playChord(lastChord);
        });
        nextButton.addActionListener(e -> next());
        for(int i = 0; i < N_VARIANTS; i++) {
            variantButtons[i] = new JButton();
            variantButtons[i].addActionListener(e -> {
                JButton button = ((JButton) e.getSource());
                Chord variant = (Chord) button.getClientProperty(KEY);
                if (chordToGuess != null) {
                    if (chordToGuess.equals(variant)) {
                        correct();
                    } else {
                        incorrect();
                        button.setForeground(Color.RED);
                    }
                    highlightCorrectAnswerButton();
                    statusLabel.setText(stats.toString());
                    nextButton.setEnabled(true);
                    lastChord = chordToGuess;
                    chordToGuess = null;
                } else {
                    piano.playChord(variant);
                    variant.highlight(piano);
                }
            });
        }
        frame.addExercise(this);
    }

    @Override
    public void start() {
        stats = new Stats();
        piano.setShowKeyLabels(false);
        next();
        taskLabel.setText(Utils.getLocalizedText(L10n.IDENTIFY_CHORD));
        statusLabel.setText("");
        messageLabel.setText("");
        nextButton.setText(Utils.getLocalizedText(L10n.NEXT_BUTTON));
        repeatButton.setText(Utils.getLocalizedText(L10n.PLAY_BUTTON));
        frame.addExerciseComponent(repeatButton, true);
        frame.addExerciseComponent(nextButton, true);
        frame.addExerciseComponent(splitter, true);
        for(int i = 0; i < N_VARIANTS; i++) {
            frame.addExerciseComponent(variantButtons[i], true);
        }
    }

    @Override
    public void stop() {
        piano.setShowKeyLabels(true);
        frame.addExerciseComponent(repeatButton, false);
        frame.addExerciseComponent(nextButton, false);
        frame.addExerciseComponent(splitter, false);
        for(int i = 0; i < N_VARIANTS; i++) {
            frame.addExerciseComponent(variantButtons[i], false);
        }
    }

    @Override
    public void next() {
        nextButton.setEnabled(false);
        chordToGuess = Chord.random(piano, frame.getChordTypesList(), frame.areInversionsAllowed());
        piano.playChord(chordToGuess);
        logger.info("next: " + chordToGuess);
        List<Chord> incorrectVariants = createIncorrectVariants(chordToGuess);
        for(int i = 0; i < N_VARIANTS - 1; i++) {
            variantButtons[i].putClientProperty(KEY, incorrectVariants.get(i));
            variantButtons[i].setText(incorrectVariants.get(i).toString());
        }
        int r = new Random().nextInt(N_VARIANTS);
        if (r != N_VARIANTS - 1) {
            variantButtons[N_VARIANTS - 1].putClientProperty(KEY, variantButtons[r].getClientProperty(KEY));
            variantButtons[N_VARIANTS - 1].setText(variantButtons[r].getText());
        }
        variantButtons[r].putClientProperty(KEY, chordToGuess);
        variantButtons[r].setText(chordToGuess.toString());
        cancelButtonHighlighting();
        piano.setHighlighted(new HashSet<>());
    }

    private List<Chord> createIncorrectVariants(Chord correctVariant) {
        List<Chord.ChordType> types = new LinkedList<>(frame.getChordTypesList());
        List<Chord> result = new ArrayList<>();
        types.remove(correctVariant.getType());
        for (int i = 0; i < N_VARIANTS - 1; i++) {
            Chord.ChordType type = types.get(new Random().nextInt(types.size()));
            types.remove(type);
            result.add(new Chord(correctVariant.getRoot(), type, 0));
        }

        return result;
    }

    private void highlightCorrectAnswerButton() {
        if (chordToGuess != null) {
            for (int i = 0; i < N_VARIANTS; i++) {
                if (variantButtons[i].getClientProperty(KEY).equals(chordToGuess)) {
                    variantButtons[i].setForeground(Color.GREEN);
                }
            }
        }
    }

    private void cancelButtonHighlighting() {
        for (int i = 0; i < N_VARIANTS; i++) {
            variantButtons[i].setForeground(Color.BLACK);
        }
    }

    @Override
    public String toString() {
        return Utils.getLocalizedText(L10n.CHORD_EXERCISE);
    }
}
