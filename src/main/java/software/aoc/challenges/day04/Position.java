package software.aoc.challenges.day04;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Position(int row, int col) {

    public Stream<Position> neighbors() {
        return IntStream.rangeClosed(-1, 1).boxed()
                .flatMap(dr -> IntStream.rangeClosed(-1, 1)
                        .filter(dc -> dr != 0 || dc != 0)
                        .mapToObj(dc -> new Position(row + dr, col + dc)));
    }
}