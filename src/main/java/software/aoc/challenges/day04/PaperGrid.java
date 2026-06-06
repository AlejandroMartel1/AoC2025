package software.aoc.challenges.day04;
import java.util.ArrayList;
import java.util.List;

public final class PaperGrid {

    private static final char roll = '@';
    private static final char empty = '.';
    private static final int threshold = 4;
    private final List<String> rows;

    private PaperGrid(List<String> rows) {
        this.rows = rows;
    }

    public static PaperGrid empty() {
        return new PaperGrid(List.of());
    }

    public PaperGrid withLayoutFrom(String input) {
        return new PaperGrid(
                input.lines()
                        .filter(line -> !line.isBlank())
                        .toList()
        );
    }

    public long countAccessibleRolls() {
        return accessiblePositions().size();
    }

    public long countTotalRemovableRolls() {
        PaperGrid current = this;
        long totalRemoved = 0;
        while (true) {
            long accessibleNow = current.countAccessibleRolls();
            if (accessibleNow == 0) break;
            totalRemoved += accessibleNow;
            current = current.withoutAccessibleRolls();
        }
        return totalRemoved;
    }

    private PaperGrid withoutAccessibleRolls() {
        List<int[]> toRemove = accessiblePositions();
        char[][] newRows = toCharMatrix();
        for (int[] position : toRemove) {
            newRows[position[0]][position[1]] = empty;
        }
        return new PaperGrid(matrixToRowList(newRows));
    }

    private List<int[]> accessiblePositions() {
        List<int[]> positions = new ArrayList<>();
        for (int row = 0; row < rows.size(); row++) {
            String currentRow = rows.get(row);
            for (int col = 0; col < currentRow.length(); col++) {
                if (currentRow.charAt(col) == roll && isAccessible(row, col)) {
                    positions.add(new int[] {row, col});
                }
            }
        }
        return positions;
    }

    private boolean isAccessible(int row, int col) {
        return countRollNeighbors(row, col) < threshold;
    }

    private int countRollNeighbors(int row, int col) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                if (hasRollAt(row + dr, col + dc)) count++;
            }
        }
        return count;
    }

    private boolean hasRollAt(int row, int col) {
        if (row < 0 || row >= rows.size()) return false;
        String line = rows.get(row);
        if (col < 0 || col >= line.length()) return false;
        return line.charAt(col) == roll;
    }

    private char[][] toCharMatrix() {
        char[][] matrix = new char[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            matrix[i] = rows.get(i).toCharArray();
        }
        return matrix;
    }

    private List<String> matrixToRowList(char[][] matrix) {
        List<String> result = new ArrayList<>(matrix.length);
        for (char[] row : matrix) {
            result.add(new String(row));
        }
        return result;
    }
}