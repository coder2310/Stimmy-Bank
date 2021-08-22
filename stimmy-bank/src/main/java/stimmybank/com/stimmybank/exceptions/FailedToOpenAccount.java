package stimmybank.com.stimmybank.exceptions;

public class FailedToOpenAccount extends Exception{
    public FailedToOpenAccount(String errorMessage){
        super(errorMessage);
    }
}
