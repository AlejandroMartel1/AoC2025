package software.aoc.challenges.day10;
import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public record Machine(IndicatorLight light, List<Button> buttons, List<Integer> joltage) {

    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\([0-9,]+\\)");
    private static final Pattern JOLTAGE_PATTERN = Pattern.compile("\\{([0-9,]+)}");

    public static Machine parse(String line) {
        return new Machine(lightIn(line), buttonsIn(line), joltageIn(line));
    }

    public int fewestButtonPresses() {
        return allButtonConfigurations()
                .filter(this::matchesLightPattern)
                .map(Integer::bitCount)
                .min().orElseThrow();
    }

    public long fewestPressesForJoltage() {
        return new JoltageSystem(wirings(), joltage).minimumPresses();
    }

    private IntStream allButtonConfigurations() {
        return IntStream.range(0, 1 << buttons.size());
    }

    private boolean matchesLightPattern(int pressedMask) {
        return xorOfPressedIn(pressedMask) == light.value();
    }

    private int xorOfPressedIn(int pressedMask) {
        return IntStream.range(0, buttons.size())
                .filter(i -> isPressed(pressedMask, i))
                .map(i -> buttons.get(i).wiring())
                .reduce(0, (a, b) -> a ^ b);
    }

    private boolean isPressed(int pressedMask, int index) {
        return (pressedMask & (1 << index)) != 0;
    }

    private List<Integer> wirings() {
        return buttons.stream().map(Button::wiring).toList();
    }

    private static IndicatorLight lightIn(String line) {
        return IndicatorLight.parse(line.substring(line.indexOf('['), line.indexOf(']') + 1));
    }

    private static List<Button> buttonsIn(String line) {
        return BUTTON_PATTERN.matcher(line).results()
                .map(MatchResult::group)
                .map(Button::parse)
                .toList();
    }

    private static List<Integer> joltageIn(String line) {
        Matcher matcher = JOLTAGE_PATTERN.matcher(line);
        if (!matcher.find()) return List.of();
        return Arrays.stream(matcher.group(1).split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }
}