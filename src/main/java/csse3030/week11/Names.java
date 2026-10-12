package csse3030.week11;

/** Name helpers (Part 4). */
public class Names {
    /**
     * Returns the first letter of each word of {@code fullName}, in upper case
     * ("Ada Lovelace" gives "AL"). Words are separated by one or more spaces;
     * leading and trailing spaces are ignored. An empty or all-space name
     * throws {@link IllegalArgumentException}. No other exception type may escape.
     */
    public static String initials(String fullName) {
        if (fullName.isBlank()) {
            throw new IllegalArgumentException("name is blank");
        }
        StringBuilder out = new StringBuilder();
        for (String part : fullName.split(" ")) {
            out.append(Character.toUpperCase(part.charAt(0)));
        }
        return out.toString();
    }
}
