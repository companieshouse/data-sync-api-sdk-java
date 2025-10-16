package uk.gov.companieshouse.api.exception;

public class InternalServiceException extends RuntimeException {

    public InternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServiceException(String message) {
        super(message);
    }

}
