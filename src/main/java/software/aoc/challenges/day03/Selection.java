package software.aoc.challenges.day03;
import java.util.ArrayDeque;
import java.util.Deque;

public final class Selection {

    private final Deque<Character> chosen;
    private final int batteriesOn;
    private int remaining;
    private int discardsAllowed;

    private Selection(int totalDigits, int batteriesOn) {
        this.chosen = new ArrayDeque<>(batteriesOn);
        this.batteriesOn = batteriesOn;
        this.remaining = totalDigits;
        this.discardsAllowed = totalDigits - batteriesOn;
    }

    public static Selection startingFor(int totalDigits, int batteriesOn) {
        return new Selection(totalDigits, batteriesOn);
    }

    public void consider(char digit) {
        while (canDiscardTopFor(digit)) discardTop();
        addOrSkip(digit);
        remaining--;
    }

    public long asLong() {
        return Long.parseLong(chosenAsString());
    }

    private void addOrSkip(char digit) {
        if (chosen.size() < batteriesOn) chosen.addLast(digit);
        else discardsAllowed--;
    }

    private void discardTop() {
        chosen.pollLast();
        discardsAllowed--;
    }

    private boolean canDiscardTopFor(char digit) {
        return !chosen.isEmpty()
                && discardsAllowed > 0
                && chosen.peekLast() < digit;
    }

    private String chosenAsString() {
        return chosen.stream().map(String::valueOf).reduce("", String::concat);
    }
}