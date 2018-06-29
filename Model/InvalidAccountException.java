package Model;

public class InvalidAccountException extends Exception {

    public InvalidAccountException() {

    }

    public InvalidAccountException(String message) {
        super(message);
    }

    public InvalidAccountException(String message, Throwable cause) {
        super(message, cause);
    }

}
