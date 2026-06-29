package software.aoc.challenges.day08;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Playground {

    private final List<CircuitNode> nodes;

    private Playground(List<CircuitNode> nodes) {
        this.nodes = nodes;
    }

    public static Playground empty() {
        return new Playground(List.of());
    }

    public Playground withNodesFrom(String input) {
        return new Playground(input.lines().filter(l -> !l.isBlank()).map(CircuitNode::parse).toList());
    }

    public long productOfTopCircuitSizes(int limit, int topCircuits) {
        if (nodes.size() < 2) return 0;
        UnionFind unionFind = unifyShortestConnections(limit);
        return productOfTop(circuitSizesIn(unionFind), topCircuits);
    }

    public long productOfXOfFinalUnifyingConnection() {
        if (nodes.size() < 2) return 0;
        UnionFind unionFind = new UnionFind(nodes.size());
        int remaining = nodes.size();
        for (Connection c : allConnectionsSorted()) {
            if (unionFind.union(c.firstIndex(), c.secondIndex()) && --remaining == 1) return productOfXFor(c);
        }
        return 0;
    }

    private UnionFind unifyShortestConnections(int limit) {
        UnionFind unionFind = new UnionFind(nodes.size());
        allConnectionsSorted().stream().limit(limit)
                .forEach(c -> unionFind.union(c.firstIndex(), c.secondIndex()));
        return unionFind;
    }

    private long productOfXFor(Connection c) {
        return nodes.get(c.firstIndex()).x() * nodes.get(c.secondIndex()).x();
    }

    private Collection<Long> circuitSizesIn(UnionFind unionFind) {
        return IntStream.range(0, nodes.size()).boxed()
                .collect(Collectors.groupingBy(unionFind::find, Collectors.counting()))
                .values();
    }

    private static long productOfTop(Collection<Long> sizes, int topCircuits) {
        return sizes.stream()
                .sorted(Comparator.reverseOrder())
                .limit(topCircuits)
                .reduce(1L, (a, b) -> a * b);
    }

    private List<Connection> allConnectionsSorted() {
        return allConnections().sorted().toList();
    }

    private Stream<Connection> allConnections() {
        return IntStream.range(0, nodes.size()).boxed()
                .flatMap(i -> IntStream.range(i + 1, nodes.size())
                        .mapToObj(j -> connectionBetween(i, j)));
    }

    private Connection connectionBetween(int i, int j) {
        return new Connection(nodes.get(i).squaredDistanceTo(nodes.get(j)), i, j);
    }
}