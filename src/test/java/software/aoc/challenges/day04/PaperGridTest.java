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
        assertEquals(0, TrashCompactor.empty().countAccessibleRolls());
        assertEquals(0, TrashCompactor.empty().countTotalRemovableRolls());
    }

    @Test
    void aGridWithoutRollsHasNoAccessibleRolls() {
        long accessible = TrashCompactor.empty()
                .withLayoutFrom("....\n....\n....")
                .countAccessibleRolls();
        assertEquals(0, accessible);
    }

    @Test
    void aSingleIsolatedRollIsAccessible() {
        long accessible = TrashCompactor.empty()
                .withLayoutFrom(".....\n..@..\n.....")
                .countAccessibleRolls();
        assertEquals(1, accessible);
    }

    @Test
    void aRollSurroundedByEightRollsIsNotAccessible() {
        long accessible = TrashCompactor.empty()
                .withLayoutFrom("@@@\n@@@\n@@@")
                .countAccessibleRolls();
        assertEquals(4, accessible);
    }

    @Test
    void aRollAtTheGridBorderCountsFewerNeighbors() {
        long accessible = TrashCompactor.empty()
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
        long removable = TrashCompactor.empty()
                .withLayoutFrom("@.@.@")
                .countTotalRemovableRolls();
        assertEquals(3, removable);
    }

    @Test
    void theIterativeRemovalEventuallyClearsAccessibleClusters() {
        long removable = TrashCompactor.empty()
                .withLayoutFrom("@@@\n@@@\n@@@")
                .countTotalRemovableRolls();
        assertEquals(9, removable);
    }

    @Test
    void thePuzzleExampleProducesExpectedCounts() {
        TrashCompactor compactor = TrashCompactor.empty().withLayoutFrom(AdventExample);
        assertEquals(13, compactor.countAccessibleRolls());
        assertEquals(43, compactor.countTotalRemovableRolls());
    }

    @Test
    void thePaperGridIsImmutable() {
        TrashCompactor original = TrashCompactor.empty();
        TrashCompactor loaded = original.withLayoutFrom("..@..\n.....\n..@..");

        assertEquals(0, original.countAccessibleRolls());
        assertEquals(2, loaded.countAccessibleRolls());
    }

    @Test
    void realPuzzleInputProducesExpectedAnswers() throws IOException {
        String input = Files.readString(Path.of("src/main/resources/inputs/day04.txt"));
        TrashCompactor compactor = TrashCompactor.empty().withLayoutFrom(input);

        assertEquals(1349L, compactor.countAccessibleRolls());
        assertEquals(8277L, compactor.countTotalRemovableRolls());
    }
}