package errors;

public class ShipError extends RuntimeException {
    private String errorPrefix;
    private String error;
    private String loc;

    public ShipError(String errorPrefix, String error, String loc) {
        this.error = error;
        this.errorPrefix = errorPrefix;
        this.loc = loc;
    }

    @Override
    public void printStackTrace() {
        System.out.printf("%s: %s%n", errorPrefix, error);
        System.out.println("\tat " + loc + ";");
    }
}
