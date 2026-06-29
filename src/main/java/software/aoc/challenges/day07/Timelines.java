package software.aoc.challenges.day07;

import java.util.HashMap;
import java.util.Map;

public record Timelines(Map<Integer, Long> counts) {

    public static Timelines startingAt(int sourceColumn) {
        return new Timelines(Map.of(sourceColumn, 1L));
    }

    public Timelines propagatedThrough(DiagramRow row) {
        Map<Integer, Long> next = new HashMap<>();
        counts.forEach((col, count) -> propagateInto(col, count, row, next));
        return new Timelines(next);
    }

    public long total() {
        return counts.values().stream().mapToLong(Long::longValue).sum();
    }

    private static void propagateInto(int col, long count, DiagramRow row, Map<Integer, Long> next) {
        if (row.isSplitterAt(col)) {
            row.splitNeighborsOf(col).forEach(n -> next.merge(n, count, Long::sum));
        } else if (row.isInside(col)) {
            next.merge(col, count, Long::sum);
        }
    }
}