package aka.alias;

public class AliasAlreadyExists extends RuntimeException {
    public AliasAlreadyExists(String message) {
        super(message);
    }
}
