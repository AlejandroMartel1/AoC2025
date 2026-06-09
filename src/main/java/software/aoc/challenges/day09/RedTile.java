package software.aoc.challenges.day09;

public record RedTile(long x, long y) {

    public static RedTile parse(String text) {
        String[] parts = text.split(",");
        return new RedTile(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

    public long rectangleAreaWith(RedTile other) {
        long width = Math.abs(x - other.x) + 1;
        long height = Math.abs(y - other.y) + 1;
        return width * height;
    }
}