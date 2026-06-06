package software.aoc.challenges.day03;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PowerSupplyTest {

    private static final String AdventExample = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
            """;

    @Test
    void anEmptyPowerSupplyHasNoJoltage() {
        assertEquals(0, PowerSupply.empty().totalJoltageWith(2));
        assertEquals(0, PowerSupply.empty().totalJoltageWith(12));
    }

    @Test
    void aBatteryBankComputesMaxJoltageWithTwoBatteries() {
        assertEquals(98, new BatteryBank("987654321111111").maxJoltageWith(2));
        assertEquals(89, new BatteryBank("811111111111119").maxJoltageWith(2));
        assertEquals(78, new BatteryBank("234234234234278").maxJoltageWith(2));
        assertEquals(92, new BatteryBank("818181911112111").maxJoltageWith(2));
    }

    @Test
    void aBatteryBankComputesMaxJoltageWithTwelveBatteries() {
        assertEquals(987654321111L, new BatteryBank("987654321111111").maxJoltageWith(12));
        assertEquals(811111111119L, new BatteryBank("811111111111119").maxJoltageWith(12));
        assertEquals(434234234278L, new BatteryBank("234234234234278").maxJoltageWith(12));
        assertEquals(888911112111L, new BatteryBank("818181911112111").maxJoltageWith(12));
    }

    @Test
    void aBankWithIncreasingDigitsKeepsTheLargestRightmost() {
        assertEquals(89, new BatteryBank("123456789").maxJoltageWith(2));
        assertEquals(99, new BatteryBank("12345678999").maxJoltageWith(2));
    }

    @Test
    void aBankWithDecreasingDigitsKeepsTheLargestLeftmost() {
        assertEquals(98, new BatteryBank("987654321").maxJoltageWith(2));
        assertEquals(987654321L, new BatteryBank("987654321").maxJoltageWith(9));
    }

    @Test
    void thePowerSupplyAggregatesJoltagesFromAllBanks() {
        long total = PowerSupply.empty()
                .withBanksFrom("12345\n98765")
                .totalJoltageWith(2);
        assertEquals(45 + 98, total);
    }

    @Test
    void thePuzzleExampleProducesExpectedJoltages() {
        PowerSupply supply = PowerSupply.empty().withBanksFrom(AdventExample);
        assertEquals(357L, supply.totalJoltageWith(2));
        assertEquals(3121910778619L, supply.totalJoltageWith(12));
    }

    @Test
    void thePowerSupplyIsImmutable() {
        PowerSupply original = PowerSupply.empty();
        PowerSupply loaded = original.withBanksFrom("987654321111111");

        assertEquals(0, original.totalJoltageWith(2));
        assertEquals(98, loaded.totalJoltageWith(2));
    }

    @Test
    void realPuzzleInputProducesExpectedAnswers() throws IOException {
        String input = Files.readString(Path.of("src/main/resources/inputs/day03.txt"));
        PowerSupply supply = PowerSupply.empty().withBanksFrom(input);

        assertEquals(17034L, supply.totalJoltageWith(2));
        assertEquals(168798209663590L, supply.totalJoltageWith(12));
    }
}