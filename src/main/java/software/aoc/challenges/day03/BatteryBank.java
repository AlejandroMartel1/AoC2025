package software.aoc.challenges.day03;

public record BatteryBank(String digits) {

    public static BatteryBank parse(String line) {
        return new BatteryBank(line.trim());
    }

    public long maxJoltageWith(int batteriesOn) {
        Selection selection = Selection.startingFor(digits.length(), batteriesOn);
        for (char digit : digits.toCharArray()) selection.consider(digit);
        return selection.asLong();
    }
}