package software.aoc.challenges.day10;

public record IndicatorLight(int value, int count) {

    public static IndicatorLight parse(String diagram) {
        return new IndicatorLight(maskOf(bitsIn(diagram)), diagram.length() - 2);
    }

    private static String bitsIn(String diagram) {
        return diagram.substring(1, diagram.length() - 1);
    }

    private static int maskOf(String bits) {
        int mask = 0;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '#') mask |= (1 << i);
        }
        return mask;
    }
}