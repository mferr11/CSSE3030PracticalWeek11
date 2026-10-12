package csse3030.week11;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Looks up students in the {@code students(id, name, grade)} table.
 *
 * <p>Contract (Part 4):
 * <ul>
 *   <li>{@code findNamesById(id)} returns the names of the students whose {@code id}
 *       column is exactly equal to the given {@code id}, so at most one name for a
 *       unique id and an empty list if there is no match.</li>
 *   <li>The {@code id} is untrusted user input. It is only ever a value to compare
 *       against and must never change the structure of the query that runs.</li>
 *   <li>A database error is reported by throwing {@link SQLException}.</li>
 * </ul>
 */
public class StudentDao {
    private final Connection conn;

    public StudentDao(Connection conn) {
        this.conn = conn;
    }

    public List<String> findNamesById(String id) throws SQLException {
        String sql = "SELECT name FROM students WHERE id = '" + id + "'";
        List<String> names = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        }
        return names;
    }
}
