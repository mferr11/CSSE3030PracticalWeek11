package csse3030.week11;

/** Thrown by {@link TicketParser#parse(String)} when the input is not a valid ticket. */
public class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }
}
