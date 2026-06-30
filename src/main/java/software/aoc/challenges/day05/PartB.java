package software.aoc.challenges.day05;
import software.aoc.challenges.Solver;

public class PartB implements Solver {
    @Override
    public long solve(String input) {
        return RangeAnalyzer.empty()
                .loadedFrom(input)
                .countAllFreshIds();
    }
}
