package danekerscode.technicaltask.exception;

public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException() {
        super("invalid credentials");
    }

}
