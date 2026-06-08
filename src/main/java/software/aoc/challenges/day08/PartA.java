package software.aoc.challenges.day08;
import software.aoc.challenges.Solver;

public final class PartA implements Solver {

    private static final int shortest_possible_connection = 1000;
    private static final int circuits_to_multiply = 3;

    @Override
    public long solve(String input) {
        return Playground.empty()
                .withNodesFrom(input)
                .productOfTopCircuitSizes(
                        shortest_possible_connection,
                        circuits_to_multiply
                );
    }
}