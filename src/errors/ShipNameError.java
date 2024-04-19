package errors;

public class ShipNameError extends ShipError{
    public ShipNameError(String error, String loc) {
        super("NameError", error, loc);
    }
}
