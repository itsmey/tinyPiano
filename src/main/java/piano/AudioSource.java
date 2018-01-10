package piano;

import common.Chord;

public interface AudioSource {
    void playKey(String key);
    void playChord(Chord chord);
}
