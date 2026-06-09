package software.aoc.challenges.day09;
import software.aoc.challenges.Solver;

public class PartB implements Solver {

    @Override
    public long solve(String input) {
        return MovieTheater.empty()
                .withRedTilesFrom(input)
                .largestGreenRectangleArea();
    }
}
