package frame;

import common.Interval;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

class ConfigureFrame extends JFrame {
    private Map<Interval, JCheckBox> intervalBinding = new HashMap<>();

    ConfigureFrame() {
        super("Настройки");
        setLayout(new BorderLayout(3,3));
        JPanel intervalsPanel = new JPanel();
        intervalsPanel.setPreferredSize(new Dimension(400, 200));
        intervalsPanel.setBorder(BorderFactory.createTitledBorder("Тренировать интервалы"));
        for (Interval interval : Interval.getAll()) {
            JCheckBox checkBox = new JCheckBox(interval.title());
            checkBox.setSelected(true);
            intervalBinding.put(interval, checkBox);
            intervalsPanel.add(checkBox);
        }
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
}
