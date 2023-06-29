package uk.gov.companieshouse.api.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String errorMsg, Throwable cause){
        super(errorMsg, cause);
    }
}
