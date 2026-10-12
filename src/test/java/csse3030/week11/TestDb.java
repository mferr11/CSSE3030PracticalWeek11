package csse3030.week11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** Provides an in-memory H2 database containing {@code students(id, name, grade)}. Provided; do not edit. */
public final class TestDb {
    private TestDb() {}

    /**
     * Opens a connection to the shared in-memory database, creating and seeding it on
     * first use. Never throws a checked exception, so it is safe in a static initialiser.
     */
    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:students;DB_CLOSE_DELAY=-1");
            try (Statement st = conn.createStatement()) {
                st.execute("CREATE TABLE IF NOT EXISTS students ("
                        + "id VARCHAR(16) PRIMARY KEY, name VARCHAR(64) NOT NULL, grade INT NOT NULL)");
                st.execute("MERGE INTO students KEY (id) VALUES ('s1001', 'Alice', 6)");
                st.execute("MERGE INTO students KEY (id) VALUES ('s1002', 'Bob', 5)");
                st.execute("MERGE INTO students KEY (id) VALUES ('s1003', 'Chen', 7)");
            }
            return conn;
        } catch (SQLException e) {
            throw new IllegalStateException("could not set up test database", e);
        }
    }
}
