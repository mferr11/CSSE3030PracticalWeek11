package csse3030.week11;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

class QuantityFuzzTest {
    @FuzzTest(maxDuration = "30s")
    void parseQuantityOnlyRejects(FuzzedDataProvider data) throws ParseException {
        String input = data.consumeRemainingAsString();
        // TODO 3.4: call QuantityParser.parseQuantity with input
    }
}
