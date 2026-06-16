package software.aoc.challenges.day10;
import java.util.List;

public final class Factory {

    private final List<Machine> machines;

    private Factory(List<Machine> machines) {
        this.machines = machines;
    }

    public static Factory empty() {
        return new Factory(List.of());
    }

    public Factory withMachinesFrom(String input) {
        return new Factory(
                input.lines()
                        .filter(line -> !line.isBlank())
                        .map(Machine::parse)
                        .toList()
        );
    }

    public long fewestTotalButtonPresses() {
        return machines.stream()
                .mapToLong(Machine::fewestButtonPresses)
                .sum();
    }

    public long fewestTotalPressesForJoltage() {
        return machines.stream()
                .mapToLong(Machine::fewestPressesForJoltage)
                .sum();
    }
}