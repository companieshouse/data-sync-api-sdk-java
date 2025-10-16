package uk.gov.companieshouse.api.converter;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.gov.companieshouse.api.exception.InternalServiceException;

@ReadingConverter
public class ReadConverter<T> implements Converter<Document, T> {

    private final ObjectMapper objectMapper;
    private final Class<T> objectClass;

    public ReadConverter(ObjectMapper objectMapper, Class<T> objectClass) {
        this.objectMapper = objectMapper;
        this.objectClass = objectClass;
    }

    @Override
    public T convert(Document source) {
        try {
            return this.objectMapper.readValue(source.toJson(), objectClass);
        } catch (JsonProcessingException e) {
            throw new InternalServiceException("failed to read and convert Document to JSON", e);
        }
    }
}
