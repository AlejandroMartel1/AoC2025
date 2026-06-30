package software.aoc.challenges.day07;

import java.util.List;

public final class TachyonManifold {

    private final List<DiagramRow> rows;

    private TachyonManifold(List<DiagramRow> rows) {
        this.rows = rows;
    }

    public static TachyonManifold empty() {
        return new TachyonManifold(List.of());
    }

    public TachyonManifold withDiagramFrom(String input) {
        return new TachyonManifold(
                input.lines().filter(line -> !line.isBlank()).map(DiagramRow::new).toList()
        );
    }

    public long countSplits() {
        if (rows.isEmpty()) return 0;
        return finalBeamState().splits();
    }

    public long countTimelines() {
        if (rows.isEmpty()) return 0;
        return finalTimelines().total();
    }

    private BeamState finalBeamState() {
        BeamState state = BeamState.startingAt(findSourceColumn());
        for (DiagramRow row : rows) state = state.propagatedThrough(row);
        return state;
    }

    private Timelines finalTimelines() {
        Timelines timelines = Timelines.startingAt(findSourceColumn());
        for (DiagramRow row : rows) timelines = timelines.propagatedThrough(row);
        return timelines;
    }

    private int findSourceColumn() {
        return rows.stream()
                .flatMapToInt(row -> row.sourceColumn().stream())
                .findFirst()
                .orElseThrow();}
}