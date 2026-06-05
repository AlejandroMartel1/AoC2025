package software.aoc.application;
import software.aoc.challenges.day01.PartA;
import software.aoc.challenges.day01.PartB;
import software.aoc.io.InputLoader;
import software.aoc.challenges.Solver;
import java.util.Map;
import java.util.function.Supplier;

public class ChallengeOrchestrator {

    private final InputLoader loader = new InputLoader();

    private static final Map<SolverKey, Supplier<Solver>> SOLVERS = Map.of(
            new SolverKey(1, 'a'), PartA::new,
            new SolverKey(1, 'b'), PartB::new
    );

    public void run(int day, char part) {
        Solver solver = solverFor(day, part);
        String input = loader.load(day);
        long startNanos = System.nanoTime();
        long result = solver.solve(input);
        long elapsedMs = (System.nanoTime() - startNanos) / 1_000_000;
        System.out.printf("Dia %02d, parte %c: %d  (%d ms)%n", day, part, result, elapsedMs);
    }

    private Solver solverFor(int day, char part) {
        Supplier<Solver> factory = SOLVERS.get(new SolverKey(day, part));
        if (factory == null) {
            throw new IllegalArgumentException("Solver no encontrado: día " + day + " parte " + part);
        }
        return factory.get();
    }

    private record SolverKey(int day, char part) {}
}
