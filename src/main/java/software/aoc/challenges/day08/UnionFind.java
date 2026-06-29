package software.aoc.challenges.day08;

public final class UnionFind {
    private final int[] parent;

    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    public int find(int x) {
        return parent[x] == x ? x : (parent[x] = find(parent[x]));
    }

    public boolean union(int x, int y) {
        int rX = find(x), rY = find(y);
        if (rX == rY) return false;
        parent[rX] = rY;
        return true;
    }
}