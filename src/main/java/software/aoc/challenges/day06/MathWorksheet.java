package software.aoc.challenges.day06;
import java.util.ArrayList;
import java.util.List;


public final class MathWorksheet {

    private final List<MathProblem> problems;

    private MathWorksheet(List<MathProblem> problems) {
        this.problems = problems;
    }

    public static MathWorksheet empty() {
        return new MathWorksheet(List.of());
    }

    public MathWorksheet loadedFrom(String input) {
        return parseUsing(input, MathWorksheet::parseProblemHorizontally);
    }

    public MathWorksheet loadedColumnarFrom(String input) {
        return parseUsing(input, MathWorksheet::parseProblemColumnar);
    }

    public long grandTotal() {
        return problems.stream()
                .mapToLong(MathProblem::result)
                .sum();
    }

    private MathWorksheet parseUsing(String input, BlockParser blockParser) {
        List<String> rows = input.lines().toList();
        int width = rows.stream().mapToInt(String::length).max().orElse(0);
        List<String> paddedRows = rows.stream()
                .map(row -> padRight(row, width))
                .toList();
        List<MathProblem> parsed = new ArrayList<>();
        int start = -1;
        for (int col = 0; col <= width; col++) {
            boolean allSpace = col == width || isAllSpaceAt(paddedRows, col);
            if (!allSpace && start == -1) {
                start = col;
            } else if (allSpace && start != -1) {
                parsed.add(blockParser.parse(paddedRows, start, col));
                start = -1;
            }
        }
        return new MathWorksheet(parsed);
    }

    private static MathProblem parseProblemHorizontally(List<String> rows, int startCol, int endCol) {
        int last = rows.size() - 1;
        List<Long> numbers = new ArrayList<>();
        for (int r = 0; r < last; r++) {
            String slice = rows.get(r).substring(startCol, endCol).trim();
            if (!slice.isEmpty()) numbers.add(Long.parseLong(slice));
        }
        String operatorSlice = rows.get(last).substring(startCol, endCol).trim();
        return new MathProblem(numbers, Operation.fromSymbol(operatorSlice.charAt(0)));
    }

    private static MathProblem parseProblemColumnar(List<String> rows, int startCol, int endCol) {
        int last = rows.size() - 1;
        List<Long> numbers = new ArrayList<>();
        Operation operation = null;
        for (int c = endCol - 1; c >= startCol; c--) {
            StringBuilder digits = new StringBuilder();
            for (int r = 0; r < last; r++) {
                char ch = rows.get(r).charAt(c);
                if (ch != ' ') digits.append(ch);
            }
            char operatorChar = rows.get(last).charAt(c);
            if (operatorChar != ' ') {
                operation = Operation.fromSymbol(operatorChar);
            }
            if (!digits.isEmpty()) {
                numbers.add(Long.parseLong(digits.toString()));
            }
        }
        return new MathProblem(numbers, operation);
    }

    private static boolean isAllSpaceAt(List<String> rows, int col) {
        for (String row : rows) {
            if (row.charAt(col) != ' ') return false;
        }
        return true;
    }

    private static String padRight(String s, int width) {
        return s.length() >= width ? s : s + " ".repeat(width - s.length());
    }

    @FunctionalInterface
    private interface BlockParser {
        MathProblem parse(List<String> rows, int startCol, int endCol);
    }
}