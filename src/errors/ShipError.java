package errors;

public class ShipError extends RuntimeException {
    private String errorPrefix;
    private String error;
    private String loc;
    private String blame;

    public ShipError(String errorPrefix, String error, String blame, String loc) {
        this.error = error;
        this.errorPrefix = errorPrefix;
        this.loc = loc;
        this.blame = blame;
    }

    @Override
    public void printStackTrace() {
        System.out.printf("%s: %s%n", errorPrefix, error);
        System.out.println("\tat '" + blame + "' " + loc);
    }
}
