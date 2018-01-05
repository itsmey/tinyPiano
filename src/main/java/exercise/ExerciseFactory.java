package exercise;

import frame.MainFrame;

public class ExerciseFactory {
    public static Exercise createKeyByLabelExcercise(MainFrame frame) {
        return new KeyByLabelExercise(frame);
    }
    public static Exercise createLabelByKeyExcercise(MainFrame frame) {
        return new LabelByKeyExercise(frame);
    }
    public static Exercise createPitchExcercise(MainFrame frame) {
        return new PitchExercise(frame);
    }
    public static Exercise createKeyByIntervalExercise(MainFrame frame) {
        return new KeyByIntervalExercise(frame);
    }
    public static Exercise createPickHighExercise(MainFrame frame) {
        return new PickHighExercise(frame);
    }
}
