package software.aoc.challenges.day01;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DialTest {

    private static final String PUZZLE_EXAMPLE = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
            """;

    @Test
    void anEmptyDialNeverPointsAtZero() {
        assertEquals(0, Dial.empty().countTimesEndingAtZero());
        assertEquals(0, Dial.empty().countTimesPassingZero());
    }

    @Test
    void theDialCountsStopsAtZero() {
        assertEquals(0, Dial.empty().follow("L1").countTimesEndingAtZero());
        assertEquals(1, Dial.empty().follow("R50").countTimesEndingAtZero());
        assertEquals(1, Dial.empty().follow("L1", "R1", "R50").countTimesEndingAtZero());
        assertEquals(0, Dial.empty().follow("L51", "L500").countTimesEndingAtZero());
    }

    @Test
    void theDialCountsZeroCrossings() {
        assertEquals(1, Dial.empty().follow("R50").countTimesPassingZero());
        assertEquals(1, Dial.empty().follow("L60").countTimesPassingZero());
        assertEquals(2, Dial.empty().follow("L150").countTimesPassingZero());
        assertEquals(10, Dial.empty().follow("R1000").countTimesPassingZero());
    }

    @Test
    void thePuzzleExampleProducesExpectedCounts() {
        Dial dial = Dial.empty().follow(PUZZLE_EXAMPLE);
        assertEquals(3, dial.countTimesEndingAtZero());
        assertEquals(6, dial.countTimesPassingZero());
    }

    @Test
    void theDialIsImmutable() {
        Dial original = Dial.empty();
        Dial rotated = original.follow("R50");

        assertEquals(0, original.countTimesEndingAtZero());
        assertEquals(1, rotated.countTimesEndingAtZero());
    }

    @Test
    void realPuzzleInputProducesExpectedAnswers() throws IOException {
        String input = Files.readString(Path.of("src/main/resources/inputs/day01.txt"));
        Dial dial = Dial.empty().follow(input);

        assertEquals(992, dial.countTimesEndingAtZero());
        assertEquals(6133, dial.countTimesPassingZero());
    }
}