package software.aoc.challenges.day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class IngredientDataBase {

    private final List<FreshRange> freshRanges;
    private final long[] availableIds;

    private IngredientDataBase(List<FreshRange> freshRanges, long[] availableIds) {
        this.freshRanges = freshRanges;
        this.availableIds = availableIds;
    }

    public static IngredientDataBase empty() {
        return new IngredientDataBase(List.of(), new long[0]);
    }

    public IngredientDataBase loadedFrom(String input) {
        String[] sections = input.trim().split("\\R\\R");
        List<FreshRange> ranges = sections[0].lines()
                .filter(line -> !line.isBlank())
                .map(FreshRange::parse)
                .toList();
        long[] ids = sections.length > 1
                ? sections[1].lines()
                .filter(line -> !line.isBlank())
                .mapToLong(Long::parseLong)
                .toArray()
                : new long[0];
        return new IngredientDataBase(ranges, ids);
    }

    public long countFreshIngredients() {
        return Arrays.stream(availableIds)
                .filter(this::isFresh)
                .count();
    }

    public long countAllFreshIds() {
        return mergedRanges().stream()
                .mapToLong(range -> range.last() - range.first() + 1)
                .sum();
    }

    private List<FreshRange> mergedRanges() {
        if (freshRanges.isEmpty()) return List.of();

        List<FreshRange> sorted = new ArrayList<>(freshRanges);
        sorted.sort(Comparator.comparingLong(FreshRange::first));

        List<FreshRange> merged = new ArrayList<>();
        FreshRange current = sorted.getFirst();
        for (int i = 1; i < sorted.size(); i++) {
            FreshRange next = sorted.get(i);
            if (next.first() <= current.last() + 1) {
                current = new FreshRange(current.first(), Math.max(current.last(), next.last()));
            } else {
                merged.add(current);
                current = next;
            }
        }
        merged.add(current);
        return merged;
    }

    private boolean isFresh(long id) {
        return freshRanges.stream().anyMatch(range -> range.contains(id));
    }
}