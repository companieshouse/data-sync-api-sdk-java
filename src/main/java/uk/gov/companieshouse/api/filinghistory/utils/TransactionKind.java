package uk.gov.companieshouse.api.filinghistory.utils;

public enum TransactionKind {
    TOP_LEVEL("top-level"),

    ANNOTATION("annotation"),

    RESOLUTION("resolution"),

    ASSOCIATED_FILING("associated-filing");

    private final String value;

    TransactionKind(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
