package frame;

import common.Interval;
import common.L10n;
import common.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

class ConfigureFrame extends JFrame {
    private Map<Interval, JCheckBox> intervalBinding = new HashMap<>();
    private JLabel localeLabel = new JLabel(Utils.getLocalizedText(L10n.LOCALE_SELECT) + ": ");
    private JPanel intervalsPanel = new JPanel();

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

        localePanel.setPreferredSize(new Dimension(100, 200));
        intervalsPanel.setPreferredSize(new Dimension(400, 200));
        intervalsPanel.setBorder(BorderFactory.createTitledBorder(Utils.getLocalizedText(L10n.TRAIN_INTERVALS)));

        int i = 0;
        for (Interval interval : Interval.getAll()) {
            String cp = L10n.INTERVAL_CHECKBOXES[i];
            JCheckBox checkBox = new JCheckBox(Utils.getLocalizedText(cp));
            checkBox.putClientProperty(L10n.KEY, cp);
            checkBox.setSelected(true);
            intervalBinding.put(interval, checkBox);
            intervalsPanel.add(checkBox);
            i++;
        }

        add(localePanel, BorderLayout.PAGE_START);
        add(intervalsPanel, BorderLayout.CENTER);

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

    void applyLocale() {
        setTitle(Utils.getLocalizedText(L10n.OPTIONS));
        intervalsPanel.setBorder(BorderFactory.createTitledBorder(Utils.getLocalizedText(L10n.TRAIN_INTERVALS)));
        L10n.processContainer(getContentPane());
    }
}
