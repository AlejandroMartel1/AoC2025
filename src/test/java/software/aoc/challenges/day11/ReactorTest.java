package software.aoc.challenges.day11;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReactorTest {

    private static final String PartAExample = """
            aaa: you hhh
            you: bbb ccc
            bbb: ddd eee
            ccc: ddd eee fff
            ddd: ggg
            eee: out
            fff: out
            ggg: out
            hhh: ccc fff iii
            iii: out
            """;

    private static final String PartBExample = """
            svr: aaa bbb
            aaa: fft
            fft: ccc
            bbb: tty
            tty: ccc
            ccc: ddd eee
            ddd: hub
            hub: fff
            eee: dac
            dac: fff
            fff: ggg hhh
            ggg: out
            hhh: out
            """;

    @Test
    void anEmptyReactorHasNoPaths() {
        assertEquals(0, Reactor.empty().countPaths("you", "out"));
        assertEquals(0, Reactor.empty().countPathsThrough("svr", "out", List.of("dac", "fft")));
    }

    @Test
    void aDeviceParsesItsNameAndOutputsFromAColonSeparatedLine() {
        Device device = Device.parse("aaa: you hhh");
        assertEquals("aaa", device.name());
        assertEquals(2, device.outputs().size());
        assertEquals("you", device.outputs().get(0));
        assertEquals("hhh", device.outputs().get(1));
    }

    @Test
    void aDeviceWithASingleOutputIsParsedAsAListOfOne() {
        Device device = Device.parse("eee: out");
        assertEquals("eee", device.name());
        assertEquals(List.of("out"), device.outputs());
    }

    @Test
    void aReactorCountsOnePathFromATargetToItself() {
        Reactor reactor = Reactor.empty().withDevicesFrom(PartAExample);
        assertEquals(1L, reactor.countPaths("out", "out"));
    }

    @Test
    void aReactorCountsPathsByMultiplicativeRecursionOverChildren() {
        Reactor reactor = Reactor.empty().withDevicesFrom(PartAExample);
        assertEquals(2L, reactor.countPaths("bbb", "out"));
    }

    @Test
    void aReactorCountsPathsFromANodeWithMultipleChildren() {
        Reactor reactor = Reactor.empty().withDevicesFrom(PartAExample);
        assertEquals(3L, reactor.countPaths("ccc", "out"));
    }

    @Test
    void thePartAExampleHasFivePathsFromYouToOut() {
        long paths = new PartA().solve(PartAExample);
        assertEquals(5L, paths);
    }

    @Test
    void countingPathsThroughAnEmptyListOfWaypointsEqualsCountingAllPaths() {
        Reactor reactor = Reactor.empty().withDevicesFrom(PartBExample);
        assertEquals(reactor.countPaths("svr", "out"),
                reactor.countPathsThrough("svr", "out", List.of()));
    }

    @Test
    void countingPathsThroughASingleWaypointFactorsAsSourceTimesWaypointTimesTarget() {
        Reactor reactor = Reactor.empty().withDevicesFrom(PartBExample);
        assertEquals(4L, reactor.countPathsThrough("svr", "out", List.of("fft")));
    }

    @Test
    void countingPathsThroughTwoWaypointsConsidersBothOrdersAndCancelsTheInfeasibleOne() {
        Reactor reactor = Reactor.empty().withDevicesFrom(PartBExample);
        assertEquals(2L, reactor.countPathsThrough("svr", "out", List.of("dac", "fft")));
    }

    @Test
    void thePartBExampleHasTwoPathsFromSvrToOutThroughDacAndFft() {
        long paths = new PartB().solve(PartBExample);
        assertEquals(2L, paths);
    }

    @Test
    void theReactorIsImmutable() {
        Reactor original = Reactor.empty();
        Reactor loaded = original.withDevicesFrom(PartAExample);

        assertEquals(0L, original.countPaths("you", "out"));
        assertEquals(5L, loaded.countPaths("you", "out"));
    }

    @Test
    void realPuzzleInputProducesExpectedAnswers() throws IOException {
        String input = Files.readString(Path.of("src/main/resources/inputs/day11.txt"));
        Reactor reactor = Reactor.empty().withDevicesFrom(input);

        assertEquals(613L, reactor.countPaths("you", "out"));
        assertEquals(372918445876116L, reactor.countPathsThrough("svr", "out", List.of("dac", "fft")));
    }
}