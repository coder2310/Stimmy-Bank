package stimmybank.com.stimmybank.exceptions;

public class UserTryingToOpenSavingsAccountWhenTheyAlreadyHaveOne
        extends Exception{
    public UserTryingToOpenSavingsAccountWhenTheyAlreadyHaveOne(String errorMessage){
        super(errorMessage);
    }
}

