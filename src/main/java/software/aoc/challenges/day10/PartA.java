package software.aoc.challenges.day10;
import software.aoc.challenges.Solver;

public final class PartA implements Solver {

    @Override
    public long solve(String input) {
        return Factory.empty()
                .withMachinesFrom(input)
                .fewestTotalButtonPresses();
    }
}