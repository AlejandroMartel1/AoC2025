package software.aoc.challenges.day06;
import java.util.List;

public record MathProblem(List<Long> numbers, Operation operation) {

    public long result() {
        return numbers.stream()
                .reduce(operation.identity(), operation::apply);
    }
}

