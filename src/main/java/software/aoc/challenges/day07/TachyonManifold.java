package software.aoc.challenges.day07;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TachyonManifold {

    private static final char source = 'S';
    private static final char splitter = '^';

    private final List<String> rows;

    private TachyonManifold(List<String> rows) {
        this.rows = rows;
    }

    public static TachyonManifold empty() {
        return new TachyonManifold(List.of());
    }

    public TachyonManifold withDiagramFrom(String input) {
        return new TachyonManifold(
                input.lines()
                        .filter(line -> !line.isBlank())
                        .toList()
        );
    }

    public long countSplits() {
        if (rows.isEmpty()) return 0;

        Set<Integer> beams = new HashSet<>();
        beams.add(findSourceColumn());

        long totalSplits = 0;
        for (String currentRow : rows) {
            Set<Integer> nextBeams = new HashSet<>();
            for (int col : beams) {
                if (isSplitterAt(currentRow, col)) {
                    totalSplits++;
                    addIfInside(nextBeams, col - 1, currentRow.length());
                    addIfInside(nextBeams, col + 1, currentRow.length());
                } else if (isInside(col, currentRow.length())) {
                    nextBeams.add(col);
                }
            }
            beams = nextBeams;
        }
        return totalSplits;
    }

    public long countTimelines() {
        if (rows.isEmpty()) return 0;

        Map<Integer, Long> timelines = new HashMap<>();
        timelines.put(findSourceColumn(), 1L);

        for (String currentRow : rows) {
            Map<Integer, Long> nextTimelines = new HashMap<>();
            for (Map.Entry<Integer, Long> entry : timelines.entrySet()) {
                int col = entry.getKey();
                long count = entry.getValue();
                if (isSplitterAt(currentRow, col)) {
                    accumulateIfInside(nextTimelines, col - 1, count, currentRow.length());
                    accumulateIfInside(nextTimelines, col + 1, count, currentRow.length());
                } else if (isInside(col, currentRow.length())) {
                    nextTimelines.merge(col, count, Long::sum);
                }
            }
            timelines = nextTimelines;
        }

        return timelines.values().stream().mapToLong(Long::longValue).sum();
    }

    private int findSourceColumn() {
        for (String row : rows) {
            int idx = row.indexOf(source);
            if (idx >= 0) return idx;
        }
        throw new IllegalStateException("Fuente 'S' no encontrada en el manifold");
    }

    private static boolean isSplitterAt(String row, int col) {
        return isInside(col, row.length()) && row.charAt(col) == splitter;
    }

    private static boolean isInside(int col, int width) {
        return col >= 0 && col < width;
    }

    private static void addIfInside(Set<Integer> set, int col, int width) {
        if (isInside(col, width)) set.add(col);
    }

    private static void accumulateIfInside(Map<Integer, Long> map, int col, long count, int width) {
        if (isInside(col, width)) {
            map.merge(col, count, Long::sum);
        }
    }
}