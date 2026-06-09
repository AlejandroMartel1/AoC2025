package software.aoc.challenges.day09;
import java.util.List;

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
                input.lines()
                        .filter(line -> !line.isBlank())
                        .map(RedTile::parse)
                        .toList()
        );
    }

    public long largestRectangleArea() {
        long maxArea = 0L;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                long area = tiles.get(i).rectangleAreaWith(tiles.get(j));
                maxArea = Math.max(maxArea, area);
            }
        }
        return maxArea;
    }

    public long largestGreenRectangleArea() {
        if (tiles.size() < 2) return 0;

        PolygonGrid grid = new PolygonGrid(tiles);
        long maxArea = 0L;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                RedTile a = tiles.get(i);
                RedTile b = tiles.get(j);
                if (grid.containsRectangleBetween(a, b)) {
                    long area = a.rectangleAreaWith(b);
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        return maxArea;
    }
}