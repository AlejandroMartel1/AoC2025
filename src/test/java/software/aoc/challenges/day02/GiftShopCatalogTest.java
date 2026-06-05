package software.aoc.challenges.day02;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GiftShopCatalogTest {

    private static final String AdventExample =
            "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
                    "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
                    "824824821-824824827,2121212118-2121212124";

    @Test
    void anEmptyCatalogHasNoInvalidOrPatternedIds() {
        assertEquals(0, GiftShopCatalog.empty().sumOfInvalidIds());
        assertEquals(0, GiftShopCatalog.empty().sumOfPatternedIds());
    }

    @Test
    void aRangeIsParsedAndProducesAllIdsInclusive() {
        IdRange range = IdRange.parse("11-22");
        assertEquals(11L, range.first());
        assertEquals(22L, range.last());
        assertEquals(12L, range.ids().count());
    }

    @Test
    void theCatalogDetectsIdsRepeatedExactlyTwice() {
        assertEquals(11 + 22, GiftShopCatalog.empty().withRangesFrom("11-22").sumOfInvalidIds());
        assertEquals(99, GiftShopCatalog.empty().withRangesFrom("95-115").sumOfInvalidIds());
        assertEquals(1010, GiftShopCatalog.empty().withRangesFrom("998-1012").sumOfInvalidIds());
        assertEquals(0, GiftShopCatalog.empty().withRangesFrom("1698522-1698528").sumOfInvalidIds());
    }

    @Test
    void theCatalogDetectsIdsRepeatedAnyNumberOfTimes() {
        assertEquals(111, GiftShopCatalog.empty().withRangesFrom("100-200").sumOfPatternedIds());
        assertEquals(99 + 111, GiftShopCatalog.empty().withRangesFrom("95-115").sumOfPatternedIds());
        assertEquals(999 + 1010, GiftShopCatalog.empty().withRangesFrom("998-1012").sumOfPatternedIds());
        assertEquals(2121212121L, GiftShopCatalog.empty().withRangesFrom("2121212118-2121212124").sumOfPatternedIds());
    }

    @Test
    void theCatalogSupportsMultipleRangesSeparatedByCommas() {
        long sum = GiftShopCatalog.empty()
                .withRangesFrom("11-22,33-44")
                .sumOfInvalidIds();
        assertEquals(11 + 22 + 33 + 44, sum);
    }

    @Test
    void thePuzzleExampleProducesExpectedSums() {
        GiftShopCatalog catalog = GiftShopCatalog.empty().withRangesFrom(AdventExample);
        assertEquals(1227775554L, catalog.sumOfInvalidIds());
        assertEquals(4174379265L, catalog.sumOfPatternedIds());
    }

    @Test
    void theCatalogIsImmutable() {
        GiftShopCatalog original = GiftShopCatalog.empty();
        GiftShopCatalog loaded = original.withRangesFrom("11-22");
        assertEquals(0, original.sumOfInvalidIds());
        assertEquals(11 + 22, loaded.sumOfInvalidIds());
    }

    @Test
    void realPuzzleInputProducesExpectedAnswers() throws IOException {
        String input = Files.readString(Path.of("src/main/resources/inputs/day02.txt"));
        GiftShopCatalog catalog = GiftShopCatalog.empty().withRangesFrom(input);
        assertEquals(19219508902L, catalog.sumOfInvalidIds());
        assertEquals(27180728081L, catalog.sumOfPatternedIds());
    }
}