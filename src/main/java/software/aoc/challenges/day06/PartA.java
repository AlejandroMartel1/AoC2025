package software.aoc.challenges.day06;
import software.aoc.challenges.Solver;

public class PartA implements Solver {

    @Override
    public long solve(String input) {
        return MathWorksheet.empty()
                .loadedFrom(input)
                .grandTotal();
    }
}
