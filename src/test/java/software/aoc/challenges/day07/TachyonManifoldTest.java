package software.aoc.challenges.day07;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TachyonManifoldTest {

    private static final String AdventExample = """
            .......S.......
            ...............
            .......^.......
            ...............
            ......^.^......
            ...............
            .....^.^.^.....
            ...............
            ....^.^...^....
            ...............
            ...^.^...^.^...
            ...............
            ..^...^.....^..
            ...............
            .^.^.^.^.^...^.
            ...............
            """;

    @Test
    void anEmptyManifoldHasNoSplitsAndNoTimelines() {
        assertEquals(0, TachyonManifold.empty().countSplits());
        assertEquals(0, TachyonManifold.empty().countTimelines());
    }

    @Test
    void aManifoldWithoutSplittersProducesOneTimelineAndZeroSplits() {
        String diagram = """
                ...S...
                .......
                .......
                """;
        TachyonManifold manifold = TachyonManifold.empty().withDiagramFrom(diagram);

        assertEquals(0, manifold.countSplits());
        assertEquals(1, manifold.countTimelines());
    }

    @Test
    void aSingleSplitterDoublesTheTimelinesAndCountsOneSplit() {
        String diagram = """
                ...S...
                .......
                ...^...
                .......
                """;
        TachyonManifold manifold = TachyonManifold.empty().withDiagramFrom(diagram);

        assertEquals(1, manifold.countSplits());
        assertEquals(2, manifold.countTimelines());
    }

    @Test
    void twoBeamsConvergingOnASameColumnAreOneBeamButTwoTimelines() {
        String diagram = """
                ...S...
                .......
                ...^...
                .......
                ..^.^..
                .......
                """;
        TachyonManifold manifold = TachyonManifold.empty().withDiagramFrom(diagram);
        assertEquals(4, manifold.countTimelines());
    }

    @Test
    void aSplitterAtTheLeftBorderLosesItsLeftEmission() {
        String diagram = """
                S......
                .......
                ^......
                .......
                """;
        TachyonManifold manifold = TachyonManifold.empty().withDiagramFrom(diagram);

        assertEquals(1, manifold.countSplits());
        assertEquals(1, manifold.countTimelines());
    }

    @Test
    void thePuzzleExampleProducesExpectedSplitAndTimelineCounts() {
        TachyonManifold manifold = TachyonManifold.empty().withDiagramFrom(AdventExample);

        assertEquals(21, manifold.countSplits());
        assertEquals(40, manifold.countTimelines());
    }

    @Test
    void theManifoldIsImmutable() {
        TachyonManifold original = TachyonManifold.empty();
        TachyonManifold loaded = original.withDiagramFrom(AdventExample);

        assertEquals(0, original.countSplits());
        assertEquals(21, loaded.countSplits());
    }

    @Test
    void realPuzzleInputProducesExpectedAnswers() throws IOException {
        String input = Files.readString(Path.of("src/main/resources/inputs/day07.txt"));
        TachyonManifold manifold = TachyonManifold.empty().withDiagramFrom(input);

        assertEquals(1649L, manifold.countSplits());
        assertEquals(16937871060075L, manifold.countTimelines());
    }
}
