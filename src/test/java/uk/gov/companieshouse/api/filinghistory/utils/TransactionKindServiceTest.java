package uk.gov.companieshouse.api.filinghistory.utils;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionKindServiceTest {

    private static final String ENTITY_ID = "entityId";
    private static final String PARENT_ENTITY_ID = "parentEntityId";
    private static final String BARCODE = "barcode";
    private static final String SALT = "salt";
    private static final String ENCODED_ENTITY_ID =
            Base64.encodeBase64URLSafeString((trim(ENTITY_ID) + SALT).getBytes(StandardCharsets.UTF_8));
    private static final String ENCODED_PARENT_ENTITY_ID =
            Base64.encodeBase64URLSafeString((trim(PARENT_ENTITY_ID) + SALT).getBytes(StandardCharsets.UTF_8));
    private static final String ENCODED_BARCODE =
            Base64.encodeBase64URLSafeString((trim(BARCODE) + SALT).getBytes(StandardCharsets.UTF_8));

    @InjectMocks
    private TransactionKindService kindService;

    @BeforeEach
    void setUp() {
        kindService = new TransactionKindService(SALT);
    }

    @Test
    void shouldEncodeEntityId() {
        TransactionKindCriteria criteria = new TransactionKindCriteria(
                ENTITY_ID,
                "",
                "TM01",
                "",
                "");
        TransactionKindResult expected = new TransactionKindResult(
                ENCODED_ENTITY_ID,
                TransactionKind.TOP_LEVEL);

        TransactionKindResult actual = kindService.encodeIdByTransactionKind(criteria);

        assertEquals(expected, actual);
    }

    @Test
    void shouldEncodeParentEntityIdWhenChildAnnotation() {
        TransactionKindCriteria criteria = new TransactionKindCriteria(
                ENTITY_ID,
                PARENT_ENTITY_ID,
                "ANNOTATION",
                "ANY",
                "");
        TransactionKindResult expected = new TransactionKindResult(
                ENCODED_PARENT_ENTITY_ID,
                TransactionKind.ANNOTATION);

        TransactionKindResult actual = kindService.encodeIdByTransactionKind(criteria);

        assertEquals(expected, actual);
    }

    @Test
    void shouldEncodeEntityIdWhenTopLevelAnnotation() {
        TransactionKindCriteria criteria = new TransactionKindCriteria(
                ENTITY_ID,
                "",
                "ANNOTATION",
                "",
                "");
        TransactionKindResult expected = new TransactionKindResult(
                ENCODED_ENTITY_ID,
                TransactionKind.ANNOTATION);

        TransactionKindResult actual = kindService.encodeIdByTransactionKind(criteria);

        assertEquals(expected, actual);
    }

    @Test
    void shouldEncodeBarcodeWhenResolution() {
        TransactionKindCriteria criteria = new TransactionKindCriteria(
                ENTITY_ID,
                "",
                "RES01",
                "",
                BARCODE);
        TransactionKindResult expected = new TransactionKindResult(
                ENCODED_BARCODE,
                TransactionKind.RESOLUTION);

        TransactionKindResult actual = kindService.encodeIdByTransactionKind(criteria);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "''", "null"
    }, nullValues = {"null"})
    void shouldEncodeEntityIdWhenResolutionButBarcodeBlank(String barcode) {
        TransactionKindCriteria criteria = new TransactionKindCriteria(
                ENTITY_ID,
                "",
                "RES01",
                "",
                barcode);
        TransactionKindResult expected = new TransactionKindResult(
                ENCODED_ENTITY_ID,
                TransactionKind.RESOLUTION);

        TransactionKindResult actual = kindService.encodeIdByTransactionKind(criteria);

        assertEquals(expected, actual);
    }

    @Test
    void shouldEncodeEntityIdWhenRES15() {
        TransactionKindCriteria criteria = new TransactionKindCriteria(
                ENTITY_ID,
                "",
                "RES15",
                "",
                BARCODE);
        TransactionKindResult expected = new TransactionKindResult(
                ENCODED_ENTITY_ID,
                TransactionKind.RESOLUTION);

        TransactionKindResult actual = kindService.encodeIdByTransactionKind(criteria);

        assertEquals(expected, actual);
    }

    @Test
    void shouldEncodeParentEntityIdWhenRES15Child() {
        TransactionKindCriteria criteria = new TransactionKindCriteria(
                ENTITY_ID,
                PARENT_ENTITY_ID,
                "RES15",
                "",
                BARCODE);
        TransactionKindResult expected = new TransactionKindResult(
                ENCODED_PARENT_ENTITY_ID,
                TransactionKind.RESOLUTION);

        TransactionKindResult actual = kindService.encodeIdByTransactionKind(criteria);

        assertEquals(expected, actual);
    }

    @Test
    void shouldEncodeParentEntityIdWhenAssociatedFiling() {
        TransactionKindCriteria criteria = new TransactionKindCriteria(
                ENTITY_ID,
                PARENT_ENTITY_ID,
                "any",
                "any",
                BARCODE);
        TransactionKindResult expected = new TransactionKindResult(
                ENCODED_PARENT_ENTITY_ID,
                TransactionKind.ASSOCIATED_FILING);

        TransactionKindResult actual = kindService.encodeIdByTransactionKind(criteria);

        assertEquals(expected, actual);
    }

    @Test
    void shouldEncodeEntityIdWhenAssociatedFilingBlockListed() {
        TransactionKindCriteria criteria = new TransactionKindCriteria(
                ENTITY_ID,
                PARENT_ENTITY_ID,
                "600",
                "any",
                "");
        TransactionKindResult expected = new TransactionKindResult(
                ENCODED_ENTITY_ID,
                TransactionKind.TOP_LEVEL);

        TransactionKindResult actual = kindService.encodeIdByTransactionKind(criteria);

        assertEquals(expected, actual);
    }

    @Test
    void shouldEncodeEntityIdWhenAssociatedFilingNotBlockListedButMissingParentEntityId() {
        TransactionKindCriteria criteria = new TransactionKindCriteria(
                ENTITY_ID,
                "",
                "any",
                "any",
                "");
        TransactionKindResult expected = new TransactionKindResult(
                ENCODED_ENTITY_ID,
                TransactionKind.TOP_LEVEL);

        TransactionKindResult actual = kindService.encodeIdByTransactionKind(criteria);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "''", "'  '"
    })
    void shouldReturnUnchangedIdIfEmpty(final String entityId) {
        TransactionKindCriteria criteria = new TransactionKindCriteria(
                entityId,
                "",
                "TM01",
                "",
                "");
        TransactionKindResult expected = new TransactionKindResult(
                entityId,
                TransactionKind.TOP_LEVEL);

        TransactionKindResult actual = kindService.encodeIdByTransactionKind(criteria);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnUnchangedIdIfNull() {
        TransactionKindCriteria criteria = new TransactionKindCriteria(
                null,
                "",
                "TM01",
                "",
                "");
        TransactionKindResult expected = new TransactionKindResult(
                null,
                TransactionKind.TOP_LEVEL);

        TransactionKindResult actual = kindService.encodeIdByTransactionKind(criteria);

        assertEquals(expected, actual);
    }
}