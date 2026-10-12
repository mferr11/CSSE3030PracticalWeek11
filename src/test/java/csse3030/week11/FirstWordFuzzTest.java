package csse3030.week11;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

class FirstWordFuzzTest {
    @FuzzTest(maxDuration = "30s")
    void firstWordNeverCrashes(FuzzedDataProvider data) {
        String input = data.consumeRemainingAsString();
        // TODO 3.1: call Words.firstWord with input
    }
}
