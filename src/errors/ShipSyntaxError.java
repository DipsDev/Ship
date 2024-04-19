package errors;

public class ShipSyntaxError extends ShipError {

    public ShipSyntaxError(String error, String loc) {
        super("SyntaxError", error, loc);
    }
}
