package csse3030.week11;

/**
 * Decodes URL-encoded ("percent-encoded") strings.
 *
 * <p>Contract (Part 3):
 * <ul>
 *   <li>{@code +} decodes to a space.</li>
 *   <li>{@code %XY}, where X and Y are hexadecimal digits (either case), decodes to the
 *       single character with that code.</li>
 *   <li>Every other character is copied unchanged.</li>
 *   <li>A {@code %} that is not followed by two characters (a truncated escape) is
 *       rejected with {@link IllegalArgumentException}.</li>
 *   <li>A {@code %} followed by two characters that are not both hexadecimal digits is
 *       rejected with {@link IllegalArgumentException}.</li>
 *   <li>{@code decode} never throws any other exception type, for any input string.</li>
 * </ul>
 */
public class UrlCodec {
    static int hex(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        if (c >= 'a' && c <= 'f') return c - 'a' + 10;
        if (c >= 'A' && c <= 'F') return c - 'A' + 10;
        return -1;
    }

    public static String decode(String s) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '+') {
                out.append(' ');
            } else if (c == '%') {
                int hi = hex(s.charAt(i + 1));
                int lo = hex(s.charAt(i + 2));
                if (hi < 0 || lo < 0) {
                    throw new IllegalArgumentException("bad hex");
                }
                out.append((char) (hi * 16 + lo));
                i += 2;
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
}
