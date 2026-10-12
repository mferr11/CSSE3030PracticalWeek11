package csse3030.week11;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

class TicketParserFuzzTest {
    @FuzzTest(maxDuration = "60s")
    void parseOnlyThrowsParseException(FuzzedDataProvider data) {
        String raw = data.consumeRemainingAsString();
        // TODO 4.3: call TicketParser.parse with raw, and accept only
        //           the exception the contract allows
    }
}
