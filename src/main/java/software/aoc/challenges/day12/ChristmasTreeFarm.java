package software.aoc.challenges.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ChristmasTreeFarm {

    private final List<Shape> shapes;
    private final List<Region> regions;

    private ChristmasTreeFarm(List<Shape> shapes, List<Region> regions) {
        this.shapes = shapes;
        this.regions = regions;
    }

    public static ChristmasTreeFarm empty() {
        return new ChristmasTreeFarm(List.of(), List.of());
    }

    public ChristmasTreeFarm loadedFrom(String input) {
        String[] sections = input.trim().split("\\R\\R");
        List<Shape> parsedShapes = parseShapes(sections);
        List<Region> parsedRegions = parseRegions(sections[sections.length - 1]);
        return new ChristmasTreeFarm(parsedShapes, parsedRegions);
    }

    public long countRegionsThatFit() {
        Packer packer = new Packer(shapes);
        return regions.stream().filter(packer::canFitAllIn).count();
    }

    private static List<Shape> parseShapes(String[] sections) {
        List<Shape> shapes = new ArrayList<>();
        for (int i = 0; i < sections.length - 1; i++) {
            shapes.add(parseShapeFrom(sections[i]));
        }
        return shapes;
    }

    private static Shape parseShapeFrom(String section) {
        List<String> lines = Arrays.stream(section.split("\\R")).toList();
        return Shape.parse(lines.subList(1, lines.size()));
    }

    private static List<Region> parseRegions(String section) {
        return section.lines().filter(line -> !line.isBlank()).map(Region::parse).toList();
    }
}
