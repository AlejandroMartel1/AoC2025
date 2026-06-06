package software.aoc.challenges.day05;

public record FreshRange(long first, long last) {

    public static FreshRange parse(String text) {
        String[] parts = text.split("-");
        return new FreshRange(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

    public boolean contains(long id) {
        return id >= first && id <= last;
    }
}