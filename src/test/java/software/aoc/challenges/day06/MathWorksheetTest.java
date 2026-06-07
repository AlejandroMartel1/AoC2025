package software.aoc.challenges.day06;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MathWorksheetTest {

    private static final String AdventExample =
            """
                    123 328  51 64\s
                     45 64  387 23\s
                      6 98  215 314
                    *   +   *   + \s
                    """;

    @Test
    void anEmptyWorksheetHasZeroGrandTotal() {
        assertEquals(0, MathWorksheet.empty().grandTotal());
    }

    @Test
    void anOperationAppliesItsSemanticsAndKnowsItsIdentity() {
        assertEquals(5L, Operation.ADD.apply(2, 3));
        assertEquals(6L, Operation.MULTIPLY.apply(2, 3));
        assertEquals(0L, Operation.ADD.identity());
        assertEquals(1L, Operation.MULTIPLY.identity());
    }

    @Test
    void anOperationParsesFromItsSymbol() {
        assertEquals(Operation.ADD, Operation.fromSymbol('+'));
        assertEquals(Operation.MULTIPLY, Operation.fromSymbol('*'));
    }

    @Test
    void aMathProblemReducesItsNumbersWithItsOperation() {
        MathProblem sum = new MathProblem(List.of(1L, 2L, 3L), Operation.ADD);
        MathProblem product = new MathProblem(List.of(2L, 3L, 4L), Operation.MULTIPLY);

        assertEquals(6L, sum.result());
        assertEquals(24L, product.result());
    }

    @Test
    void aMathProblemWithoutNumbersFallsBackToTheOperationIdentity() {
        MathProblem emptySum = new MathProblem(List.of(), Operation.ADD);
        MathProblem emptyProduct = new MathProblem(List.of(), Operation.MULTIPLY);

        assertEquals(0L, emptySum.result());
        assertEquals(1L, emptyProduct.result());
    }

    @Test
    void theWorksheetReadsProblemsHorizontallyByDefault() {
        long total = MathWorksheet.empty()
                .loadedFrom(AdventExample)
                .grandTotal();

        assertEquals(4277556L, total);
    }

    @Test
    void theWorksheetReadsProblemsColumnarFromRightToLeft() {
        long total = MathWorksheet.empty()
                .loadedColumnarFrom(AdventExample)
                .grandTotal();

        assertEquals(3263827L, total);
    }

    @Test
    void theWorksheetIsImmutable() {
        MathWorksheet original = MathWorksheet.empty();
        MathWorksheet loaded = original.loadedFrom(AdventExample);

        assertEquals(0L, original.grandTotal());
        assertEquals(4277556L, loaded.grandTotal());
    }

    @Test
    void realPuzzleInputProducesExpectedAnswers() throws IOException {
        String input = Files.readString(Path.of("src/main/resources/inputs/day06.txt"));

        assertEquals(5316572080628L, MathWorksheet.empty().loadedFrom(input).grandTotal());
        assertEquals(11299263623062L, MathWorksheet.empty().loadedColumnarFrom(input).grandTotal());
    }
}