package software.aoc.challenges.day08;
import software.aoc.challenges.Solver;

public final class PartB implements Solver {

    @Override
    public long solve(String input) {
        return Playground.empty()
                .withNodesFrom(input)
                .productOfXOfFinalUnifyingConnection();
    }
}