package uk.gov.companieshouse.api.filinghistory.utils;

public class TransactionKindCriteria {

    private String entityId;
    private String parentEntityId;
    private String formType;
    private String parentFormType;
    private String barcode;

    public TransactionKindCriteria(String entityId, String parentEntityId, String formType, String parentFormType, String barcode) {
        this.entityId = entityId;
        this.parentEntityId = parentEntityId;
        this.formType = formType;
        this.parentFormType = parentFormType;
        this.barcode = barcode;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getParentEntityId() {
        return parentEntityId;
    }

    public void setParentEntityId(String parentEntityId) {
        this.parentEntityId = parentEntityId;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getParentFormType() {
        return parentFormType;
    }

    public void setParentFormType(String parentFormType) {
        this.parentFormType = parentFormType;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
