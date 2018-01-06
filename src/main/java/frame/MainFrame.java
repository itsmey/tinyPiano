package frame;

import common.Interval;
import exercise.Exercise;
import piano.Piano;

import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.awt.*;

public class MainFrame extends JFrame implements Settings {
    private static final int SELECTOR_PANEL_HEIGHT = 80;
    private static final int EXERCISE_PANEL_HEIGHT = 150;

    private Piano piano;
    private Exercise[] exercises;
    private JPanel selectorPanel = new JPanel();
    private JPanel exercisePanel = new JPanel();
    private JPanel exerciseComponentsPanel = new JPanel();
    private JLabel taskLabel = new JLabel("Задание не выбрано", JLabel.CENTER);
    private JLabel statusLabel = new JLabel("", JLabel.CENTER);
    private JLabel messageLabel = new JLabel("", JLabel.CENTER);
    private JComboBox<Exercise> selectorBox;
    private JButton selectButton = new JButton("Выбрать");
    private JButton stopButton = new JButton("Остановить");
    private JButton configureButton = new JButton("Настроить...");
    private ConfigureFrame configureFrame = new ConfigureFrame();

    private Exercise exercise;

    public MainFrame(Piano piano) {
        super("TinyPiano");

        this.piano = piano;

        int pianoWidth = piano.getPanel().getPreferredSize().width;

        setLayout(new BorderLayout(3,3));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        exercisePanel.setLayout(new GridLayout(4,1));
        selectorPanel.setPreferredSize(new Dimension(pianoWidth, SELECTOR_PANEL_HEIGHT));
        exercisePanel.setPreferredSize(new Dimension(pianoWidth, EXERCISE_PANEL_HEIGHT));

        selectorBox = new JComboBox<>();
        selectorBox.addActionListener(e -> {
            if (selectorBox.getSelectedItem() != null)
                configureButton.setEnabled(((Exercise) selectorBox.getSelectedItem()).isConfigurable());
        });
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
            messageLabel.setText("");
            taskLabel.setText("Задание не выбрано");
            piano.setHighlighted(new HashSet<>());
        });
        configureButton.addActionListener(e -> configureFrame.setVisible(true));

        selectorPanel.add(selectorBox);
        selectorPanel.add(selectButton);
        selectorPanel.add(stopButton);
        selectorPanel.add(configureButton);

        exercisePanel.add(taskLabel);
        exercisePanel.add(statusLabel);
        exercisePanel.add(messageLabel);
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

    public JLabel getMessageLabel() {
        return messageLabel;
    }

    public void addExercise(Exercise exercise) {
        selectorBox.addItem(exercise);
    }

    public void addExerciseComponent(JComponent component, boolean add) {
        if (add)
            exerciseComponentsPanel.add(component);
        else
            exerciseComponentsPanel.remove(component);
        repaint();
    }

    @Override
    public List<Interval> getIntervalsList() {
        return configureFrame.getIntervalsList();
    }
}
