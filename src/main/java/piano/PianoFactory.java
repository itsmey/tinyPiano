package piano;

import impl.jFugueAudioSource.JFugueAudioSource;
import impl.tinyPiano.TinyPiano;

import java.util.HashMap;
import java.util.Map;

public class PianoFactory {
    private static final int NUMBER_OF_KEYS = 61;
    private static final String FIRST_KEY = "C3";

    public static Piano createPiano() {
        Map<Character, String> binding = new HashMap<>();
        binding.put('1', "C3");
        binding.put('!', "C#3");
        binding.put('2', "D3");
        binding.put('@', "D#3");
        binding.put('3', "E3");
        binding.put('4', "F3");
        binding.put('$', "F#3");
        binding.put('5', "G3");
        binding.put('%', "G#3");
        binding.put('6', "A3");
        binding.put('^', "A#3");
        binding.put('7', "B3");
        
        binding.put('8', "C4");
        binding.put('*', "C#4");
        binding.put('9', "D4");
        binding.put('(', "D#4");
        binding.put('0', "E4");
        binding.put('q', "F4");
        binding.put('Q', "F#4");
        binding.put('w', "G4");
        binding.put('W', "G#4");
        binding.put('e', "A4");
        binding.put('E', "A#4");
        binding.put('r', "B4");
        
        binding.put('t', "C5");
        binding.put('T', "C#5");
        binding.put('y', "D5");
        binding.put('Y', "D#5");
        binding.put('u', "E5");
        binding.put('i', "F5");
        binding.put('I', "F#5");
        binding.put('o', "G5");
        binding.put('O', "G#5");
        binding.put('p', "A5");
        binding.put('P', "A#5");
        binding.put('a', "B5");
        
        binding.put('s', "C6");
        binding.put('S', "C#6");
        binding.put('d', "D6");
        binding.put('D', "D#6");
        binding.put('f', "E6");
        binding.put('g', "F6");
        binding.put('G', "F#6");
        binding.put('h', "G6");
        binding.put('H', "G#6");
        binding.put('j', "A6");
        binding.put('J', "A#6");
        binding.put('k', "B6");
        
        binding.put('l', "C7");
        binding.put('L', "C#7");
        binding.put('z', "D7");
        binding.put('Z', "D#7");
        binding.put('x', "E7");
        binding.put('c', "F7");
        binding.put('C', "F#7");
        binding.put('v', "G7");
        binding.put('V', "G#7");
        binding.put('b', "A7");
        binding.put('B', "A#7");
        binding.put('n', "B7");

        binding.put('m', "C8");
        return new TinyPiano(FIRST_KEY, NUMBER_OF_KEYS, new JFugueAudioSource(), binding);
    }
}
