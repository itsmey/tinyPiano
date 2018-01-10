package frame;

import common.Chord;
import common.Interval;

import java.util.List;

public interface Settings {
    List<Interval> getIntervalsList();
    List<Chord.ChordType> getChordTypesList();
    boolean areInversionsAllowed();
}
