package com.kutapps.keyten.shared.adapters.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.joda.time.DateTime;

import java.io.IOException;

public class DateTimeTypeAdapter extends TypeAdapter<DateTime> {
    @Override
    public void write(JsonWriter out, DateTime value) throws IOException {
        out.value(value.toString());
    }

    @Override
    public DateTime read(JsonReader in) throws IOException {
        return new DateTime(in.nextString());
    }
}
