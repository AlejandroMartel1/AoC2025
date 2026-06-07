package software.aoc.challenges.day06;
import java.util.function.LongBinaryOperator;

public enum Operation {

    ADD('+', Long::sum),
    MULTIPLY('*', (a, b) -> a * b);

    private final char symbol;
    private final LongBinaryOperator combine;

    Operation(char symbol, LongBinaryOperator combine) {
        this.symbol = symbol;
        this.combine = combine;
    }

    public static Operation fromSymbol(char symbol) {
        for (Operation op : values()) {
            if (op.symbol == symbol) return op;
        }
        throw new IllegalArgumentException("Operador desconocido: " + symbol);
    }

    public long apply(long a, long b) {
        return combine.applyAsLong(a, b);
    }

    public long identity() {
        return this == ADD ? 0L : 1L;
    }
}