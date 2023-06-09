package uk.gov.companieshouse.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class EnumWriteConverter implements Converter<Enum<?>, String> {

    @Override
    public String convert(Enum<?> source) {
        try {
            return source.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
