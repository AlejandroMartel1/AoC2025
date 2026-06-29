package software.aoc.challenges.day12;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

public final class Packer {

    private final List<Shape> shapes;
    private final Set<Long> failedStates = new HashSet<>();

    public Packer(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public boolean canFitAllIn(Region region) {
        long capacity = region.capacity();
        if (totalCellsRequired(region) > capacity) return false;
        if (totalBoundingArea(region) <= capacity) return true;
        return packBacktracking(region);
    }

    private long totalCellsRequired(Region region) {
        return sumOverPieces(region, Shape::size);
    }

    private long totalBoundingArea(Region region) {
        return sumOverPieces(region, Shape::boundingArea);
    }

    private long sumOverPieces(Region region, ToIntFunction<Shape> measure) {
        return IntStream.range(0, region.quantities().size())
                .mapToLong(i -> (long) region.quantities().get(i) * measure.applyAsInt(shapes.get(i)))
                .sum();
    }

    private boolean packBacktracking(Region region) {
        failedStates.clear();
        List<Shape> pieces = piecesOrderedBySize(region);
        Grid grid = new Grid(region.width(), region.height());
        return tryPlace(pieces, 0, grid);
    }

    private List<Shape> piecesOrderedBySize(Region region) {
        List<Shape> pieces = new ArrayList<>();
        for (int shapeIndex = 0; shapeIndex < region.quantities().size(); shapeIndex++) {
            int count = region.quantities().get(shapeIndex);
            for (int copy = 0; copy < count; copy++) pieces.add(shapes.get(shapeIndex));
        }
        pieces.sort(Comparator.comparingInt(Shape::size).reversed());
        return pieces;
    }

    private boolean tryPlace(List<Shape> pieces, int index, Grid grid) {
        if (index == pieces.size()) return true;
        long key = stateKeyFor(index, grid);
        if (failedStates.contains(key)) return false;
        if (anyOrientationFits(pieces, index, grid)) return true;
        failedStates.add(key);
        return false;
    }

    private boolean anyOrientationFits(List<Shape> pieces, int index, Grid grid) {
        return pieces.get(index).uniqueOrientations().stream()
                .anyMatch(orientation -> anyAnchorWorksFor(orientation, pieces, index, grid));
    }

    private boolean anyAnchorWorksFor(Shape orientation, List<Shape> pieces, int index, Grid grid) {
        return grid.validAnchorsFor(orientation)
                .anyMatch(anchor -> tryPlaceAt(orientation, anchor, pieces, index, grid));
    }

    private boolean tryPlaceAt(Shape orientation, Position anchor,
                               List<Shape> pieces, int index, Grid grid) {
        if (!grid.canPlace(orientation, anchor.row(), anchor.col())) return false;
        grid.place(orientation, anchor.row(), anchor.col());
        if (tryPlace(pieces, index + 1, grid)) return true;
        grid.remove(orientation, anchor.row(), anchor.col());
        return false;
    }

    private long stateKeyFor(int index, Grid grid) {
        return ((long) index << 32) | (grid.contentHash() & 0xFFFFFFFFL);
    }
}