package software.aoc.challenges.day05;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class IngredientDataBaseTest {

    private static final String AdventExample = """
            3-5
            10-14
            16-20
            12-18

            1
            5
            8
            11
            17
            32
            """;

    @Test
    void anEmptyDatabaseHasNoFreshIngredients() {
        assertEquals(0, IngredientDataBase.empty().countFreshIngredients());
        assertEquals(0, IngredientDataBase.empty().countAllFreshIds());
    }

    @Test
    void aFreshRangeRecognizesIdsAtItsBoundsAndInside() {
        FreshRange range = FreshRange.parse("3-5");
        assertEquals(3L, range.first());
        assertEquals(5L, range.last());
        assertTrue(range.contains(3));
        assertTrue(range.contains(4));
        assertTrue(range.contains(5));
        assertFalse(range.contains(2));
        assertFalse(range.contains(6));
    }

    @Test
    void theDatabaseDetectsFreshIdsAmongAvailable() {
        long count = IngredientDataBase.empty()
                .loadedFrom(AdventExample)
                .countFreshIngredients();
        assertEquals(3, count);
    }

    @Test
    void theDatabaseCountsTotalFreshIdsAcrossDisjointRanges() {
        long total = IngredientDataBase.empty()
                .loadedFrom("1-2\n5-7\n\n")
                .countAllFreshIds();
        assertEquals(5, total);
    }

    @Test
    void theDatabaseMergesAdjacentRangesAsContinuous() {
        long total = IngredientDataBase.empty()
                .loadedFrom("1-5\n6-10\n\n")
                .countAllFreshIds();
        assertEquals(10, total);
    }

    @Test
    void theDatabaseMergesOverlappingRangesWithoutDoubleCounting() {
        long total = IngredientDataBase.empty()
                .loadedFrom("1-100\n10-50\n\n")
                .countAllFreshIds();
        assertEquals(100, total);
    }

    @Test
    void thePuzzleExampleProducesExpectedCounts() {
        IngredientDataBase database = IngredientDataBase.empty().loadedFrom(AdventExample);
        assertEquals(3L, database.countFreshIngredients());
        assertEquals(14L, database.countAllFreshIds());
    }

    @Test
    void theDatabaseIsImmutable() {
        IngredientDataBase original = IngredientDataBase.empty();
        IngredientDataBase loaded = original.loadedFrom(AdventExample);

        assertEquals(0, original.countFreshIngredients());
        assertEquals(3, loaded.countFreshIngredients());
    }

    @Test
    void realPuzzleInputProducesExpectedAnswers() throws IOException {
        String input = Files.readString(Path.of("src/main/resources/inputs/day05.txt"));
        IngredientDataBase database = IngredientDataBase.empty().loadedFrom(input);

        assertEquals(643L, database.countFreshIngredients());
        assertEquals(342018167474526L, database.countAllFreshIds());
    }
}