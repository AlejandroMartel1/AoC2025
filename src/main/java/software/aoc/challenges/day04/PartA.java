package software.aoc.challenges.day04;
import software.aoc.challenges.Solver;

public final class PartA implements Solver {
    @Override
    public long solve(String input) {
        return TrashCompactor.empty()
                .withLayoutFrom(input)
                .countAccessibleRolls();
    }
}
