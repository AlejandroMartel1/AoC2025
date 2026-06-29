package software.aoc.challenges.day12;

public record Position(int row, int col) {

    public Position translatedBy(int dRow, int dCol) {
        return new Position(row + dRow, col + dCol);
    }
}