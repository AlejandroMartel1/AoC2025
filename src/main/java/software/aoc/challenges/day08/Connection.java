package software.aoc.challenges.day08;

public record Connection(long squaredDistance, int firstIndex, int secondIndex)
        implements Comparable<Connection> {

    @Override
    public int compareTo(Connection other) {
        return Long.compare(squaredDistance, other.squaredDistance);
    }
}