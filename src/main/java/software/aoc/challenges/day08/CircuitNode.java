package software.aoc.challenges.day08;

public record CircuitNode(long x, long y, long z) {

    public static CircuitNode parse(String text) {
        String[] parts = text.split(",");
        return new CircuitNode(
                Long.parseLong(parts[0]),
                Long.parseLong(parts[1]),
                Long.parseLong(parts[2])
        );
    }

    public long squaredDistanceTo(CircuitNode other) {
        long dx = x - other.x;
        long dy = y - other.y;
        long dz = z - other.z;
        return dx * dx + dy * dy + dz * dz;
    }
}