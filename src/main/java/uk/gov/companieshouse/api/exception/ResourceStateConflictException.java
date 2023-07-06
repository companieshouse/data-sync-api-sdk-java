package uk.gov.companieshouse.api.exception;

public class ResourceStateConflictException extends RuntimeException {
    public ResourceStateConflictException(String message) {
        super(message);
    }
}
