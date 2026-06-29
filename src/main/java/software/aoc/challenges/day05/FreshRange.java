package software.aoc.challenges.day05;

public record FreshRange(long first, long last) {

    public static FreshRange parse(String text) {
        String[] parts = text.split("-");
        return new FreshRange(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

    public boolean contains(long id) {
        return id >= first && id <= last;
    }

    public long size() {
        return last - first + 1;
    }

    public boolean overlapsOrAdjacentTo(FreshRange other) {
        return first <= other.last + 1 && other.first <= last + 1;
    }

    public FreshRange mergedWith(FreshRange other) {
        return new FreshRange(Math.min(first, other.first), Math.max(last, other.last));
    }
}