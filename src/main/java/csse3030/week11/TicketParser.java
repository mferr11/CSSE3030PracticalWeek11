package csse3030.week11;

/**
 * Parses ticket strings of the form {@code TKT1:<holder>:<quantity>}.
 *
 * <p>Contract (Part 2):
 * <ul>
 *   <li>The input starts with the literal prefix {@code TKT1:}.</li>
 *   <li>After the prefix there are exactly two {@code :}-separated fields: the holder
 *       (any text without a colon) and the quantity.</li>
 *   <li>The quantity is a decimal integer between 1 and 10 inclusive.</li>
 *   <li>On success, {@code parse} returns a {@link Ticket} whose {@code seatIds} are
 *       {@code 1..quantity}.</li>
 *   <li>Any input that does not meet these rules is rejected by throwing
 *       {@link ParseException}. No other exception type may escape.</li>
 * </ul>
 */
public class TicketParser {
    public record Ticket(String holder, int quantity, int[] seatIds) {}

    public static Ticket parse(String raw) throws ParseException {
        if (!raw.startsWith("TKT1:")) {
            throw new ParseException("bad magic");
        }
        String[] parts = raw.substring(5).split(":");
        if (parts.length != 2) {
            throw new ParseException("bad field count");
        }
        String holder = parts[0];
        int qty;
        try {
            qty = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new ParseException("bad quantity");
        }
        if (qty > 10) {
            throw new ParseException("too many");
        }
        int[] seats = new int[qty];
        for (int i = 0; i < qty; i++) {
            seats[i] = i + 1;
        }
        return new Ticket(holder, qty, seats);
    }
}
