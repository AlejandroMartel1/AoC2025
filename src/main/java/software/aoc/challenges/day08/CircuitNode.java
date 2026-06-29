package software.aoc.challenges.day08;

import java.util.Arrays;

public record CircuitNode(long x, long y, long z) {

    public static CircuitNode parse(String text) {
        long[] coords = coordsIn(text);
        return new CircuitNode(coords[0], coords[1], coords[2]);
    }

    public long squaredDistanceTo(CircuitNode other) {
        return square(x - other.x) + square(y - other.y) + square(z - other.z);
    }

    private static long[] coordsIn(String text) {
        return Arrays.stream(text.split(",")).mapToLong(Long::parseLong).toArray();
    }

    private static long square(long n) {
        return n * n;
    }
}