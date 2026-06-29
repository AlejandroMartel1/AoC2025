package software.aoc.challenges.day10;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FactoryTest {

    private static final String AdventExample = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
            """;

    @Test
    void anEmptyFactoryHasNoButtonPresses() {
        assertEquals(0, Factory.empty().fewestTotalButtonPresses());
        assertEquals(0, Factory.empty().fewestTotalPressesForJoltage());
    }

    @Test
    void aMachineParsesTheLightsDiagramAsABitmask() {
        Machine machine = Machine.parse("[.##.] (3) (1,3) {1,2,3,4}");
        assertEquals(0b0110, machine.light().value());
    }

    @Test
    void aMachineParsesEachButtonAsABitmaskOfAffectedIndices() {
        Machine machine = Machine.parse("[....] (1,3) (0,2) {0,0,0,0}");
        assertEquals(0b1010, machine.buttons().get(0).wiring());
        assertEquals(0b0101, machine.buttons().get(1).wiring());
    }

    @Test
    void aMachineParsesTheJoltageRequirementsAsCounterTargets() {
        Machine machine = Machine.parse("[.##.] (3) (1,3) {3,5,4,7}");
        assertEquals(List.of(3, 5, 4, 7), machine.joltage());
    }

    @Test
    void theFirstExampleMachineNeedsTwoPressesForTheLights() {
        Machine machine = Machine.parse("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}");
        assertEquals(2, machine.fewestButtonPresses());
    }

    @Test
    void theSecondExampleMachineNeedsThreePressesForTheLights() {
        Machine machine = Machine.parse("[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}");
        assertEquals(3, machine.fewestButtonPresses());
    }

    @Test
    void theThirdExampleMachineNeedsTwoPressesForTheLights() {
        Machine machine = Machine.parse("[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}");
        assertEquals(2, machine.fewestButtonPresses());
    }

    @Test
    void theFirstExampleMachineNeedsTenPressesForTheJoltage() {
        Machine machine = Machine.parse("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}");
        assertEquals(10L, machine.fewestPressesForJoltage());
    }

    @Test
    void theSecondExampleMachineNeedsTwelvePressesForTheJoltage() {
        Machine machine = Machine.parse("[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}");
        assertEquals(12L, machine.fewestPressesForJoltage());
    }

    @Test
    void theThirdExampleMachineNeedsElevenPressesForTheJoltage() {
        Machine machine = Machine.parse("[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}");
        assertEquals(11L, machine.fewestPressesForJoltage());
    }

    @Test
    void thePuzzleExampleSumsToSevenForTheLights() {
        long total = Factory.empty()
                .withMachinesFrom(AdventExample)
                .fewestTotalButtonPresses();
        assertEquals(7L, total);
    }

    @Test
    void thePuzzleExampleSumsToThirtyThreeForTheJoltage() {
        long total = Factory.empty()
                .withMachinesFrom(AdventExample)
                .fewestTotalPressesForJoltage();
        assertEquals(33L, total);
    }

    @Test
    void aJoltageSystemWithOneButtonPerCounterIsCompletelyDetermined() {
        Machine machine = Machine.parse("[...] (0) (1) (2) {5,3,7}");
        assertEquals(15L, machine.fewestPressesForJoltage());
    }

    @Test
    void theFactoryIsImmutable() {
        Factory original = Factory.empty();
        Factory loaded = original.withMachinesFrom(AdventExample);

        assertEquals(0, original.fewestTotalButtonPresses());
        assertEquals(7, loaded.fewestTotalButtonPresses());
    }

    @Test
    void realPuzzleInputProducesExpectedAnswers() throws IOException {
        String input = Files.readString(Path.of("src/main/resources/inputs/day10.txt"));
        Factory factory = Factory.empty().withMachinesFrom(input);

        assertEquals(545L, factory.fewestTotalButtonPresses());
        assertEquals(22430L, factory.fewestTotalPressesForJoltage());
    }
}