package exercise;

import frame.MainFrame;

public class ExerciseFactory {
    public static Exercise createKeyByLabelExcercise(MainFrame frame) {
        return new KeyByLabelExercise(frame);
    }
    public static Exercise createLabelByKeyExcercise(MainFrame frame) {
        return new LabelByKeyExercise(frame);
    }
}
