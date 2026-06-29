package software.aoc.challenges.day11;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Reactor {

    private final Map<String, List<String>> deviceOutputs;

    private Reactor(Map<String, List<String>> deviceOutputs) {
        this.deviceOutputs = deviceOutputs;
    }

    public static Reactor empty() {
        return new Reactor(Map.of());
    }

    public Reactor withDevicesFrom(String input) {
        Map<String, List<String>> connections = new HashMap<>();
        input.lines()
                .filter(line -> !line.isBlank())
                .map(Device::parse)
                .forEach(device -> connections.put(device.name(), device.outputs()));
        return new Reactor(Map.copyOf(connections));
    }

    public long countPaths(String source, String target) {
        if (!deviceOutputs.containsKey(source) && !source.equals(target)) {
            return 0;
        }
        return countPathsMemoized(source, target, new HashMap<>());
    }

    public long countPathsThrough(String source, String target, List<String> mandatoryWaypoints) {
        if (mandatoryWaypoints.isEmpty()) {
            return countPaths(source, target);
        }
        long total = 0L;
        for (List<String> permutation : allPermutationsOf(mandatoryWaypoints)) {
            total += productOfSegmentPaths(source, target, permutation);
        }
        return total;
    }

    private long productOfSegmentPaths(String source, String target, List<String> waypointsInOrder) {
        long product = 1L;
        String previous = source;
        for (String waypoint : waypointsInOrder) {
            product *= countPaths(previous, waypoint);
            if (product == 0L) return 0L;
            previous = waypoint;
        }
        return product * countPaths(previous, target);
    }

    private long countPathsMemoized(String current, String target, Map<String, Long> memo) {
        if (current.equals(target)) return 1L;
        Long cached = memo.get(current);
        if (cached != null) return cached;

        long total = 0L;
        for (String next : deviceOutputs.getOrDefault(current, Collections.emptyList())) {
            total += countPathsMemoized(next, target, memo);
        }
        memo.put(current, total);
        return total;
    }

    private static List<List<String>> allPermutationsOf(List<String> items) {
        List<List<String>> result = new ArrayList<>();
        permuteInPlace(new ArrayList<>(items), 0, result);
        return result;
    }

    private static void permuteInPlace(List<String> arr, int startIndex, List<List<String>> result) {
        if (startIndex == arr.size()) {
            result.add(new ArrayList<>(arr));
            return;
        }
        for (int i = startIndex; i < arr.size(); i++) {
            Collections.swap(arr, startIndex, i);
            permuteInPlace(arr, startIndex + 1, result);
            Collections.swap(arr, startIndex, i);
        }
    }
}