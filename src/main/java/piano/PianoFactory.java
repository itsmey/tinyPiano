package piano;

import impl.jFugueAudioSource.JFugueAudioSource;
import impl.tinyPiano.TinyPiano;

public class PianoFactory {
    public static Piano createPiano(String firstKey, int numberOfKeys) {
        return new TinyPiano(firstKey, numberOfKeys, new JFugueAudioSource());
    }
}
