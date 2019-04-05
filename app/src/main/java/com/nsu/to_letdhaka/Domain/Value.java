package com.nsu.to_letdhaka.Domain;

import java.util.Objects;

public class Value {
    private String value;

    public Value() {
    }

    public Value(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Value{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Value)) return false;
        Value value1 = (Value) o;
        return Objects.equals(getValue(), value1.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    public int getvalue(){
        return Integer.valueOf(value);
    }
}
