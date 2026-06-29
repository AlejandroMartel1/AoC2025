package software.aoc.challenges.day11;
import java.util.Arrays;
import java.util.List;

public record Device(String name, List<String> outputs) {

    public static Device parse(String line) {
        String[] parts = line.split(": ", 2);
        String deviceName = parts[0].trim();
        List<String> deviceOutputs = Arrays.stream(parts[1].trim().split("\\s+"))
                .toList();
        return new Device(deviceName, deviceOutputs);
    }
}