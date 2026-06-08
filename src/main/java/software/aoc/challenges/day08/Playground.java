package software.aoc.challenges.day08;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Playground {

    private final List<CircuitNode> nodes;

    private Playground(List<CircuitNode> nodes) {
        this.nodes = nodes;
    }

    public static Playground empty() {
        return new Playground(List.of());
    }

    public Playground withNodesFrom(String input) {
        return new Playground(
                input.lines()
                        .filter(line -> !line.isBlank())
                        .map(CircuitNode::parse)
                        .toList()
        );
    }

    public long productOfTopCircuitSizes(int connectionsToMake, int topCircuits) {
        if (nodes.size() < 2) return 0;

        List<Connection> connections = allConnectionsSorted();

        UnionFind uf = new UnionFind(nodes.size());
        int actualConnections = Math.min(connectionsToMake, connections.size());
        for (int i = 0; i < actualConnections; i++) {
            Connection c = connections.get(i);
            uf.union(c.firstIndex(), c.secondIndex());
        }

        Map<Integer, Integer> circuitSizes = new HashMap<>();
        for (int i = 0; i < nodes.size(); i++) {
            int root = uf.find(i);
            circuitSizes.merge(root, 1, Integer::sum);
        }

        return circuitSizes.values().stream()
                .sorted(Comparator.reverseOrder())
                .limit(topCircuits)
                .mapToLong(Integer::longValue)
                .reduce(1L, (a, b) -> a * b);
    }

    public long productOfXOfFinalUnifyingConnection() {
        if (nodes.size() < 2) return 0;

        List<Connection> connections = allConnectionsSorted();
        UnionFind uf = new UnionFind(nodes.size());
        int remainingCircuits = nodes.size();

        for (Connection c : connections) {
            if (uf.union(c.firstIndex(), c.secondIndex())) {
                remainingCircuits--;
                if (remainingCircuits == 1) {
                    return nodes.get(c.firstIndex()).x() * nodes.get(c.secondIndex()).x();
                }
            }
        }
        throw new IllegalStateException("No se puede conseguir un único circuito conectando los pares");
    }

    private List<Connection> allConnectionsSorted() {
        List<Connection> result = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                long dist = nodes.get(i).squaredDistanceTo(nodes.get(j));
                result.add(new Connection(dist, i, j));
            }
        }
        Collections.sort(result);
        return result;
    }
}