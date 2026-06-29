package software.aoc.challenges.day04;
import java.util.List;
import java.util.stream.Stream;

public final class TrashCompactor {

    private static final char ROLL = '@';
    private static final char EMPTY = '.';
    private static final int ACCESSIBILITY_THRESHOLD = 4;

    private final PaperGrid grid;

    private TrashCompactor(PaperGrid grid) {
        this.grid = grid;
    }

    public static TrashCompactor empty() {
        return new TrashCompactor(new PaperGrid(List.of()));
    }

    public TrashCompactor withLayoutFrom(String input) {
        return new TrashCompactor(new PaperGrid(
                input.lines().filter(line -> !line.isBlank()).toList()
        ));
    }

    public long countAccessibleRolls() {
        return accessibleRollPositions().count();
    }

    public long countTotalRemovableRolls() {
        long total = 0;
        TrashCompactor current = this;
        while (current.hasAccessibleRolls()) {
            total += current.countAccessibleRolls();
            current = current.afterRemovingAccessibleRolls();
        }
        return total;
    }

    private boolean hasAccessibleRolls() {
        return accessibleRollPositions().findAny().isPresent();
    }

    private TrashCompactor afterRemovingAccessibleRolls() {
        return new TrashCompactor(grid.withAllAt(accessibleRollPositions().toList(), EMPTY));
    }

    private Stream<Position> accessibleRollPositions() {
        return grid.allPositions().filter(this::isAccessibleRollAt);
    }

    private boolean isAccessibleRollAt(Position p) {
        return isRollAt(p) && countRollNeighborsOf(p) < ACCESSIBILITY_THRESHOLD;
    }

    private boolean isRollAt(Position p) {
        return grid.isInside(p) && grid.at(p) == ROLL;
    }

    private long countRollNeighborsOf(Position p) {
        return p.neighbors().filter(this::isRollAt).count();
    }
}