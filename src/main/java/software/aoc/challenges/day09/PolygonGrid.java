package software.aoc.challenges.day09;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public final class PolygonGrid {

    private final long[] xs;
    private final long[] ys;
    private final Map<Long, Integer> xIndex;
    private final Map<Long, Integer> yIndex;
    private final long[][] insidePrefixSum;

    public PolygonGrid(List<RedTile> polygonVertices) {
        this.xs = uniqueSorted(polygonVertices.stream().mapToLong(RedTile::x).toArray());
        this.ys = uniqueSorted(polygonVertices.stream().mapToLong(RedTile::y).toArray());
        this.xIndex = indexOf(xs);
        this.yIndex = indexOf(ys);
        this.insidePrefixSum = buildPrefixSum(VerticalSegment.extractFrom(polygonVertices));
    }

    public boolean containsRectangleBetween(RedTile r1, RedTile r2) {
        int iMin = xIndex.get(Math.min(r1.x(), r2.x()));
        int iMax = xIndex.get(Math.max(r1.x(), r2.x()));
        int jMin = yIndex.get(Math.min(r1.y(), r2.y()));
        int jMax = yIndex.get(Math.max(r1.y(), r2.y()));
        return matchesPolygonShape(iMin, iMax, jMin, jMax);
    }

    private boolean matchesPolygonShape(int iMin, int iMax, int jMin, int jMax) {
        if (iMin < iMax && jMin < jMax) return isFullRectangleInside(iMin, iMax, jMin, jMax);
        if (iMin == iMax && jMin < jMax) return verticalLineInside(iMin, jMin, jMax);
        if (jMin == jMax && iMin < iMax) return horizontalLineInside(jMin, iMin, iMax);
        return false;
    }

    private boolean isFullRectangleInside(int iMin, int iMax, int jMin, int jMax) {
        long expected = (long) (iMax - iMin) * (jMax - jMin);
        return rectangleSumOver(iMin, iMax, jMin, jMax) == expected;
    }

    private boolean isCellInside(int i, int j) {
        return rectangleSumOver(i, i + 1, j, j + 1) == 1L;
    }

    private long rectangleSumOver(int iMin, int iMax, int jMin, int jMax) {
        return insidePrefixSum[iMax][jMax] - insidePrefixSum[iMin][jMax] - insidePrefixSum[iMax][jMin]
                + insidePrefixSum[iMin][jMin];
    }

    private boolean verticalLineInside(int i, int jMin, int jMax) {
        return IntStream.range(jMin, jMax).allMatch(j -> verticalEdgeAt(i, j));
    }

    private boolean horizontalLineInside(int j, int iMin, int iMax) {
        return IntStream.range(iMin, iMax).allMatch(i -> horizontalEdgeAt(i, j));
    }

    private boolean verticalEdgeAt(int i, int j) {
        return cellInsideOrLeft(i, j) || cellInsideOrRight(i, j);
    }

    private boolean horizontalEdgeAt(int i, int j) {
        return cellInsideOrBelow(i, j) || cellInsideOrAbove(i, j);
    }

    private boolean cellInsideOrLeft(int i, int j) {
        return i - 1 >= 0 && isCellInside(i - 1, j);
    }

    private boolean cellInsideOrRight(int i, int j) {
        return i < xs.length - 1 && isCellInside(i, j);
    }

    private boolean cellInsideOrBelow(int i, int j) {
        return j - 1 >= 0 && isCellInside(i, j - 1);
    }

    private boolean cellInsideOrAbove(int i, int j) {
        return j < ys.length - 1 && isCellInside(i, j);
    }

    private long[][] buildPrefixSum(List<VerticalSegment> segments) {
        int m = xs.length - 1;
        int n = ys.length - 1;
        long[][] prefix = new long[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                long inside = cellCenterInsidePolygon(i, j, segments) ? 1L : 0L;
                prefix[i + 1][j + 1] = inside + prefix[i][j + 1] + prefix[i + 1][j] - prefix[i][j];
            }
        }
        return prefix;
    }

    private boolean cellCenterInsidePolygon(int i, int j, List<VerticalSegment> segments) {
        double cx = midpoint(xs[i], xs[i + 1]);
        double cy = midpoint(ys[j], ys[j + 1]);
        return crossingsRightOf(cx, cy, segments) % 2 == 1;
    }

    private static int crossingsRightOf(double cx, double cy, List<VerticalSegment> segments) {
        return (int) segments.stream().filter(s -> s.crossesRightRayFrom(cx, cy)).count();
    }

    private static double midpoint(long a, long b) {
        return (a + b) / 2.0;
    }

    private static Map<Long, Integer> indexOf(long[] coords) {
        Map<Long, Integer> result = new HashMap<>();
        for (int i = 0; i < coords.length; i++) result.put(coords[i], i);
        return result;
    }

    private static long[] uniqueSorted(long[] values) {
        return Arrays.stream(values).distinct().sorted().toArray();
    }
}