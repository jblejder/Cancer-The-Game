package com.kutapps.keyten.shared.database;

public interface Mapper<IN, OUT> {
    OUT map(IN v);
}
