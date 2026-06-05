package software.aoc.challenges.day01;
import software.aoc.challenges.Solver;

public final class PartA implements Solver {

    @Override
    public long solve(String input) {
        return Dial.empty()
                .follow(input)
                .countTimesEndingAtZero();
    }
}