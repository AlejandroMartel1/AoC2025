package software.aoc.challenges.day02;
import software.aoc.challenges.Solver;

public final class PartB implements Solver {
    @Override
    public long solve(String input) {
        return GiftShopCatalog.empty()
                .withRangesFrom(input)
                .sumOfPatternedIds();
    }
}
