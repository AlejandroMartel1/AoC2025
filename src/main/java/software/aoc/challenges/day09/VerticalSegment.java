package software.aoc.challenges.day09;
import java.util.List;
import java.util.stream.IntStream;

public record VerticalSegment(long x, long y1, long y2) {

    public static List<VerticalSegment> extractFrom(List<RedTile> vertices) {
        return IntStream.range(0, vertices.size())
                .filter(i -> isVerticalEdgeAt(vertices, i))
                .mapToObj(i -> segmentBetween(vertices, i))
                .toList();
    }

    public boolean crossesRightRayFrom(double cx, double cy) {
        return x > cx && y1 < cy && cy < y2;
    }

    private static boolean isVerticalEdgeAt(List<RedTile> vertices, int i) {
        return vertices.get(i).x() == vertices.get((i + 1) % vertices.size()).x();
    }

    private static VerticalSegment segmentBetween(List<RedTile> vertices, int i) {
        RedTile a = vertices.get(i);
        RedTile b = vertices.get((i + 1) % vertices.size());
        return new VerticalSegment(a.x(), Math.min(a.y(), b.y()), Math.max(a.y(), b.y()));
    }
}