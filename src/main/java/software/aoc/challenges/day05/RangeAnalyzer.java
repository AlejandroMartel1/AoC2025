package software.aoc.challenges.day05;
import java.util.Arrays;
import java.util.List;

public final class RangeAnalyzer {

    private final List<FreshRange> freshRanges;
    private final long[] availableIds;

    private RangeAnalyzer(List<FreshRange> freshRanges, long[] availableIds) {
        this.freshRanges = freshRanges;
        this.availableIds = availableIds;
    }

    public static RangeAnalyzer empty() {
        return new RangeAnalyzer(List.of(), new long[0]);
    }

    public RangeAnalyzer loadedFrom(String input) {
        String[] sections = input.trim().split("\\R\\R");
        return new RangeAnalyzer(parseRanges(sections[0]), parseIds(idsSectionIn(sections)));
    }

    public long countFreshIngredients() {
        return Arrays.stream(availableIds).filter(this::isFresh).count();
    }

    public long countAllFreshIds() {
        return MergedRanges.from(freshRanges).totalSize();
    }

    private boolean isFresh(long id) {
        return freshRanges.stream().anyMatch(range -> range.contains(id));
    }

    private static List<FreshRange> parseRanges(String section) {
        return section.lines().filter(line -> !line.isBlank()).map(FreshRange::parse).toList();
    }

    private static long[] parseIds(String section) {
        return section.lines().filter(line -> !line.isBlank()).mapToLong(Long::parseLong).toArray();
    }

    private static String idsSectionIn(String[] sections) {
        return sections.length > 1 ? sections[1] : "";
    }
}