package software.aoc.challenges.day09;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class MovieTheater {

    private final List<RedTile> tiles;

    private MovieTheater(List<RedTile> tiles) {
        this.tiles = tiles;
    }

    public static MovieTheater empty() {
        return new MovieTheater(List.of());
    }

    public MovieTheater withRedTilesFrom(String input) {
        return new MovieTheater(
                input.lines().filter(line -> !line.isBlank()).map(RedTile::parse).toList()
        );
    }

    public long largestRectangleArea() {
        return allTilePairs().mapToLong(this::areaOf).max().orElse(0L);
    }

    public long largestGreenRectangleArea() {
        if (tiles.size() < 2) return 0;
        PolygonGrid grid = new PolygonGrid(tiles);
        return allTilePairs()
                .filter(pair -> grid.containsRectangleBetween(pair[0], pair[1]))
                .mapToLong(this::areaOf)
                .max().orElse(0L);
    }

    private long areaOf(RedTile[] pair) {
        return pair[0].rectangleAreaWith(pair[1]);
    }

    private Stream<RedTile[]> allTilePairs() {
        return IntStream.range(0, tiles.size()).boxed()
                .flatMap(i -> IntStream.range(i + 1, tiles.size())
                        .mapToObj(j -> new RedTile[] { tiles.get(i), tiles.get(j) }));
    }
}