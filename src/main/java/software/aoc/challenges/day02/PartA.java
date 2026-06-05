package software.aoc.challenges.day02;
import software.aoc.challenges.Solver;

public final class PartA implements Solver {
    @Override
    public long solve(String input) {
        return GiftShopCatalog.empty()
                .withRangesFrom(input)
                .sumOfInvalidIds();
    }
}
