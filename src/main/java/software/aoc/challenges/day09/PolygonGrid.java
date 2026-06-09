package software.aoc.challenges.day09;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PolygonGrid {

    private final long[] xs;
    private final long[] ys;
    private final Map<Long, Integer> xIndex;
    private final Map<Long, Integer> yIndex;
    private final long[][] insidePrefixSum;

    public PolygonGrid(List<RedTile> polygonVertices) {
        this.xs = uniqueSorted(polygonVertices.stream().mapToLong(RedTile::x).toArray());
        this.ys = uniqueSorted(polygonVertices.stream().mapToLong(RedTile::y).toArray());
        this.xIndex = buildIndex(xs);
        this.yIndex = buildIndex(ys);
        this.insidePrefixSum = buildPrefixSum(polygonVertices);
    }

    public boolean containsRectangleBetween(RedTile r1, RedTile r2) {
        long xMin = Math.min(r1.x(), r2.x());
        long xMax = Math.max(r1.x(), r2.x());
        long yMin = Math.min(r1.y(), r2.y());
        long yMax = Math.max(r1.y(), r2.y());
        int iMin = xIndex.get(xMin);
        int iMax = xIndex.get(xMax);
        int jMin = yIndex.get(yMin);
        int jMax = yIndex.get(yMax);

        if (iMin < iMax && jMin < jMax) {
            return allCellsInside(iMin, iMax, jMin, jMax);
        }
        if (iMin == iMax && jMin < jMax) {
            return verticalLineInside(iMin, jMin, jMax);
        }
        if (jMin == jMax && iMin < iMax) {
            return horizontalLineInside(jMin, iMin, iMax);
        }
        return false;
    }

    private static Map<Long, Integer> buildIndex(long[] coords) {
        Map<Long, Integer> result = new HashMap<>();
        for (int i = 0; i < coords.length; i++) {
            result.put(coords[i], i);
        }
        return result;
    }

    private boolean allCellsInside(int iMin, int iMax, int jMin, int jMax) {
        long expected = (long) (iMax - iMin) * (jMax - jMin);
        long actual = insidePrefixSum[iMax][jMax]
                - insidePrefixSum[iMin][jMax]
                - insidePrefixSum[iMax][jMin]
                + insidePrefixSum[iMin][jMin];
        return actual == expected;
    }

    private boolean verticalLineInside(int i, int jMin, int jMax) {
        int m = xs.length - 1;
        for (int j = jMin; j < jMax; j++) {
            boolean left = (i - 1 >= 0) && cellInside(i - 1, j);
            boolean right = (i < m) && cellInside(i, j);
            if (!left && !right) return false;
        }
        return true;
    }

    private boolean horizontalLineInside(int j, int iMin, int iMax) {
        int n = ys.length - 1;
        for (int i = iMin; i < iMax; i++) {
            boolean below = (j - 1 >= 0) && cellInside(i, j - 1);
            boolean above = (j < n) && cellInside(i, j);
            if (!below && !above) return false;
        }
        return true;
    }

    private boolean cellInside(int i, int j) {
        long sum = insidePrefixSum[i + 1][j + 1]
                - insidePrefixSum[i][j + 1]
                - insidePrefixSum[i + 1][j]
                + insidePrefixSum[i][j];
        return sum == 1L;
    }

    private long[][] buildPrefixSum(List<RedTile> vertices) {
        long[][] verticalSegments = extractVerticalSegments(vertices);
        int m = xs.length - 1;
        int n = ys.length - 1;
        long[][] prefix = new long[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            double cx = (xs[i] + xs[i + 1]) / 2.0;
            for (int j = 0; j < n; j++) {
                double cy = (ys[j] + ys[j + 1]) / 2.0;
                int crossings = 0;
                for (long[] seg : verticalSegments) {
                    long sx = seg[0], sy1 = seg[1], sy2 = seg[2];
                    if (sx > cx && sy1 < cy && cy < sy2) crossings++;
                }
                long inside = (crossings % 2 == 1) ? 1L : 0L;
                prefix[i + 1][j + 1] = inside + prefix[i][j + 1] + prefix[i + 1][j] - prefix[i][j];
            }
        }
        return prefix;
    }

    private static long[][] extractVerticalSegments(List<RedTile> vertices) {
        int count = vertices.size();
        long[][] result = new long[count][];
        int written = 0;
        for (int i = 0; i < count; i++) {
            RedTile a = vertices.get(i);
            RedTile b = vertices.get((i + 1) % count);
            if (a.x() == b.x()) {
                long y1 = Math.min(a.y(), b.y());
                long y2 = Math.max(a.y(), b.y());
                result[written++] = new long[] {a.x(), y1, y2};
            }
        }
        return Arrays.copyOf(result, written);
    }

    private static long[] uniqueSorted(long[] values) {
        long[] copy = values.clone();
        Arrays.sort(copy);
        int written = 0;
        for (int i = 0; i < copy.length; i++) {
            if (i == 0 || copy[i] != copy[i - 1]) {
                copy[written++] = copy[i];
            }
        }
        return Arrays.copyOf(copy, written);
    }
}