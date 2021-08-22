package stimmybank.com.stimmybank.exceptions;

public class UserTryingToOpenCheckingAccountWhenTheyAlreadyHaveOne extends Exception{
    public UserTryingToOpenCheckingAccountWhenTheyAlreadyHaveOne(String errorMessage){
        super(errorMessage);
    }
}

