package software.aoc.challenges.day03;

import java.util.ArrayDeque;
import java.util.Deque;

public record BatteryBank(String digits) {

    public static BatteryBank parse(String line) {
        return new BatteryBank(line.trim());
    }

    public long maxJoltageWith(int batteriesOn) {
        Deque<Character> chosen = new ArrayDeque<>(batteriesOn);
        int remaining = digits.length();
        int discardsAllowed = digits.length() - batteriesOn;

        for (int i = 0; i < digits.length(); i++) {
            char digit = digits.charAt(i);
            while (!chosen.isEmpty()
                    && chosen.peekLast() < digit
                    && discardsAllowed > 0
                    && chosen.size() + remaining > batteriesOn) {
                chosen.pollLast();
                discardsAllowed--;
            }
            if (chosen.size() < batteriesOn) {
                chosen.addLast(digit);
            } else {
                discardsAllowed--;
            }
            remaining--;
        }

        return Long.parseLong(chosen.stream()
                .map(String::valueOf)
                .reduce("", String::concat));
    }
}