package software.aoc.challenges.day11;
import software.aoc.challenges.Solver;

public final class PartA implements Solver {

    private static final String source = "you";
    private static final String target = "out";

    @Override
    public long solve(String input) {
        return Reactor.empty()
                .withDevicesFrom(input)
                .countPaths(source, target);
    }
}