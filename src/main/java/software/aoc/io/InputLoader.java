package software.aoc.io;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class InputLoader {

    private static final String INPUTS_DIR = "src/main/resources/inputs";

    public String load(int day) {
        Path path = Path.of(INPUTS_DIR, String.format("day%02d.txt", day));
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo " + path, e);
        }
    }
}