package uk.gov.companieshouse.api.filinghistory.utils;

import static org.apache.commons.lang3.StringUtils.trim;

import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class TransactionKindService {

    private static final String ANNOTATION = "ANNOTATION";
    private static final String RES_15 = "RES15";
    private final String transactionIdSalt;

    public TransactionKindService(String transactionIdSalt) {
        this.transactionIdSalt = transactionIdSalt;
    }

    public TransactionKindResult encodeIdByTransactionKind(TransactionKindCriteria kindCriteria) {
        final String encodedId;
        final TransactionKind kindEnum;
        if (ANNOTATION.equals(kindCriteria.getFormType())) {
            if (StringUtils.isNotBlank(kindCriteria.getParentEntityId())) {
                encodedId = encodeTransactionId(kindCriteria.getParentEntityId());
            } else {
                encodedId = encodeTransactionId(kindCriteria.getEntityId());
            }
            kindEnum = TransactionKind.ANNOTATION;

        } else if (FormTypeService.isResolutionType(kindCriteria.getFormType())) {
            if (StringUtils.isNotBlank(kindCriteria.getBarcode()) && !RES_15.equals(kindCriteria.getFormType())) {
                encodedId = encodeTransactionId(kindCriteria.getBarcode());
            } else if (StringUtils.isNotBlank(kindCriteria.getParentEntityId())) {
                encodedId = encodeTransactionId(kindCriteria.getParentEntityId());
            } else {
                encodedId = encodeTransactionId(kindCriteria.getEntityId());
            }
            kindEnum = TransactionKind.RESOLUTION;

        } else if (!FormTypeService.isAssociatedFilingBlockListed(kindCriteria)
                && StringUtils.isNotBlank(kindCriteria.getParentEntityId())) {
            encodedId = encodeTransactionId(kindCriteria.getParentEntityId());
            kindEnum = TransactionKind.ASSOCIATED_FILING;

        } else {
            encodedId = encodeTransactionId(kindCriteria.getEntityId());
            kindEnum = TransactionKind.TOP_LEVEL;
        }

        return new TransactionKindResult(encodedId, kindEnum);
    }

    public String encodeTransactionId(String id) {
        return StringUtils.isBlank(id) ? id
                : Base64.encodeBase64URLSafeString((trim(id) + transactionIdSalt).getBytes(StandardCharsets.UTF_8));
    }
}
