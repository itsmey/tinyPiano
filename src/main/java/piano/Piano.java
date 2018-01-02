package piano;

import javax.swing.*;
import java.util.Set;

public interface Piano {
    JPanel getPanel();
    void addKeyListener(PianoKeyListener listener);
    void removeKeyListener(PianoKeyListener listener);
    void setShowKeyLabels(boolean show);
    String getFirstKey();
    int getNumberOfKeys();
    void highlight(String key);
    void cancelHighlight(String key);
    void setHighlighted(Set<String> keys);
    void play(String key);
}
