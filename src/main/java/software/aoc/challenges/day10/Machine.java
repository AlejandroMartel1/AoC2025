package software.aoc.challenges.day10;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Machine(int targetMask, int[] targetCounters, List<Integer> buttonMasks) {

    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\(([0-9,]+)\\)");
    private static final Pattern JOLTAGE_PATTERN = Pattern.compile("\\{([0-9,]+)}");

    public static Machine parse(String line) {
        int lightsStart = line.indexOf('[') + 1;
        int lightsEnd = line.indexOf(']');
        int target = bitmaskFromLights(line.substring(lightsStart, lightsEnd));

        List<Integer> buttons = new ArrayList<>();
        Matcher buttonMatcher = BUTTON_PATTERN.matcher(line);
        while (buttonMatcher.find()) {
            buttons.add(bitmaskFromIndices(buttonMatcher.group(1)));
        }

        int[] joltages = new int[0];
        Matcher joltageMatcher = JOLTAGE_PATTERN.matcher(line);
        if (joltageMatcher.find()) {
            joltages = parseCounters(joltageMatcher.group(1));
        }

        return new Machine(target, joltages, buttons);
    }

    public int fewestButtonPresses() {
        int n = buttonMasks.size();
        int bestPresses = Integer.MAX_VALUE;
        for (int subset = 0; subset < (1 << n); subset++) {
            int xor = 0;
            for (int i = 0; i < n; i++) {
                if ((subset & (1 << i)) != 0) {
                    xor ^= buttonMasks.get(i);
                }
            }
            if (xor == targetMask) {
                int presses = Integer.bitCount(subset);
                if (presses < bestPresses) bestPresses = presses;
            }
        }
        return bestPresses;
    }

    public long fewestPressesForJoltage() {
        return new JoltageSystem(buttonMasks, targetCounters).minimumPresses();
    }

    private static int bitmaskFromLights(String diagram) {
        int mask = 0;
        for (int i = 0; i < diagram.length(); i++) {
            if (diagram.charAt(i) == '#') mask |= (1 << i);
        }
        return mask;
    }

    private static int bitmaskFromIndices(String csv) {
        int mask = 0;
        for (String token : csv.split(",")) {
            mask |= (1 << Integer.parseInt(token.trim()));
        }
        return mask;
    }

    private static int[] parseCounters(String csv) {
        String[] tokens = csv.split(",");
        int[] result = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            result[i] = Integer.parseInt(tokens[i].trim());
        }
        return result;
    }
}