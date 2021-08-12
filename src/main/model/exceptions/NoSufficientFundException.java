package model.exceptions;

public class NoSufficientFundException extends Exception {

    public NoSufficientFundException() {
    }

    public NoSufficientFundException(String msg) {
        super(msg);
    }

}
