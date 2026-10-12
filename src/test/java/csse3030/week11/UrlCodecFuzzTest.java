package csse3030.week11;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

class UrlCodecFuzzTest {
    @FuzzTest(maxDuration = "60s")
    void decodeNeverThrowsUnexpectedly(FuzzedDataProvider data) {
        String input = data.consumeRemainingAsString();
        // TODO 4.1: call UrlCodec.decode with input, and accept only
        //           the exception the contract allows
    }
}
