package software.aoc.challenges.day10;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JoltageSystem {

    private final List<Integer> buttonMasks;
    private final int[] targetCounters;

    public JoltageSystem(List<Integer> buttonMasks, int[] targetCounters) {
        this.buttonMasks = buttonMasks;
        this.targetCounters = targetCounters;
    }

    public long minimumPresses() {
        int n = buttonMasks.size();
        int m = targetCounters.length;

        Fraction[][] augmented = buildAugmentedMatrix(n, m);
        int[] pivotColumnOfRow = new int[m];
        int rank = reduceToRowEchelonForm(augmented, n, m, pivotColumnOfRow);
        List<Integer> freeVariables = freeVariables(n, rank, pivotColumnOfRow);
        int[] freeVariableCaps = freeVariableCaps(freeVariables);
        long[] best = { Long.MAX_VALUE };
        enumerateFreeVariables(0, new long[freeVariables.size()], freeVariables,
                freeVariableCaps, augmented, rank, best);

        return best[0];
    }

    private Fraction[][] buildAugmentedMatrix(int n, int m) {
        Fraction[][] augmented = new Fraction[m][n + 1];
        for (int counter = 0; counter < m; counter++) {
            for (int button = 0; button < n; button++) {
                boolean affects = ((buttonMasks.get(button) >> counter) & 1) != 0;
                augmented[counter][button] = Fraction.of(affects ? 1 : 0);
            }
            augmented[counter][n] = Fraction.of(targetCounters[counter]);
        }
        return augmented;
    }

    private int reduceToRowEchelonForm(Fraction[][] aug, int n, int m, int[] pivotColumnOfRow) {
        Arrays.fill(pivotColumnOfRow, -1);
        int row = 0;
        for (int col = 0; col < n && row < m; col++) {
            int pivot = -1;
            for (int r = row; r < m; r++) {
                if (aug[r][col].isNonZero()) { pivot = r; break; }
            }
            if (pivot == -1) continue;

            Fraction[] tmp = aug[pivot]; aug[pivot] = aug[row]; aug[row] = tmp;

            Fraction pivotValue = aug[row][col];
            for (int c = 0; c <= n; c++) {
                aug[row][c] = aug[row][c].dividedBy(pivotValue);
            }
            for (int r = 0; r < m; r++) {
                if (r != row && aug[r][col].isNonZero()) {
                    Fraction factor = aug[r][col];
                    for (int c = 0; c <= n; c++) {
                        aug[r][c] = aug[r][c].minus(factor.times(aug[row][c]));
                    }
                }
            }
            pivotColumnOfRow[row] = col;
            row++;
        }
        return row;
    }

    private List<Integer> freeVariables(int n, int rank, int[] pivotColumnOfRow) {
        boolean[] isBasic = new boolean[n];
        for (int r = 0; r < rank; r++) {
            if (pivotColumnOfRow[r] >= 0) isBasic[pivotColumnOfRow[r]] = true;
        }
        List<Integer> free = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!isBasic[i]) free.add(i);
        }
        return free;
    }

    private int[] freeVariableCaps(List<Integer> freeVariables) {
        int[] caps = new int[freeVariables.size()];
        for (int k = 0; k < freeVariables.size(); k++) {
            int button = buttonMasks.get(freeVariables.get(k));
            int cap = Integer.MAX_VALUE;
            for (int counter = 0; counter < targetCounters.length; counter++) {
                if (((button >> counter) & 1) != 0) {
                    cap = Math.min(cap, targetCounters[counter]);
                }
            }
            caps[k] = (cap == Integer.MAX_VALUE) ? 0 : cap;
        }
        return caps;
    }

    private void enumerateFreeVariables(int index, long[] freeValues, List<Integer> freeVariables,
                                        int[] caps, Fraction[][] aug, int rank, long[] best) {
        if (index == freeVariables.size()) {
            evaluateAssignment(freeValues, freeVariables, aug, rank, best);
            return;
        }
        for (long value = 0; value <= caps[index]; value++) {
            freeValues[index] = value;
            enumerateFreeVariables(index + 1, freeValues, freeVariables,
                    caps, aug, rank, best);
        }
    }

    private void evaluateAssignment(long[] freeValues, List<Integer> freeVariables,
                                    Fraction[][] aug, int rank, long[] best) {
        long totalPresses = 0;
        for (long value : freeValues) totalPresses += value;

        for (int r = 0; r < rank; r++) {
            Fraction basicValue = aug[r][aug[r].length - 1];
            for (int t = 0; t < freeVariables.size(); t++) {
                int freeColumn = freeVariables.get(t);
                basicValue = basicValue.minus(aug[r][freeColumn].times(Fraction.of(freeValues[t])));
            }
            if (!basicValue.isNonNegativeInteger()) return;
            totalPresses += basicValue.asLong();
        }

        if (totalPresses < best[0]) best[0] = totalPresses;
    }

    private record Fraction(long numerator, long denominator) {

        static Fraction of(long value) {
            return new Fraction(value, 1);
        }

        Fraction {
            if (denominator < 0) { numerator = -numerator; denominator = -denominator; }
            long divisor = gcd(Math.abs(numerator), denominator);
            if (divisor != 0) { numerator /= divisor; denominator /= divisor; }
            else { denominator = 1; }
        }

        Fraction minus(Fraction other) {
            return new Fraction(numerator * other.denominator - other.numerator * denominator,
                    denominator * other.denominator);
        }

        Fraction times(Fraction other) {
            return new Fraction(numerator * other.numerator, denominator * other.denominator);
        }

        Fraction dividedBy(Fraction other) {
            return new Fraction(numerator * other.denominator, denominator * other.numerator);
        }

        boolean isNonZero() {
            return numerator != 0;
        }

        boolean isNonNegativeInteger() {
            return denominator == 1 && numerator >= 0;
        }

        long asLong() {
            return numerator;
        }

        private static long gcd(long a, long b) {
            while (b != 0) { long t = b; b = a % b; a = t; }
            return a;
        }
    }
}