package com.vk.birthdaynotification.dto;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class MultiDateDeserializer extends StdDeserializer<Date> {
    private static final long serialVersionUID = 1L;

    private static final SimpleDateFormat[] DATE_FORMATTERS = new SimpleDateFormat[]{
            new SimpleDateFormat("dd.MM.yyyy"),
            new SimpleDateFormat("dd.MM.yyyy"),
            new SimpleDateFormat("d.MM.yyyy"),
            new SimpleDateFormat("d.M.yyyy"),
            new SimpleDateFormat("dd.MM")
    };

    public MultiDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        final String date = node.textValue();

        for (SimpleDateFormat formatter : DATE_FORMATTERS) {
            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        throw new JsonParseException(jp, "Unparseable date: \"" + date + "\". Supported formats: " +
                Arrays.stream(DATE_FORMATTERS).map(SimpleDateFormat::toPattern).collect(Collectors.joining("; ")));
    }
}
