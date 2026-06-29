package software.aoc.challenges.day04;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class PaperGrid {

    private final List<String> rows;

    public PaperGrid(List<String> rows) {
        this.rows = rows;
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
        char[][] matrix = toMatrix();
        positions.forEach(p -> matrix[p.row()][p.col()] = value);
        return new PaperGrid(matrixAsRows(matrix));
    }

    private char[][] toMatrix() {
        return rows.stream().map(String::toCharArray).toArray(char[][]::new);
    }

    private static List<String> matrixAsRows(char[][] matrix) {
        return Arrays.stream(matrix).map(String::new).toList();
    }
}