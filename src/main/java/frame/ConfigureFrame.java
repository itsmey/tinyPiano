package frame;

import common.Chord;
import common.Interval;
import common.L10n;
import common.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

class ConfigureFrame extends JFrame {
    private Map<Interval, JCheckBox> intervalBinding = new HashMap<>();
    private Map<Chord.ChordType, JCheckBox> chordTypesBinding = new HashMap<>();
    private JLabel localeLabel = new JLabel(Utils.getLocalizedText(L10n.LOCALE_SELECT) + ": ");
    private JPanel intervalsPanel = new JPanel();
    private JPanel chordTypePanel = new JPanel();
    private JCheckBox areInversionsAllowedCheckBox = new JCheckBox(Utils.getLocalizedText(L10n.INVERSIONS_ALLOWED));

    ConfigureFrame(Map<String, Locale> locales, Runnable applier) {
        super(Utils.getLocalizedText(L10n.OPTIONS));

        setLayout(new BorderLayout(3,3));

        JPanel localePanel = new JPanel();
        localeLabel.putClientProperty(L10n.KEY, L10n.LOCALE_SELECT);
        localePanel.add(localeLabel);
        JComboBox<String> localesBox = new JComboBox<>();
        for(String s : locales.keySet()) {
            localesBox.addItem(s);
        }
        localesBox.addActionListener(e -> {
            Locale.setDefault(locales.get(localesBox.getSelectedItem()));
            applier.run();
        });
        localePanel.add(localesBox);

        localePanel.setPreferredSize(new Dimension(200, 80));
        intervalsPanel.setPreferredSize(new Dimension(300, 200));
        intervalsPanel.setBorder(BorderFactory.createTitledBorder(Utils.getLocalizedText(L10n.TRAIN_INTERVALS)));
        chordTypePanel.setPreferredSize(new Dimension(500, 200));
        chordTypePanel.setBorder(BorderFactory.createTitledBorder(Utils.getLocalizedText(L10n.TRAIN_CHORDS)));

        int i = 0;
        for (Interval interval : Interval.getAll()) {
            String cp = L10n.INTERVALS.get(i);
            JCheckBox checkBox = new JCheckBox(Utils.getLocalizedText(cp) + "(" + i + ")");
            checkBox.putClientProperty(L10n.KEY, cp);
            checkBox.setSelected(true);
            intervalBinding.put(interval, checkBox);
            intervalsPanel.add(checkBox);
            i++;
        }
        for (Chord.ChordType chordType : Chord.ChordType.values()) {
            String cp = L10n.CHORDS.get(chordType);
            JCheckBox checkBox = new JCheckBox(Utils.getLocalizedText(cp) + "(" + chordType.getSymbol() + ")");
            checkBox.putClientProperty(L10n.KEY, cp);
            checkBox.setSelected(true);
            chordTypesBinding.put(chordType, checkBox);
            chordTypePanel.add(checkBox);
        }
        areInversionsAllowedCheckBox.putClientProperty(L10n.KEY, L10n.INVERSIONS_ALLOWED);
        areInversionsAllowedCheckBox.setSelected(true);
        chordTypePanel.add(areInversionsAllowedCheckBox);

        add(localePanel, BorderLayout.PAGE_START);
        add(intervalsPanel, BorderLayout.CENTER);
        add(chordTypePanel, BorderLayout.PAGE_END);

        setResizable(false);

        pack();
    }

    List<Interval> getIntervalsList() {
        List<Interval> list = new ArrayList<>();
        for (Map.Entry<Interval, JCheckBox> e : intervalBinding.entrySet()) {
            if (e.getValue().isSelected())
                list.add(e.getKey());
        }
        return list;
    }

    List<Chord.ChordType> getChordTypesList() {
        List<Chord.ChordType> list = new ArrayList<>();
        for (Map.Entry<Chord.ChordType, JCheckBox> e : chordTypesBinding.entrySet()) {
            if (e.getValue().isSelected())
                list.add(e.getKey());
        }
        return list;
    }

    boolean areInversionsAllowed() {
        return areInversionsAllowedCheckBox.isSelected();
    }

    void applyLocale() {
        setTitle(Utils.getLocalizedText(L10n.OPTIONS));
        intervalsPanel.setBorder(BorderFactory.createTitledBorder(Utils.getLocalizedText(L10n.TRAIN_INTERVALS)));
        chordTypePanel.setBorder(BorderFactory.createTitledBorder(Utils.getLocalizedText(L10n.TRAIN_CHORDS)));
        L10n.processContainer(getContentPane());
    }
}
