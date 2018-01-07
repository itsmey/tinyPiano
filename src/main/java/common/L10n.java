package common;

import javax.swing.*;
import java.awt.*;

public class L10n {
    public static final String KEY = "L10n";
    public static final String TITLE = "main.title";
    public static final String OPTIONS = "main.configuration";
    public static final String TRAIN_INTERVALS = "main.train_intervals";

    public static final String KEY_BY_INTERVAL_EXERCISE = "exercise.key_by_interval";
    public static final String KEY_BY_LABEL_EXERCISE = "exercise.key_by_label";
    public static final String LABEL_BY_KEY_EXERCISE = "exercise.label_by_key";
    public static final String PICK_HIGH_EXERCISE = "exercise.pick_high";
    public static final String PITCH_EXERCISE = "exercise.pitch";

    public static final String LOCALE_SELECT = "label.select_locale";
    public static final String TASK_LABEL = "label.task";
    public static final String SELECT_BUTTON = "button.select";
    public static final String STOP_BUTTON = "button.stop";
    public static final String CONFIGURE_BUTTON = "button.configure";

    private static final String UNISON_CHECKBOX = "checkbox.unison";
    private static final String MINOR_SECOND_CHECKBOX = "checkbox.minor_second";
    private static final String MAJOR_SECOND_CHECKBOX = "checkbox.major_second";
    private static final String MINOR_THIRD_CHECKBOX = "checkbox.minor_third";
    private static final String MAJOR_THIRD_CHECKBOX = "checkbox.major_third";
    private static final String FOURTH_CHECKBOX = "checkbox.fourth";
    private static final String TRITONE_CHECKBOX = "checkbox.tritone";
    private static final String FIFTH_CHECKBOX = "checkbox.fifth";
    private static final String MINOR_SIXTH_CHECKBOX = "checkbox.minor_sixth";
    private static final String MAJOR_SIXTH_CHECKBOX = "checkbox.major_sixth";
    private static final String MINOR_SEVENTH_CHECKBOX = "checkbox.minor_seventh";
    private static final String MAJOR_SEVENTH_CHECKBOX = "checkbox.major_seventh";
    private static final String OCTAVE_CHECKBOX = "checkbox.octave";

    public static final String[] INTERVAL_CHECKBOXES = {
            UNISON_CHECKBOX, MINOR_SECOND_CHECKBOX, MAJOR_SECOND_CHECKBOX, MINOR_THIRD_CHECKBOX, MAJOR_THIRD_CHECKBOX,
            FOURTH_CHECKBOX, TRITONE_CHECKBOX, FIFTH_CHECKBOX, MINOR_SIXTH_CHECKBOX, MAJOR_SIXTH_CHECKBOX,
            MINOR_SEVENTH_CHECKBOX, MAJOR_SEVENTH_CHECKBOX, OCTAVE_CHECKBOX
    };

    public static void processContainer(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof Container) {
                processContainer((Container) c);
                if (c instanceof JComponent) {
                    processJComponent((JComponent) c);
                }
            }
        }
    }

    private static void processJComponent(JComponent jComponent) {
        String clientProperty = (String)jComponent.getClientProperty(KEY);
        if (clientProperty != null) {
            String text = Utils.getLocalizedText(clientProperty);
            if (jComponent instanceof JLabel) {
                ((JLabel) jComponent).setText(text);
                return;
            }
            if (jComponent instanceof JButton) {
                ((JButton) jComponent).setText(text);
                return;
            }
            if (jComponent instanceof JCheckBox) {
                ((JCheckBox) jComponent).setText(text);
                return;
            }
        }
    }
}
