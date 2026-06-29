package software.aoc.challenges.day10;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.stream.IntStream;

public final class JoltageSystem {

    private final List<Integer> wirings;
    private final List<Integer> joltage;
    private final Map<List<Integer>, OptionalLong> memo = new HashMap<>();

    public JoltageSystem(List<Integer> wirings, List<Integer> joltage) {
        this.wirings = wirings;
        this.joltage = joltage;
    }

    public long minimumPresses() {
        return solve(joltage).orElse(0L);
    }

    private OptionalLong solve(List<Integer> remaining) {
        if (allZero(remaining)) return OptionalLong.of(0L);
        if (memo.containsKey(remaining)) return memo.get(remaining);
        OptionalLong best = bestOverAllMasks(remaining);
        memo.put(remaining, best);
        return best;
    }

    private OptionalLong bestOverAllMasks(List<Integer> remaining) {
        return IntStream.range(0, 1 << wirings.size())
                .mapToObj(mask -> costUsing(mask, remaining))
                .flatMapToLong(OptionalLong::stream)
                .min();
    }

    private OptionalLong costUsing(int mask, List<Integer> remaining) {
        int[] effect = effectOf(mask);
        if (!canApply(effect, remaining)) return OptionalLong.empty();
        return solve(reducedFrom(effect, remaining))
                .stream().map(sub -> Integer.bitCount(mask) + 2L * sub).findFirst();
    }

    private int[] effectOf(int mask) {
        int[] sum = new int[joltage.size()];
        for (int i = 0; i < wirings.size(); i++) {
            if ((mask & (1 << i)) != 0) addWiringTo(sum, wirings.get(i));
        }
        return sum;
    }

    private void addWiringTo(int[] sum, int wiring) {
        for (int c = 0; c < sum.length; c++) {
            if (((wiring >> c) & 1) != 0) sum[c]++;
        }
    }

    private boolean canApply(int[] effect, List<Integer> remaining) {
        return IntStream.range(0, remaining.size())
                .allMatch(i -> effect[i] <= remaining.get(i) && parityMatches(effect[i], remaining.get(i)));
    }

    private List<Integer> reducedFrom(int[] effect, List<Integer> remaining) {
        return IntStream.range(0, remaining.size())
                .map(i -> (remaining.get(i) - effect[i]) / 2)
                .boxed().toList();
    }

    private static boolean parityMatches(int a, int b) {
        return (a & 1) == (b & 1);
    }

    private static boolean allZero(List<Integer> values) {
        return values.stream().allMatch(v -> v == 0);
    }
}