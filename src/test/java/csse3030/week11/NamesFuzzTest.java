package csse3030.week11;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

class NamesFuzzTest {
    @FuzzTest(maxDuration = "60s")
    void initialsNeverThrowUnexpectedly(FuzzedDataProvider data) {
        String input = data.consumeRemainingAsString();
        // TODO 4.1: call Names.initials with input, and accept only
        //           the exception the contract allows
    }
}
