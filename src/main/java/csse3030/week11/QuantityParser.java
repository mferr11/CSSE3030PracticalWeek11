package csse3030.week11;

/** Reads ticket quantities (Part 3). */
public class QuantityParser {
    /**
     * Reads a ticket quantity: a decimal integer from 1 to 10, which is returned.
     * Any other input throws {@link ParseException}.
     * No other exception type may ever escape.
     */
    public static int parseQuantity(String s) throws ParseException {
        int q;
        try {
            q = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new ParseException("not a number");
        }
        if (q < 1 || q > 10) {
            throw new ParseException("out of range");
        }
        return q;
    }
}
