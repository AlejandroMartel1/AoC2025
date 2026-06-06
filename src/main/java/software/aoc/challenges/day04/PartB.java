package software.aoc.challenges.day04;
import software.aoc.challenges.Solver;

public class PartB implements Solver {
    @Override
    public long solve(String input) {
        return PaperGrid.empty()
                .withLayoutFrom(input)
                .countTotalRemovableRolls();
    }
}
