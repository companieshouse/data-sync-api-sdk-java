package uk.gov.companieshouse.api.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.api.psc.Statement;
import uk.gov.companieshouse.api.utils.TestHelper;

class ReadConverterTest {

    private static final String ETAG = TestHelper.ETAG;

    private ReadConverter<Statement> pscStatementReadConverter;

    @BeforeEach
    void setUp(){
        pscStatementReadConverter = new ReadConverter<>(new ObjectMapper(), Statement.class);
    }
    @Test
    void correctlyConvertsDocumentToStatementObject(){
        Document source = Document.parse("{\"etag\" : \"etag\"}");
        Statement actualStatement = pscStatementReadConverter.convert(source);
        Assertions.assertNotNull(actualStatement);
        Assertions.assertEquals(ETAG, actualStatement.getEtag());
    }
}
