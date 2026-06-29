package software.aoc.challenges.day06;

import java.util.ArrayList;
import java.util.List;

public record ProblemSheet(List<String> rows, int width) {

    public static ProblemSheet from(String input) {
        List<String> raw = input.lines().toList();
        int width = widthOf(raw);
        return new ProblemSheet(paddedRows(raw, width), width);
    }

    public char charAt(int row, int col) {
        return rows.get(row).charAt(col);
    }

    public int operatorRowIndex() {
        return rows.size() - 1;
    }

    public String sliceAt(int row, int startCol, int endCol) {
        return rows.get(row).substring(startCol, endCol).trim();
    }

    public List<int[]> blockRanges() {
        List<int[]> ranges = new ArrayList<>();
        int start = -1;
        for (int col = 0; col <= width; col++) start = stepBoundary(col, start, ranges);
        return ranges;
    }

    private int stepBoundary(int col, int start, List<int[]> ranges) {
        if (isBoundaryAt(col) && start != -1) { ranges.add(new int[] { start, col }); return -1; }
        if (!isBoundaryAt(col) && start == -1) return col;
        return start;
    }

    private boolean isBoundaryAt(int col) {
        return col == width || isColumnAllSpaces(col);
    }

    private boolean isColumnAllSpaces(int col) {
        return rows.stream().allMatch(row -> row.charAt(col) == ' ');
    }

    private static int widthOf(List<String> rows) {
        return rows.stream().mapToInt(String::length).max().orElse(0);
    }

    private static List<String> paddedRows(List<String> rows, int width) {
        return rows.stream().map(line -> padRight(line, width)).toList();
    }

    private static String padRight(String s, int width) {
        return s.length() >= width ? s : s + " ".repeat(width - s.length());
    }
}