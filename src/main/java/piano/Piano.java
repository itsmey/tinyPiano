package piano;

import javax.swing.*;
import java.util.Set;

public interface Piano {
    JPanel getPanel();
    void addKeyListener(PianoKeyListener listener);
    void removeKeyListener(PianoKeyListener listener);
    void setShowKeyLabels(boolean show);
    void setShowKeyAssist(boolean show);
    String getFirstKey();
    String getLastKey();
    int getNumberOfKeys();
    void highlight(String key);
    void highlight(String key, String text);
    void cancelHighlight(String key);
    void setHighlighted(Set<String> keys);
    boolean isHighlighted(String Key);
    void play(String key);
    String getActualKey(char ch);
}
