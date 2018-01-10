package common;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class L10n {
    private static final Logger logger = Logger.getLogger(L10n.class.getName());

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
    public static final String ANSWER_BUTTON = "button.answer";
    public static final String PLAY_BUTTON = "button.play";
    public static final String NEXT_BUTTON = "button.next";
    public static final String KEY_ASSIST_CHECKBOX = "checkbox.key_assist";

    private static final String UNISON = "interval.unison";
    private static final String MINOR_SECOND = "interval.minor_second";
    private static final String MAJOR_SECOND = "interval.major_second";
    private static final String MINOR_THIRD = "interval.minor_third";
    private static final String MAJOR_THIRD = "interval.major_third";
    private static final String FOURTH = "interval.fourth";
    private static final String TRITONE = "interval.tritone";
    private static final String FIFTH = "interval.fifth";
    private static final String MINOR_SIXTH = "interval.minor_sixth";
    private static final String MAJOR_SIXTH = "interval.major_sixth";
    private static final String MINOR_SEVENTH = "interval.minor_seventh";
    private static final String MAJOR_SEVENTH = "interval.major_seventh";
    private static final String OCTAVE = "interval.octave";

    private static final Map<String, String> notes = new HashMap<>();
    public static Map<String, String> NOTES = null;

    static {
        notes.put("C", "note.c");
        notes.put("C#", "note.c_sharp");
        notes.put("D", "note.d");
        notes.put("D#", "note.d_sharp");
        notes.put("Db", "note.d_flat");
        notes.put("E", "note.e");
        notes.put("Eb", "note.e_flat");
        notes.put("F", "note.f");
        notes.put("F#", "note.f_sharp");
        notes.put("G", "note.g");
        notes.put("G#", "note.g_sharp");
        notes.put("Gb", "note.g_flat");
        notes.put("A", "note.a");
        notes.put("A#", "note.a_sharp");
        notes.put("Ab", "note.a_flat");
        notes.put("B", "note.b");
        notes.put("Bb", "note.b_flat");
        NOTES = Collections.unmodifiableMap(notes);
    }

    private static final String[] octaves = {
            "octave.sub_contra",
            "octave.contra",
            "octave.great",
            "octave.small",
            "octave.one_line",
            "octave.two_line",
            "octave.three_line",
            "octave.four_line",
            "octave.five_line"
    };

    public static final List<String> OCTAVES = List.of(octaves);

    private static final String[] intervals = {
            UNISON, MINOR_SECOND, MAJOR_SECOND, MINOR_THIRD, MAJOR_THIRD,
            FOURTH, TRITONE, FIFTH, MINOR_SIXTH, MAJOR_SIXTH,
            MINOR_SEVENTH, MAJOR_SEVENTH, OCTAVE
    };

    public static final List<String> INTERVALS = List.of(intervals);

    public static final String HIGHER = "dynamic.higher";
    public static final String LOWER = "dynamic.lower";
    public static final String CORRECT = "dynamic.correct";
    public static final String YOU_ARE_MISTAKEN = "dynamic.you_are_mistaken";
    public static final String PLAY_NOTE_WHICH = "dynamic.play_note_which";
    public static final String PLAY_NOTE = "dynamic.play_note";
    public static final String ENTER_KEY_SYMBOL = "dynamic.enter_key_symbol";
    public static final String PICK_HIGH = "dynamic.pick_high";
    public static final String IDENTIFY_KEY = "dynamic.identify_key";
    public static final String MISTAKE = "dynamic.mistake";
    public static final String NOT_A_KEY = "dynamic.not_a_key";
    public static final String PLAY = "dynamic.play";

    public static final String STATS = "stats.stats";

    public static String construct(String format, Object... args) {
        return String.format(format, args);
    }

    public static void processContainer(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof Container) {
                processContainer((Container) c);
            }
            if (c instanceof JComponent) {
                processJComponent((JComponent) c);
            }
        }
    }

    private static void processJComponent(JComponent jComponent) {
        String clientProperty = (String)jComponent.getClientProperty(KEY);
        if (clientProperty != null) {
            logger.info(clientProperty);
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
