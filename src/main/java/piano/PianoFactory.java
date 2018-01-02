package piano;

import impl.jFugueAudioSource.JFugueAudioSource;
import impl.tinyPiano.TinyPiano;

public class PianoFactory {
    private static final int NUMBER_OF_KEYS = 61;
    private static final String FIRST_KEY = "C3";

    public static Piano createPiano() {
        return new TinyPiano(FIRST_KEY, NUMBER_OF_KEYS, new JFugueAudioSource());
    }
}
