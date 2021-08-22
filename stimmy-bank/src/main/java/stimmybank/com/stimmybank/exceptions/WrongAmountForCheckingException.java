package stimmybank.com.stimmybank.exceptions;



public class WrongAmountForCheckingException extends Exception {
    public WrongAmountForCheckingException(String errorMessage){
        super(errorMessage);
    }
}
