package software.aoc.challenges.day06;
import software.aoc.challenges.Solver;

public final class PartB implements Solver {

    @Override
    public long solve(String input) {
        return MathWorksheet.empty()
                .loadedColumnarFrom(input)
                .grandTotal();
    }
}

