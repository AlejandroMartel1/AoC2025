package software.aoc.challenges.day01;
import java.util.List;

public final class Dial {

    private static final int START = 50;
    private static final int SIZE = 100;

    private final List<Rotation> rotations;

    private Dial(List<Rotation> rotations) {
        this.rotations = rotations;
    }

    public static Dial empty() {
        return new Dial(List.of());
    }

    public Dial follow(String instructions) {
        return new Dial(
                instructions.lines()
                        .map(Rotation::parse)
                        .toList()
        );
    }

    public int countTimesEndingAtZero() {
        int zeros = 0;
        int position = START;
        for (Rotation rotation : rotations) {
            position = Math.floorMod(position + rotation.step(), SIZE);
            if (position == 0) zeros++;
        }
        return zeros;
    }

    public int countTimesPassingZero() {
        int zeros = 0;
        int absolute = START;
        for (Rotation rotation : rotations) {
            int next = absolute + rotation.step();
            zeros += zerosBetween(absolute, next);
            absolute = next;
        }
        return zeros;
    }

    private static int zerosBetween(int from, int to) {
        return from < to
                ? Math.floorDiv(to, SIZE) - Math.floorDiv(from, SIZE)
                : Math.floorDiv(from - 1, SIZE) - Math.floorDiv(to - 1, SIZE);
    }
}