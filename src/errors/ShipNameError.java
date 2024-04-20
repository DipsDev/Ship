package errors;

public class ShipNameError extends ShipError{
    public ShipNameError(String error, String blame, String loc) {
        super("NameError", error, blame, loc);
    }
}
