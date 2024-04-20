package errors;

public class ShipTypeError extends ShipError{
    public ShipTypeError( String error, String blame, String loc) {
        super("TypeError", error, blame, loc);
    }
}
