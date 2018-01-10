package frame;

import common.Interval;
import common.L10n;
import common.Utils;
import exercise.Exercise;
import piano.Piano;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.awt.*;
import java.util.List;
import java.util.logging.Logger;

public class MainFrame extends JFrame implements Settings {
    private static final Logger logger = Logger.getLogger(MainFrame.class.getName());

    private static final int SELECTOR_PANEL_HEIGHT = 80;
    private static final int EXERCISE_PANEL_HEIGHT = 150;

    private Piano piano;
    private JPanel selectorPanel = new JPanel();
    private JPanel exercisePanel = new JPanel();
    private JPanel exerciseComponentsPanel = new JPanel();
    private JLabel taskLabel = new JLabel(Utils.getLocalizedText(L10n.TASK_LABEL), JLabel.CENTER);
    private JLabel statusLabel = new JLabel("", JLabel.CENTER);
    private JLabel messageLabel = new JLabel("", JLabel.CENTER);
    private JComboBox<Exercise> selectorBox;
    private JButton selectButton = new JButton(Utils.getLocalizedText(L10n.SELECT_BUTTON));
    private JButton stopButton = new JButton(Utils.getLocalizedText(L10n.STOP_BUTTON));
    private JButton configureButton = new JButton(Utils.getLocalizedText(L10n.CONFIGURE_BUTTON));
    private ConfigureFrame configureFrame;

    private Exercise exercise;

    private static final Map<String, Locale> locales = new HashMap<>();

    static {
        Locale.setDefault(new Locale("en", "US"));
        locales.put("English", new Locale("en", "US"));
        locales.put("Русский", new Locale("ru", "RU"));
    }

    public MainFrame(Piano piano) {
        super(Utils.getLocalizedText(L10n.TITLE));

        this.piano = piano;

        taskLabel.putClientProperty(L10n.KEY, L10n.TASK_LABEL);
        selectButton.putClientProperty(L10n.KEY, L10n.SELECT_BUTTON);
        stopButton.putClientProperty(L10n.KEY, L10n.STOP_BUTTON);
        configureButton.putClientProperty(L10n.KEY, L10n.CONFIGURE_BUTTON);

        configureFrame = new ConfigureFrame(locales, this::applyLocale);

        int pianoWidth = piano.getPanel().getPreferredSize().width;

        setLayout(new BorderLayout(3,3));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        exercisePanel.setLayout(new GridLayout(4,1));
        selectorPanel.setPreferredSize(new Dimension(pianoWidth, SELECTOR_PANEL_HEIGHT));
        exercisePanel.setPreferredSize(new Dimension(pianoWidth, EXERCISE_PANEL_HEIGHT));

        selectorBox = new JComboBox<>();
        selectorBox.setPreferredSize(new Dimension(300, 25));

        selectButton.addActionListener(e -> {
            stopButton.doClick();
            if (selectorBox.getSelectedItem() != null) {
                exercise = (Exercise)selectorBox.getSelectedItem();
                exercise.start();
            }
            configureButton.setEnabled(false);
            stopButton.setEnabled(true);
        });
        stopButton.setEnabled(false);
        stopButton.addActionListener(e -> {
            if (exercise != null) {
                exercise.stop();
                exercise = null;
            }
            statusLabel.setText("");
            messageLabel.setText("");
            taskLabel.setText(Utils.getLocalizedText(L10n.TASK_LABEL));
            piano.setHighlighted(new HashSet<>());
            configureButton.setEnabled(true);
            stopButton.setEnabled(false);
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

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new CustomKeyEventDispatcher());
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

    private void applyLocale() {
        setTitle(Utils.getLocalizedText(L10n.TITLE));
        configureFrame.applyLocale();
        L10n.processContainer(getContentPane());
        selectorBox.repaint();
    }

    @Override
    public List<Interval> getIntervalsList() {
        return configureFrame.getIntervalsList();
    }

    public void keyPressed(KeyEvent e) {
        logger.info("keyPressed " + e.getKeyChar() + " " + e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        logger.info("keyReleased " + e.getKeyChar() + " " + e.getKeyCode());
    }

    private class CustomKeyEventDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                keyPressed(e);
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                keyReleased(e);
            }
            return false;
        }
    }
}
