package software.aoc.challenges.day03;
import software.aoc.challenges.Solver;

public final class PartA implements Solver {

    @Override
    public long solve(String input) {
        return PowerSupply.empty()
                .withBanksFrom(input)
                .totalJoltageWith(2);
    }
}