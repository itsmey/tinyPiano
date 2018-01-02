package frame;

import exercise.Exercise;
import piano.Piano;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class MainFrame extends JFrame {
    private static final int SELECTOR_PANEL_HEIGHT = 100;
    private static final int EXERCISE_PANEL_HEIGHT = 200;

    private Piano piano;
    private Exercise[] exercises;
    private JPanel selectorPanel = new JPanel();
    private JPanel exercisePanel = new JPanel();
    private JPanel exerciseComponentsPanel = new JPanel();
    private JLabel taskLabel = new JLabel("Задание не выбрано", JLabel.CENTER);
    private JLabel statusLabel = new JLabel("", JLabel.CENTER);
    private JComboBox<Exercise> selectorBox;
    private JButton selectButton = new JButton("Выбрать");
    private JButton stopButton = new JButton("Остановить");

    private Exercise exercise;

    public MainFrame(Piano piano) {
        super("TinyPiano");

        this.piano = piano;

        int pianoWidth = piano.getPanel().getPreferredSize().width;

        setLayout(new BorderLayout(3,3));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        exercisePanel.setLayout(new GridLayout(3,1));
        selectorPanel.setPreferredSize(new Dimension(pianoWidth, SELECTOR_PANEL_HEIGHT));
        exercisePanel.setPreferredSize(new Dimension(pianoWidth, EXERCISE_PANEL_HEIGHT));

        selectorBox = new JComboBox<>();
        selectButton.addActionListener(e -> {
            stopButton.doClick();
            if (selectorBox.getSelectedItem() != null) {
                exercise = (Exercise)selectorBox.getSelectedItem();
                exercise.start();
            }
        });
        stopButton.addActionListener(e -> {
            if (exercise != null) {
                exercise.stop();
                exercise = null;
            }
            statusLabel.setText("");
            taskLabel.setText("Задание не выбрано");
            piano.setHighlighted(new HashSet<>());
        });

        selectorPanel.add(selectorBox);
        selectorPanel.add(selectButton);
        selectorPanel.add(stopButton);

        exercisePanel.add(taskLabel);
        exercisePanel.add(statusLabel);
        exercisePanel.add(exerciseComponentsPanel);

        add(selectorPanel, BorderLayout.PAGE_START);
        add(exercisePanel, BorderLayout.CENTER);
        add(piano.getPanel(), BorderLayout.PAGE_END);

        getContentPane().setPreferredSize(new Dimension(pianoWidth,
                piano.getPanel().getPreferredSize().height + SELECTOR_PANEL_HEIGHT + EXERCISE_PANEL_HEIGHT));
        pack();

        setResizable(false);
    }

    public Piano getPiano() {
        return piano;
    }

    public JLabel getTaskLabel() {
        return taskLabel;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void addExercise(Exercise exercise) {
        selectorBox.addItem(exercise);
    }

    public void addExerciseComponent(JComponent component, boolean add) {
        if (add)
            exerciseComponentsPanel.add(component);
        else
            exerciseComponentsPanel.remove(component);
    }
}
