package software.aoc.challenges.day01;

public record Rotation(int step) {

    public static Rotation parse(String instruction) {
        return new Rotation(directionOf(instruction) * magnitudeOf(instruction));
    }

    private static int directionOf(String instruction) {
        return instruction.charAt(0) == 'R' ? 1 : -1;
    }

    private static int magnitudeOf(String instruction) {
        return Integer.parseInt(instruction.substring(1).trim());
    }
}