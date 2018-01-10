package impl.jFugueAudioSource;

import common.Chord;
import org.jfugue.StreamingPlayer;
import piano.AudioSource;

public class JFugueAudioSource implements AudioSource {
    private static StreamingPlayer player = new StreamingPlayer();

    @Override
    public void playKey(String key) {
        player.stream(key);
    }

    @Override
    public void playChord(Chord chord) {
        String inversion = "";
        for (int i = 1; i <= chord.getInversion(); i++) {
            inversion += "^";
        }
        player.stream(chord.getRoot() + chord.getType().getSymbol() + inversion);
    }
}
