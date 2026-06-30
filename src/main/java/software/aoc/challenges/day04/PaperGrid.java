package software.aoc.challenges.day04;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record PaperGrid(List<String> rows) {

    public PaperGrid {
        rows = List.copyOf(rows);
    }

    public boolean isInside(Position p) {
        return p.row() >= 0 && p.row() < rows.size()
                && p.col() >= 0 && p.col() < rows.get(p.row()).length();
    }

    public char at(Position p) {
        return rows.get(p.row()).charAt(p.col());
    }

    public Stream<Position> allPositions() {
        return IntStream.range(0, rows.size()).boxed()
                .flatMap(r -> IntStream.range(0, rows.get(r).length())
                        .mapToObj(c -> new Position(r, c)));
    }

    public PaperGrid withAllAt(Collection<Position> positions, char value) {
        char[][] tempMatrix = rows.stream().map(String::toCharArray).toArray(char[][]::new);

        positions.forEach(p -> tempMatrix[p.row()][p.col()] = value);

        return new PaperGrid(
                Arrays.stream(tempMatrix).map(String::new).toList()
        );
    }
}