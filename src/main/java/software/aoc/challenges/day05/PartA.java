package software.aoc.challenges.day05;
import software.aoc.challenges.Solver;

public final class PartA implements Solver {

    @Override
    public long solve(String input) {
        return RangeAnalyzer.empty()
                .loadedFrom(input)
                .countFreshIngredients();
    }
}