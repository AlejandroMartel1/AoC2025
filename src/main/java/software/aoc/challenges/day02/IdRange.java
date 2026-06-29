package software.aoc.challenges.day02;
import java.util.stream.LongStream;

public record IdRange(long first, long last) {

    public static IdRange parse(String text) {
        String[] parts = text.split("-");
        return new IdRange(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

    public LongStream ids() {
        return LongStream.rangeClosed(first, last);
    }
}