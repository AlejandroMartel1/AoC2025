package software.aoc.application;

public final class Main {

    private static final int Day = 9;
    private static final char Part = 'b';

    public static void main(String[] args) {
        try {
            Arguments arguments = Arguments.parse(args);
            new ChallengeOrchestrator().run(arguments.day(), arguments.part());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Uso: Main <día> <parte>");
            System.err.println("Ejemplo: Main 1 a");
            System.exit(1);
        }
    }

    private record Arguments(int day, char part) {
        static Arguments parse(String[] args) {
            int day = args.length > 0 ? parseDay(args[0]) : Day;
            char part = args.length > 1 ? parsePart(args[1]) : Part;
            return new Arguments(day, part);
        }

        private static int parseDay(String value) {
            try {
                int day = Integer.parseInt(value);
                if (day < 1 || day > 12) {
                    throw new IllegalArgumentException("Valor fuera del rango válido: 1-24 " + day);
                }
                return day;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El valor introducido tiene que ser numérico: " + value);
            }
        }

        private static char parsePart(String value) {
            if (value.length() != 1 || (value.charAt(0) != 'a' && value.charAt(0) != 'b')) {
                throw new IllegalArgumentException("El argumento tiene que ser 'a' o 'b': " + value);
            }
            return value.charAt(0);
        }
    }
}