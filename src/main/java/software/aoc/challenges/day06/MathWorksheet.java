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
        return parseWith(input, MathWorksheet::parseHorizontally);
    }

    public MathWorksheet loadedColumnarFrom(String input) {
        return parseWith(input, MathWorksheet::parseColumnar);
    }

    public long grandTotal() {
        return problems.stream().mapToLong(MathProblem::result).sum();
    }

    private static MathWorksheet parseWith(String input, BlockParser blockParser) {
        ProblemSheet sheet = ProblemSheet.from(input);
        return new MathWorksheet(parseAllBlocks(sheet, blockParser));
    }

    private static List<MathProblem> parseAllBlocks(ProblemSheet sheet, BlockParser blockParser) {
        return sheet.blockRanges().stream()
                .map(range -> blockParser.parse(sheet, range[0], range[1]))
                .toList();
    }

    private static MathProblem parseHorizontally(ProblemSheet sheet, int startCol, int endCol) {
        return new MathProblem(numbersByRow(sheet, startCol, endCol), operatorByRow(sheet, startCol, endCol));
    }

    private static List<Long> numbersByRow(ProblemSheet sheet, int startCol, int endCol) {
        List<Long> numbers = new ArrayList<>();
        for (int row = 0; row < sheet.operatorRowIndex(); row++) {
            addIfNonEmpty(numbers, sheet.sliceAt(row, startCol, endCol));
        }
        return numbers;
    }

    private static Operation operatorByRow(ProblemSheet sheet, int startCol, int endCol) {
        return Operation.fromSymbol(sheet.sliceAt(sheet.operatorRowIndex(), startCol, endCol).charAt(0));
    }

    private static MathProblem parseColumnar(ProblemSheet sheet, int startCol, int endCol) {
        List<Long> numbers = new ArrayList<>();
        Operation operation = null;
        for (int col = endCol - 1; col >= startCol; col--) {
            addIfNonEmpty(numbers, digitsInColumn(sheet, col));
            Operation found = operatorAt(sheet, col);
            if (found != null) operation = found;
        }
        return new MathProblem(numbers, operation);
    }

    private static String digitsInColumn(ProblemSheet sheet, int col) {
        StringBuilder digits = new StringBuilder();
        for (int row = 0; row < sheet.operatorRowIndex(); row++) {
            appendIfNotSpace(digits, sheet.charAt(row, col));
        }
        return digits.toString();
    }

    private static Operation operatorAt(ProblemSheet sheet, int col) {
        char ch = sheet.charAt(sheet.operatorRowIndex(), col);
        return ch == ' ' ? null : Operation.fromSymbol(ch);
    }

    private static void addIfNonEmpty(List<Long> numbers, String numericText) {
        if (!numericText.isEmpty()) numbers.add(Long.parseLong(numericText));
    }

    private static void appendIfNotSpace(StringBuilder digits, char ch) {
        if (ch != ' ') digits.append(ch);
    }

    @FunctionalInterface
    private interface BlockParser {
        MathProblem parse(ProblemSheet sheet, int startCol, int endCol);
    }
}