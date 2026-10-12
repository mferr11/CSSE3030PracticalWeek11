package csse3030.week11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;

class StudentDaoTest {
    private final StudentDao dao = new StudentDao(TestDb.connect());

    @Test
    void findsAliceById() throws SQLException {
        // TODO 5.1: assert that findNamesById("s1001") returns exactly ["Alice"]
    }

    @Test
    void injectionIdMatchesNoStudent() throws SQLException {
        // TODO 5.2: assert that findNamesById("x' OR '1'='1") returns an empty list
    }
}
