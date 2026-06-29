package software.aoc.challenges.day10;

import java.util.Arrays;

public record Button(int wiring) {

    public static Button parse(String schematic) {
        return new Button(maskOf(indicesIn(schematic)));
    }

    private static String indicesIn(String schematic) {
        return schematic.substring(1, schematic.length() - 1);
    }

    private static int maskOf(String csv) {
        return Arrays.stream(csv.split(","))
                .mapToInt(token -> 1 << Integer.parseInt(token.trim()))
                .reduce(0, (a, b) -> a | b);
    }
}