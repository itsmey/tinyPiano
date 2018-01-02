package impl.tinyPiano;

import common.Key;
import common.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

class TinyPianoPanel extends JPanel {
    private Map<String, Rectangle> blackKeys;
    private Map<String, Rectangle> whiteKeys;
    private boolean canShowLabels = true;
    private Set<String> highlighted = new HashSet<>();

    TinyPianoPanel(String firstKey, int numberOfKeys) {
        super(true);

        blackKeys = new HashMap<>();
        whiteKeys = new HashMap<>();

        int i = 0;
        int x = 0;
        int y = 0;
        String key = firstKey;
        while (i < numberOfKeys) {
            if (Key.isBlack(key)) {
                blackKeys.put(key, new Rectangle(x - Dimensions.WIDTH_B / 2, y, Dimensions.WIDTH_B, Dimensions.HEIGHT_B));
            } else {
                whiteKeys.put(key, new Rectangle(x, y, Dimensions.WIDTH_W, Dimensions.HEIGHT_W));
                x += Dimensions.WIDTH_W;
            }
            key = Key.next(key);
            i++;
        }
        setPreferredSize(new Dimension(x, Dimensions.HEIGHT_W));
    }

    @Override
    protected void paintComponent(Graphics g) {
        List<Rectangle> blackHighlighted = new ArrayList<>();
        Color color = g.getColor();
        g.clearRect(0,0,getWidth(),getHeight());

        for (Map.Entry<String, Rectangle> e : blackKeys.entrySet()) {
            Rectangle r = e.getValue();
            if (highlighted.contains(e.getKey())) {
                blackHighlighted.add(r);
            } else {
                g.fillRect(r.x, r.y, r.width, r.height);
            }
        }

        for (Map.Entry<String, Rectangle> e : whiteKeys.entrySet()) {
            Rectangle r = e.getValue();
            g.drawRect(r.x, r.y, r.width, r.height);
            if (highlighted.contains(e.getKey())) {
                g.setColor(Dimensions.HIGHLIGHT_COLOR);
                int m = Dimensions.HIGHLIGHT_OVAL_MARGIN;
                g.fillOval(r.x + m/2, r.y + Dimensions.HEIGHT_W - Dimensions.WIDTH_W + m/2, Dimensions.WIDTH_W - m, Dimensions.WIDTH_W - m);
                g.setColor(color);
            }
            if (canShowLabels) {
                drawLabelForWhiteKey(e.getKey(), r.x, r.y, g);
            }
        }

        g.setColor(Dimensions.HIGHLIGHT_COLOR);
        for (Rectangle r : blackHighlighted) {
            g.fillRect(r.x, r.y, r.width, r.height);
        }
        g.setColor(color);
    }

    String determineKeyPressed(int x, int y) {
        for (Map.Entry<String, Rectangle> e : blackKeys.entrySet()) {
            if (Utils.isWithinRectangle(x, y, e.getValue()))
                return e.getKey();
        }

        for (Map.Entry<String, Rectangle> e : whiteKeys.entrySet()) {
            if (Utils.isWithinRectangle(x, y, e.getValue()))
                return e.getKey();
        }

        return null;
    }

    void showLabels(boolean show) {
        canShowLabels = show;
    }

    private void drawLabelForWhiteKey(String key, int x, int y, Graphics g) {
        char[] chars = key.toCharArray();
        int offset = 0;
        int length = chars.length;
        int width = g.getFontMetrics().charsWidth(chars, offset, length);
        g.drawChars(chars, offset, length, x + (Dimensions.WIDTH_W - width) / 2, y + Dimensions.HEIGHT_W - 3);
    }

    void highlight(String key) {
        highlighted.add(key);
        paintComponent(getGraphics());
    }

    void cancelHighlight(String key) {
        highlighted.remove(key);
        paintComponent(getGraphics());
    }

    void setHighlighted(Set<String> keys) {
        highlighted = keys;
        paintComponent(getGraphics());
    }
}