package software.aoc.challenges.day07;
import software.aoc.challenges.Solver;

public final class PartB implements Solver {

    @Override
    public long solve(String input) {
        return TachyonManifold.empty()
                .withDiagramFrom(input)
                .countTimelines();
    }
}