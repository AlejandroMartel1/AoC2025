package software.aoc.challenges.day01;
import software.aoc.challenges.Solver;

public final class PartB implements Solver {

    @Override
    public int solve(String input) {
        return Dial.empty()
                .follow(input)
                .countTimesPassingZero();
    }
}