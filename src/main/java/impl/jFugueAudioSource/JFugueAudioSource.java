package impl.jFugueAudioSource;

import org.jfugue.StreamingPlayer;
import piano.AudioSource;

public class JFugueAudioSource implements AudioSource {
    @Override
    public void playKey(String key) {
        new StreamingPlayer().stream(key);
    }
}
