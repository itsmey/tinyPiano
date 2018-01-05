import exercise.ExerciseFactory;
import frame.MainFrame;
import piano.Piano;
import piano.PianoFactory;

public class Main {
    public static void main(String[] args) {
        Piano piano = PianoFactory.createPiano();
        MainFrame frame = new MainFrame(piano);
        ExerciseFactory.createKeyByLabelExcercise(frame);
        ExerciseFactory.createLabelByKeyExcercise(frame);
        ExerciseFactory.createPitchExcercise(frame);
        ExerciseFactory.createKeyByIntervalExercise(frame);
        ExerciseFactory.createPickHighExercise(frame);
        frame.setVisible(true);
    }
}