package software.aoc.challenges.day12;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChristmasTreeFarmTest {

    private static final String AdventExample = """
            0:
            ###
            ##.
            ##.

            1:
            ###
            ##.
            .##

            2:
            .##
            ###
            ##.

            3:
            ##.
            ###
            ##.

            4:
            ###
            #..
            ###

            5:
            ###
            .#.
            ###

            4x4: 0 0 0 0 2 0
            12x5: 1 0 1 0 2 2
            12x5: 1 0 1 0 3 2
            """;

    @Test
    void anEmptyFarmCountsZeroRegions() {
        assertEquals(0L, ChristmasTreeFarm.empty().countRegionsThatFit());
    }

    @Test
    void thePuzzleExampleCountsTwoFittingRegions() {
        long count = ChristmasTreeFarm.empty().loadedFrom(AdventExample).countRegionsThatFit();
        assertEquals(2L, count);
    }

    @Test
    void theFarmIsImmutable() {
        ChristmasTreeFarm original = ChristmasTreeFarm.empty();
        ChristmasTreeFarm loaded = original.loadedFrom(AdventExample);
        assertEquals(0L, original.countRegionsThatFit());
        assertEquals(2L, loaded.countRegionsThatFit());
    }

    @Test
    void realPuzzleInputProducesExpectedAnswer() throws IOException {
        String input = Files.readString(Path.of("src/main/resources/inputs/day12.txt"));
        long count = ChristmasTreeFarm.empty().loadedFrom(input).countRegionsThatFit();
        // Sustituye XXX por la respuesta correcta de AoC cuando la tengas
        // assertEquals(XXX, count);
        System.out.println("Día 12 PartA: " + count);
    }
}
