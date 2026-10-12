package csse3030.week11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;

class StudentDaoTest {
    private final StudentDao dao = new StudentDao(TestDb.connect());

    @Test
    void injectionIdMatchesNoStudent() throws SQLException {
        // TODO 5.1: assert that findNamesById("x' OR '1'='1") returns an empty list
        fail("TODO 5.1: not written yet");
    }
}
