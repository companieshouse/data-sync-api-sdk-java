package uk.gov.companieshouse.api.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import uk.gov.companieshouse.api.exception.BadRequestException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            JsonNode jsonNode = jsonParser.readValueAsTree();
            JsonNode dateNode = jsonNode.get("$date");

            return dateNode.textValue() != null ?
                    LocalDate.parse(dateNode.textValue(), dateTimeFormatter) :
                    Instant.ofEpochMilli(dateNode.get("$numberLong").asLong())
                            .atZone(ZoneId.systemDefault()).toLocalDate();

        } catch (Exception exception) {
            throw new BadRequestException("Deserialization failed.", exception);
        }
    }

}
