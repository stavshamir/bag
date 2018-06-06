package bag.alias;

public class AliasAlreadyExists extends RuntimeException {
    public AliasAlreadyExists(String message) {
        super("Error: " + message);
    }
}
