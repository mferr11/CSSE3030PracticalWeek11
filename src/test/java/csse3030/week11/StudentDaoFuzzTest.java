package csse3030.week11;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import java.sql.SQLException;

class StudentDaoFuzzTest {
    private static final StudentDao dao = new StudentDao(TestDb.connect());

    @FuzzTest(maxDuration = "60s")
    void lookupByIdIsSafe(FuzzedDataProvider data) {
        String id = data.consumeRemainingAsString();
        // TODO 5.3: call dao.findNamesById with id, and accept only
        //           the exception the contract allows
    }
}
