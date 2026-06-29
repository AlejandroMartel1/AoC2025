package software.aoc.challenges.day05;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class MergedRanges {

    private final List<FreshRange> ranges;

    private MergedRanges(List<FreshRange> ranges) {
        this.ranges = ranges;
    }

    public static MergedRanges from(List<FreshRange> ranges) {
        return new MergedRanges(mergeOverlapping(sortedByStart(ranges)));
    }

    public long totalSize() {
        return ranges.stream().mapToLong(FreshRange::size).sum();
    }

    private static List<FreshRange> sortedByStart(List<FreshRange> ranges) {
        return ranges.stream()
                .sorted(Comparator.comparingLong(FreshRange::first))
                .toList();
    }

    private static List<FreshRange> mergeOverlapping(List<FreshRange> sorted) {
        if (sorted.isEmpty()) return List.of();
        List<FreshRange> merged = new ArrayList<>();
        FreshRange current = sorted.getFirst();
        for (FreshRange next : sorted.subList(1, sorted.size())) {
            if (current.overlapsOrAdjacentTo(next)) current = current.mergedWith(next);
            else { merged.add(current); current = next; }
        }
        merged.add(current);
        return merged;
    }
}