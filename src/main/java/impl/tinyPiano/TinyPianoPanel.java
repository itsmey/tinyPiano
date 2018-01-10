package impl.tinyPiano;

import common.Key;
import common.Utils;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

class TinyPianoPanel extends JPanel {
    private static final Logger logger = Logger.getLogger(TinyPianoPanel.class.getName());

    private static int REPAINT_DELAY = 50;

    private Map<String, Rectangle> blackKeys;
    private Map<String, Rectangle> whiteKeys;
    private boolean canShowLabels = true;
    private boolean canShowKeyAssist = true;
    private Set<String> highlighted = new HashSet<>();
    private Map<String, String> highlightedWithText = new HashMap<>();
    private Map<String, Character> keyAssists;

    TinyPianoPanel(String firstKey, int numberOfKeys, Map<String, Character> keyAssists) {
        super(true);

        this.keyAssists = keyAssists;

        blackKeys = new HashMap<>();
        whiteKeys = new HashMap<>();

        int i = 0;
        int x = 0;
        int y = Dimensions.HEADER;
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
        setPreferredSize(new Dimension(x, Dimensions.HEIGHT_W + Dimensions.HEADER));
        setDoubleBuffered(true);
        Timer t = new Timer(REPAINT_DELAY, e -> repaint());
        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        List<Rectangle> blackHighlighted = new ArrayList<>();

        g.clearRect(0,0,getWidth(),getHeight());

        for (Map.Entry<String, Rectangle> e : blackKeys.entrySet()) {
            Rectangle r = e.getValue();
            String key = e.getKey();
            if (highlighted.contains(key)) {
                blackHighlighted.add(r);
            } else {
                g.fillRect(r.x, r.y, r.width, r.height);
            }
            if (highlightedWithText.containsKey(key)) {
                highlightBlackWithText(g, r, highlightedWithText.get(key));
            }
            if (canShowLabels) {
                drawLabelForBlackKey(key, r.x, r.y, g);
            }
            if (canShowKeyAssist) {
                Color color = g.getColor();
                g.setColor(Dimensions.KEY_ASSISR_COLOR);
                drawLabelForBlackKey(Character.toString(keyAssists.get(key)),
                        r.x, r.y - g.getFontMetrics().getHeight(), g);
                g.setColor(color);
            }
        }

        for (Map.Entry<String, Rectangle> e : whiteKeys.entrySet()) {
            Rectangle r = e.getValue();
            String key = e.getKey();
            g.drawRect(r.x, r.y, r.width, r.height);
            if (highlighted.contains(key)) {
                highlightWhite(g, r);
            }
            if (highlightedWithText.containsKey(key)) {
                highlightWhiteWithText(g, r, highlightedWithText.get(key));
            }
            if (canShowLabels) {
                drawLabelForWhiteKey(key, r.x, r.y, g);
            }
            if (canShowKeyAssist) {
                Color color = g.getColor();
                g.setColor(Dimensions.KEY_ASSISR_COLOR);
                drawLabelForWhiteKey(Character.toString(keyAssists.get(key)),
                        r.x, r.y - g.getFontMetrics().getHeight(), g);
                g.setColor(color);
            }
        }

        for (Rectangle r : blackHighlighted) {
            highlightBlack(g, r);
        }
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

    void showKeyAssist(boolean show) {
        canShowKeyAssist = show;
    }

    private void drawLabelForWhiteKey(String key, int x, int y, Graphics g) {
        char[] chars = key.toCharArray();
        int offset = 0;
        int length = chars.length;
        int width = g.getFontMetrics().charsWidth(chars, offset, length);
        g.drawChars(chars, offset, length, x + (Dimensions.WIDTH_W - width) / 2, y + Dimensions.HEIGHT_W - Dimensions.LABEL_MARGIN);
    }

    private void drawLabelForBlackKey(String key, int x, int y, Graphics g) {
        char[] chars = key.toCharArray();
        int offset = 0;
        int length = chars.length;
        int width = g.getFontMetrics().charsWidth(chars, offset, length);
        g.drawChars(chars, offset, length, x + (Dimensions.WIDTH_B - width) / 2, y - Dimensions.LABEL_MARGIN);
    }

    void highlight(String key) {
        highlighted.add(Key.normalize(key));
    }

    void highlight(String key, String text) {
        highlightedWithText.put(Key.normalize(key), text);
    }

    void cancelHighlight(String key) {
        highlighted.remove(Key.normalize(key));
        highlightedWithText.remove(Key.normalize(key));
    }

    void setHighlighted(Set<String> keys) {
        highlighted = keys;
    }

    boolean isHighlighted(String key) {
        return highlighted.contains(key);
    }

    private void highlightWhite(Graphics g, Rectangle r) {
        Color color = g.getColor();
        g.setColor(Dimensions.HIGHLIGHT_COLOR);
        int m = Dimensions.HIGHLIGHT_OVAL_MARGIN;
        g.fillOval(r.x + m/2, r.y + Dimensions.HEIGHT_W - Dimensions.WIDTH_W + m/2, Dimensions.WIDTH_W - m, Dimensions.WIDTH_W - m);
        g.setColor(color);
    }

    private void highlightWhiteWithText(Graphics g, Rectangle r, String text) {
        Color color = g.getColor();
        g.setColor(Dimensions.HIGHLIGHT_COLOR);
        drawLabelForWhiteKey(text, r.x, r.y, g);
        g.setColor(color);
    }

    private void highlightBlack(Graphics g, Rectangle r) {
        Color color = g.getColor();
        g.setColor(Dimensions.HIGHLIGHT_COLOR);
        g.fillRect(r.x, r.y, r.width, r.height);
        g.setColor(color);
    }

    private void highlightBlackWithText(Graphics g, Rectangle r, String text) {
        Color color = g.getColor();
        g.setColor(Dimensions.HIGHLIGHT_COLOR);
        drawLabelForBlackKey(text, r.x, r.y, g);
        g.setColor(color);
    }
}