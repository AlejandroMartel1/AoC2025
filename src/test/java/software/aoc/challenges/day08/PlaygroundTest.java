package software.aoc.challenges.day08;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PlaygroundTest {

    private static final String AdventExample = """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689
            """;

    @Test
    void anEmptyPlaygroundHasNoCircuits() {
        assertEquals(0, Playground.empty().productOfTopCircuitSizes(1000, 3));
        assertEquals(0, Playground.empty().productOfXOfFinalUnifyingConnection());
    }

    @Test
    void aCircuitNodeComputesSquaredDistanceIn3D() {
        CircuitNode a = new CircuitNode(0, 0, 0);
        CircuitNode b = new CircuitNode(3, 4, 12);
        // 3^2 + 4^2 + 12^2 = 9 + 16 + 144 = 169
        assertEquals(169L, a.squaredDistanceTo(b));
        assertEquals(169L, b.squaredDistanceTo(a));
    }

    @Test
    void aCircuitNodeIsParsedFromCommaSeparatedCoordinates() {
        CircuitNode node = CircuitNode.parse("162,817,812");
        assertEquals(162L, node.x());
        assertEquals(817L, node.y());
        assertEquals(812L, node.z());
    }

    @Test
    void aConnectionOrdersItselfByDistance() {
        Connection shorter = new Connection(100L, 0, 1);
        Connection longer = new Connection(200L, 2, 3);
        assertEquals(-1, Integer.signum(shorter.compareTo(longer)));
        assertEquals(1, Integer.signum(longer.compareTo(shorter)));
        assertEquals(0, shorter.compareTo(new Connection(100L, 5, 6)));
    }

    @Test
    void aUnionFindStartsWithEachElementInItsOwnSet() {
        UnionFind uf = new UnionFind(5);
        assertEquals(0, uf.find(0));
        assertEquals(1, uf.find(1));
        assertEquals(2, uf.find(2));
    }

    @Test
    void aUnionFindMergesDistinctSetsAndReportsAsTrue() {
        UnionFind uf = new UnionFind(5);
        assertTrue(uf.union(0, 1));
        assertEquals(uf.find(0), uf.find(1));
        assertFalse(uf.union(0, 1));
    }

    @Test
    void thePuzzleExampleAfterTenConnectionsProducesExpectedProduct() {
        long product = Playground.empty()
                .withNodesFrom(AdventExample)
                .productOfTopCircuitSizes(10, 3);
        assertEquals(40L, product);
    }

    @Test
    void thePuzzleExampleFindsTheFinalUnifyingConnection() {
        long product = Playground.empty()
                .withNodesFrom(AdventExample)
                .productOfXOfFinalUnifyingConnection();
        assertEquals(25272L, product);
    }

    @Test
    void thePlaygroundIsImmutable() {
        Playground original = Playground.empty();
        Playground loaded = original.withNodesFrom(AdventExample);

        assertEquals(0, original.productOfTopCircuitSizes(10, 3));
        assertEquals(40, loaded.productOfTopCircuitSizes(10, 3));
    }

    @Test
    void realPuzzleInputProducesExpectedAnswers() throws IOException {
        String input = Files.readString(Path.of("src/main/resources/inputs/day08.txt"));
        Playground playground = Playground.empty().withNodesFrom(input);

        assertEquals(50568L, playground.productOfTopCircuitSizes(1000, 3));
        assertEquals(36045012L, playground.productOfXOfFinalUnifyingConnection());
    }
}