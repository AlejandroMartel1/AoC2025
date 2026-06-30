package software.aoc.challenges.day07;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record BeamState(Set<Integer> beams, long splits) {

    public static BeamState startingAt(int sourceColumn) {
        return new BeamState(Set.of(sourceColumn), 0L);
    }

    public BeamState propagatedThrough(DiagramRow row) {
        long extraSplits = beams.stream().filter(row::isSplitterAt).count();
        Set<Integer> next = beams.stream()
                .flatMap(col -> nextBeamsFrom(col, row))
                .collect(Collectors.toSet());
        return new BeamState(next, splits + extraSplits);
    }

    private static Stream<Integer> nextBeamsFrom(int col, DiagramRow row) {
        if (row.isSplitterAt(col)) return row.splitNeighborsOf(col).boxed();
        if (row.isInside(col)) return Stream.of(col);
        return Stream.empty();
    }
}