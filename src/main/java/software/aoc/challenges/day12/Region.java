package software.aoc.challenges.day12;

import java.util.Arrays;
import java.util.List;

public record Region(int width, int height, List<Integer> quantities) {

    public static Region parse(String line) {
        String[] parts = line.split(":\\s*");
        int[] dims = parseDimensions(parts[0]);
        return new Region(dims[0], dims[1], parseQuantities(parts[1]));
    }

    public long capacity() {
        return (long) width * height;
    }

    private static int[] parseDimensions(String spec) {
        String[] parts = spec.split("x");
        return new int[] { Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) };
    }

    private static List<Integer> parseQuantities(String csv) {
        return Arrays.stream(csv.trim().split("\\s+"))
                .map(Integer::parseInt)
                .toList();
    }
}