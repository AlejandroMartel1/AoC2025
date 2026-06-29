package software.aoc.challenges.day12;

import software.aoc.challenges.Solver;

public final class PartA implements Solver {

    @Override
    public long solve(String input) {
        return ChristmasTreeFarm.empty()
                .loadedFrom(input)
                .countRegionsThatFit();
    }
}