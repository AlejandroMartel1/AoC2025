package software.aoc.challenges.day02;
import java.util.List;
import java.util.function.LongPredicate;
import java.util.stream.Stream;

public final class GiftShopCatalog {

    private final List<IdRange> ranges;

    private GiftShopCatalog(List<IdRange> ranges) {
        this.ranges = ranges;
    }

    public static GiftShopCatalog empty() {
        return new GiftShopCatalog(List.of());
    }

    public GiftShopCatalog withRangesFrom(String input) {
        return new GiftShopCatalog(
                Stream.of(input.trim().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(IdRange::parse)
                        .toList()
        );
    }

    public long sumOfInvalidIds() {
        return sumMatchingIds(GiftShopCatalog::isInvalidId);
    }

    public long sumOfPatternedIds() {
        return sumMatchingIds(GiftShopCatalog::isPatternedId);
    }

    private long sumMatchingIds(LongPredicate condition) {
        return ranges.stream()
                .flatMapToLong(IdRange::ids)
                .filter(condition)
                .sum();
    }

    private static boolean isInvalidId(long id) {
        String digits = Long.toString(id);
        int n = digits.length();
        return n % 2 == 0 && matchesPatternOfLength(digits, n / 2);
    }

    private static boolean isPatternedId(long id) {
        String digits = Long.toString(id);
        int n = digits.length();
        for (int k = 1; k < n; k++) {
            if (n % k != 0) continue;
            if (matchesPatternOfLength(digits, k)) return true;
        }
        return false;
    }

    private static boolean matchesPatternOfLength(String digits, int patternLength) {
        String pattern = digits.substring(0, patternLength);
        for (int start = patternLength; start < digits.length(); start += patternLength) {
            if (!digits.startsWith(pattern, start)) return false;
        }
        return true;
    }
}
