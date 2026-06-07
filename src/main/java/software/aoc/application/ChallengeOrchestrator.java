package software.aoc.application;
import software.aoc.io.InputLoader;
import software.aoc.challenges.Solver;
import java.util.Map;
import java.util.function.Supplier;
import static java.util.Map.entry;

public class ChallengeOrchestrator {

    private final InputLoader loader = new InputLoader();

    private static final Map<SolverKey, Supplier<Solver>> solvers = Map.ofEntries(
            entry(new SolverKey(1, 'a'), software.aoc.challenges.day01.PartA::new),
            entry(new SolverKey(1, 'b'), software.aoc.challenges.day01.PartB::new),
            entry(new SolverKey(2, 'a'), software.aoc.challenges.day02.PartA::new),
            entry(new SolverKey(2, 'b'), software.aoc.challenges.day02.PartB::new),
            entry(new SolverKey(3, 'a'), software.aoc.challenges.day03.PartA::new),
            entry(new SolverKey(3, 'b'), software.aoc.challenges.day03.PartB::new),
            entry(new SolverKey(4, 'a'), software.aoc.challenges.day04.PartA::new),
            entry(new SolverKey(4, 'b'), software.aoc.challenges.day04.PartB::new),
            entry(new SolverKey(5, 'a'), software.aoc.challenges.day05.PartA::new),
            entry(new SolverKey(5, 'b'), software.aoc.challenges.day05.PartB::new),
            entry(new SolverKey(6, 'a'), software.aoc.challenges.day06.PartA::new),
            entry(new SolverKey(6, 'b'), software.aoc.challenges.day06.PartB::new),
            entry(new SolverKey(7, 'a'), software.aoc.challenges.day07.PartA::new),
            entry(new SolverKey(7, 'b'), software.aoc.challenges.day07.PartB::new)
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
        Supplier<Solver> factory = solvers.get(new SolverKey(day, part));
        if (factory == null) {
            throw new IllegalArgumentException("Solver no  ha encontrado el día " + day + " parte " + part);
        }
        return factory.get();
    }

    private record SolverKey(int day, char part) {}
}
