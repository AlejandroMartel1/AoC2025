package software.aoc.challenges.day09;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieTheaterTest {

    private static final String AdventExample = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
            """;

    @Test
    void anEmptyMovieTheaterHasNoRectangleArea() {
        assertEquals(0, MovieTheater.empty().largestRectangleArea());
        assertEquals(0, MovieTheater.empty().largestGreenRectangleArea());
    }

    @Test
    void aRedTileIsParsedFromCommaSeparatedCoordinates() {
        RedTile tile = RedTile.parse("162,817");
        assertEquals(162L, tile.x());
        assertEquals(817L, tile.y());
    }

    @Test
    void aRedTileComputesRectangleAreaCountingTilesInclusive() {
        assertEquals(24L, new RedTile(2, 5).rectangleAreaWith(new RedTile(9, 7)));
        assertEquals(35L, new RedTile(7, 1).rectangleAreaWith(new RedTile(11, 7)));
        assertEquals(6L, new RedTile(7, 3).rectangleAreaWith(new RedTile(2, 3)));
        assertEquals(50L, new RedTile(2, 5).rectangleAreaWith(new RedTile(11, 1)));
    }

    @Test
    void theRectangleAreaIsSymmetric() {
        RedTile a = new RedTile(2, 5);
        RedTile b = new RedTile(9, 7);
        assertEquals(a.rectangleAreaWith(b), b.rectangleAreaWith(a));
    }

    @Test
    void thePuzzleExampleProducesExpectedLargestRectangle() {
        long area = MovieTheater.empty()
                .withRedTilesFrom(AdventExample)
                .largestRectangleArea();
        assertEquals(50L, area);
    }

    @Test
    void thePuzzleExampleProducesExpectedLargestGreenRectangle() {
        long area = MovieTheater.empty()
                .withRedTilesFrom(AdventExample)
                .largestGreenRectangleArea();
        assertEquals(24L, area);
    }

    @Test
    void thePolygonGridAcceptsARectangleFullyInsideTheClosedPolygon() {
        MovieTheater theater = MovieTheater.empty().withRedTilesFrom(AdventExample);
        assertEquals(24L, theater.largestGreenRectangleArea());
    }

    @Test
    void theMovieTheaterIsImmutable() {
        MovieTheater original = MovieTheater.empty();
        MovieTheater loaded = original.withRedTilesFrom(AdventExample);

        assertEquals(0, original.largestRectangleArea());
        assertEquals(50, loaded.largestRectangleArea());
    }

    @Test
    void realPuzzleInputProducesExpectedAnswers() throws IOException {
        String input = Files.readString(Path.of("src/main/resources/inputs/day09.txt"));
        MovieTheater theater = MovieTheater.empty().withRedTilesFrom(input);

        assertEquals(4754955192L, theater.largestRectangleArea());
        assertEquals(1568849600L, theater.largestGreenRectangleArea());
    }
}