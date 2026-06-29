package software.aoc.challenges.day03;

import java.util.List;

public final class PowerSupply {

    private final List<BatteryBank> banks;

    private PowerSupply(List<BatteryBank> banks) {
        this.banks = banks;
    }

    public static PowerSupply empty() {
        return new PowerSupply(List.of());
    }

    public PowerSupply withBanksFrom(String input) {
        return new PowerSupply(
                input.lines().filter(line -> !line.isBlank()).map(BatteryBank::parse).toList()
        );
    }

    public long totalJoltageWith(int batteriesOn) {
        return banks.stream().mapToLong(bank -> bank.maxJoltageWith(batteriesOn)).sum();
    }
}