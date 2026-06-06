package software.aoc.challenges.day04;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaperGridTest {

    private static final String AdventExample = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
            """;

    @Test
    void anEmptyGridHasNoAccessibleOrRemovableRolls() {
        assertEquals(0, PaperGrid.empty().countAccessibleRolls());
        assertEquals(0, PaperGrid.empty().countTotalRemovableRolls());
    }

    @Test
    void aGridWithoutRollsHasNoAccessibleRolls() {
        long accessible = PaperGrid.empty()
                .withLayoutFrom("....\n....\n....")
                .countAccessibleRolls();
        assertEquals(0, accessible);
    }

    @Test
    void aSingleIsolatedRollIsAccessible() {
        long accessible = PaperGrid.empty()
                .withLayoutFrom(".....\n..@..\n.....")
                .countAccessibleRolls();
        assertEquals(1, accessible);
    }

    @Test
    void aRollSurroundedByEightRollsIsNotAccessible() {
        long accessible = PaperGrid.empty()
                .withLayoutFrom("@@@\n@@@\n@@@")
                .countAccessibleRolls();
        assertEquals(4, accessible);
    }

    @Test
    void aRollAtTheGridBorderCountsFewerNeighbors() {
        long accessible = PaperGrid.empty()
                .withLayoutFrom("""
                                           @...
                                           ....
                                           ....
                                           """)
                .countAccessibleRolls();
        assertEquals(1, accessible);
    }

    @Test
    void allRollsBecomeRemovableWhenIsolated() {
        long removable = PaperGrid.empty()
                .withLayoutFrom("@.@.@")
                .countTotalRemovableRolls();
        assertEquals(3, removable);
    }

    @Test
    void theIterativeRemovalEventuallyClearsAccessibleClusters() {
        long removable = PaperGrid.empty()
                .withLayoutFrom("@@@\n@@@\n@@@")
                .countTotalRemovableRolls();
        assertEquals(9, removable);
    }

    @Test
    void thePuzzleExampleProducesExpectedCounts() {
        PaperGrid grid = PaperGrid.empty().withLayoutFrom(AdventExample);
        assertEquals(13, grid.countAccessibleRolls());
        assertEquals(43, grid.countTotalRemovableRolls());
    }

    @Test
    void thePaperGridIsImmutable() {
        PaperGrid original = PaperGrid.empty();
        PaperGrid loaded = original.withLayoutFrom("..@..\n.....\n..@..");

        assertEquals(0, original.countAccessibleRolls());
        assertEquals(2, loaded.countAccessibleRolls());
    }

    @Test
    void realPuzzleInputProducesExpectedAnswers() throws IOException {
        String input = Files.readString(Path.of("src/main/resources/inputs/day04.txt"));
        PaperGrid grid = PaperGrid.empty().withLayoutFrom(input);

        assertEquals(1349L, grid.countAccessibleRolls());
        assertEquals(8277L, grid.countTotalRemovableRolls());
    }
}