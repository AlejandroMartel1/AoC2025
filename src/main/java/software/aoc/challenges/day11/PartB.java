package software.aoc.challenges.day11;
import software.aoc.challenges.Solver;
import java.util.List;

public final class PartB implements Solver {

    private static final String source = "svr";
    private static final String target = "out";
    private static final List<String>  points = List.of("dac", "fft");

    @Override
    public long solve(String input) {
        return Reactor.empty()
                .withDevicesFrom(input)
                .countPathsThrough(source, target, points);
    }
}