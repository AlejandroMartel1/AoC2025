package software.aoc.challenges.day07;

import java.util.OptionalInt;
import java.util.stream.IntStream;

public record DiagramRow(String text) {

    public static final char SOURCE = 'S';
    public static final char SPLITTER = '^';

    public boolean isSplitterAt(int col) {
        return isInside(col) && text.charAt(col) == SPLITTER;
    }

    public boolean isInside(int col) {
        return col >= 0 && col < text.length();
    }

    public IntStream splitNeighborsOf(int col) {
        return IntStream.of(col - 1, col + 1).filter(this::isInside);
    }

    public OptionalInt sourceColumn() {
        int idx = text.indexOf(SOURCE);
        return idx >= 0 ? OptionalInt.of(idx) : OptionalInt.empty();
    }
}