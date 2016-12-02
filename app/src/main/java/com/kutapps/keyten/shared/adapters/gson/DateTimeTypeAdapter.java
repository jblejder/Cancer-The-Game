package com.kutapps.keyten.shared.adapters.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class DateTimeTypeAdapter extends TypeAdapter<DateTime>
{
    private static final String PATTERN = "hh:mm dd/MM/yyyy";

    @Override
    public void write(JsonWriter out, DateTime value) throws IOException
    {
        out.value(value.toString(PATTERN));
    }

    @Override
    public DateTime read(JsonReader in) throws IOException
    {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(PATTERN);
        return new DateTime(formatter.parseDateTime(in.nextString()));
    }
}
