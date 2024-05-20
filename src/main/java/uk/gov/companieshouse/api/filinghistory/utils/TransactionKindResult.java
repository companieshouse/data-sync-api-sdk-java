package uk.gov.companieshouse.api.filinghistory.utils;

import org.apache.commons.lang3.StringUtils;

public class TransactionKindResult {

    private String encodedId;
    private TransactionKind kind;

    public TransactionKindResult(String encodedId, TransactionKind kind) {
        this.encodedId = encodedId;
        this.kind = kind;
    }

    public String getEncodedId() {
        return encodedId;
    }

    public void setEncodedId(String encodedId) {
        this.encodedId = encodedId;
    }

    public TransactionKind getKind() {
        return kind;
    }

    public void setKind(TransactionKind kind) {
        this.kind = kind;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TransactionKindResult)) {
            return false;
        }
        TransactionKindResult cmp = (TransactionKindResult) obj;

        final boolean sameKind = (this.kind == null && cmp.kind == null) ||
                (this.kind != null && this.kind.equals(cmp.kind));

        final boolean sameEncodedId = StringUtils.equals(this.encodedId, cmp.encodedId);

        return sameKind && sameEncodedId;
    }
}