package software.aoc.challenges.day12;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record Shape(Set<Position> cells) {

    public static Shape parse(List<String> rows) {
        Set<Position> filled = new HashSet<>();
        for (int r = 0; r < rows.size(); r++) {
            for (int c = 0; c < rows.get(r).length(); c++) {
                if (rows.get(r).charAt(c) == '#') filled.add(new Position(r, c));
            }
        }
        return new Shape(normalized(filled));
    }

    public Set<Shape> uniqueOrientations() {
        Set<Shape> result = new HashSet<>();
        Shape current = this;
        for (int rotation = 0; rotation < 4; rotation++) {
            result.add(current);
            result.add(current.flipped());
            current = current.rotated();
        }
        return result;
    }

    public Shape rotated() {
        Set<Position> rotated = new HashSet<>();
        for (Position p : cells) rotated.add(new Position(p.col(), -p.row()));
        return new Shape(normalized(rotated));
    }

    public Shape flipped() {
        Set<Position> flipped = new HashSet<>();
        for (Position p : cells) flipped.add(new Position(p.row(), -p.col()));
        return new Shape(normalized(flipped));
    }

    public int size() {
        return cells.size();
    }

    public int width() {
        return cells.stream().mapToInt(Position::col).max().orElse(-1) + 1;
    }

    public int height() {
        return cells.stream().mapToInt(Position::row).max().orElse(-1) + 1;
    }

    public int boundingArea() {
        return width() * height();
    }

    private static Set<Position> normalized(Set<Position> cells) {
        int minRow = cells.stream().mapToInt(Position::row).min().orElse(0);
        int minCol = cells.stream().mapToInt(Position::col).min().orElse(0);
        Set<Position> result = new HashSet<>();
        for (Position p : cells) result.add(p.translatedBy(-minRow, -minCol));
        return result;
    }
}