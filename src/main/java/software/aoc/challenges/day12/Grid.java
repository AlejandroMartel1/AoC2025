package software.aoc.challenges.day12;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Grid {

    private final int width;
    private final int height;
    private final boolean[][] occupied;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.occupied = new boolean[height][width];
    }

    public boolean canPlace(Shape shape, int rowOffset, int colOffset) {
        for (Position p : shape.cells()) {
            if (!isFreeAt(rowOffset + p.row(), colOffset + p.col())) return false;
        }
        return true;
    }

    public void place(Shape shape, int rowOffset, int colOffset) {
        for (Position p : shape.cells()) occupied[rowOffset + p.row()][colOffset + p.col()] = true;
    }

    public void remove(Shape shape, int rowOffset, int colOffset) {
        for (Position p : shape.cells()) occupied[rowOffset + p.row()][colOffset + p.col()] = false;
    }

    public Stream<Position> validAnchorsFor(Shape shape) {
        int rowLimit = height - shape.height();
        int colLimit = width - shape.width();
        if (rowLimit < 0 || colLimit < 0) return Stream.empty();
        return IntStream.rangeClosed(0, rowLimit).boxed()
                .flatMap(r -> IntStream.rangeClosed(0, colLimit)
                        .mapToObj(c -> new Position(r, c)));
    }

    public int contentHash() {
        return Arrays.deepHashCode(occupied);
    }

    private boolean isFreeAt(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width && !occupied[row][col];
    }
}