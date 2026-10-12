package csse3030.week11;

/** Small string helpers (Part 3). */
public class Words {
    /**
     * Returns the first word of {@code s}: the text before the first space.
     * If {@code s} contains no space, the whole of {@code s} is returned.
     * Never throws for any non-null string.
     */
    public static String firstWord(String s) {
        return s.substring(0, s.indexOf(' '));
    }
}
