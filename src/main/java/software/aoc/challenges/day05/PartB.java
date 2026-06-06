package software.aoc.challenges.day05;
import software.aoc.challenges.Solver;

public class PartB implements Solver {
    @Override
    public long solve(String input) {
        return IngredientDataBase.empty()
                .loadedFrom(input)
                .countAllFreshIds();
    }
}
