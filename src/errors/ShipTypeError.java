package errors;

public class ShipTypeError extends ShipError{
    public ShipTypeError( String error, String loc) {
        super("TypeError", error, loc);
    }
}
