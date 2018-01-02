import exercise.ExerciseFactory;
import frame.MainFrame;
import piano.Piano;
import piano.PianoFactory;

public class Main {
    public static void main(String[] args) {
        Piano piano = PianoFactory.createPiano("C3", 48);
        MainFrame frame = new MainFrame(piano);
        ExerciseFactory.createKeyByLabelExcercise(frame);
        ExerciseFactory.createLabelByKeyExcercise(frame);
        ExerciseFactory.createPitchExcercise(frame);
        frame.setVisible(true);
    }
}