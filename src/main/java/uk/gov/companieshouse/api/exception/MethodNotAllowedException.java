package uk.gov.companieshouse.api.exception;

public class MethodNotAllowedException extends RuntimeException {
    public MethodNotAllowedException(String message) {
        super(message);
    }
}
