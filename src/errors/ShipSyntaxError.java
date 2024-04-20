package errors;

public class ShipSyntaxError extends ShipError {

    public ShipSyntaxError(String error, String blame, String loc) {
        super("SyntaxError", error, blame, loc);
    }
}
