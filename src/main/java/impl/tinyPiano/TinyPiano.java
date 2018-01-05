package impl.tinyPiano;

import common.Key;
import common.Utils;
import piano.AudioSource;
import piano.Piano;
import piano.PianoKeyListener;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TinyPiano implements Piano {
    private TinyPianoPanel panel;
    private List<PianoKeyListener> listeners;
    private String firstKey;
    private int numberOfKeys;
    private AudioSource audioSource;

    public TinyPiano(String firstKey, int numberOfKeys, AudioSource audioSource) {
        listeners = new ArrayList<>();
        this.firstKey = firstKey;
        this.numberOfKeys = numberOfKeys;
        this.audioSource = audioSource;

        panel = new TinyPianoPanel(firstKey, numberOfKeys);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                keyPressed(panel.determineKeyPressed(e.getX(), e.getY()));
            }
        });

        addKeyListener(audioSource::playKey);
    }

    @Override
    public void setHighlighted(Set<String> keys) {
        panel.setHighlighted(keys);
    }

    @Override
    public void play(String key) {
        audioSource.playKey(key);
    }

    @Override
    public void highlight(String key) {
        if (key == null)
            return;
        panel.highlight(key);
    }

    @Override
    public void highlight(String key, String text) {
        if (key == null || text == null)
            return;
        panel.highlight(key, text);
    }

    @Override
    public void cancelHighlight(String key) {
        if (key == null)
            return;
        panel.cancelHighlight(key);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void addKeyListener(PianoKeyListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeKeyListener(PianoKeyListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void setShowKeyLabels(boolean show) {
        panel.showLabels(show);
        panel.paintComponent(panel.getGraphics());
    }

    @Override
    public String getFirstKey() {
        return firstKey;
    }

    @Override
    public String getLastKey() {
        return Key.transpose(firstKey, numberOfKeys - 1);
    }

    @Override
    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    private void keyPressed(String key) {
        if (key == null) {
            return;
        }
        for (PianoKeyListener listener: listeners) {
            listener.onKeyPressed(key);
        }
    }
}
