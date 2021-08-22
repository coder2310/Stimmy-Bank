package stimmybank.com.stimmybank.exceptions;

public class WrongAmountForSavingsException extends Exception{
    public WrongAmountForSavingsException(String errorMessage){
        super(errorMessage);
    }
}
