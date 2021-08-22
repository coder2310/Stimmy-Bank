package stimmybank.com.stimmybank.exceptions;

public class RestTemplateException extends Exception{
    public RestTemplateException(String errorMessage){
        super(errorMessage);
    }
}
