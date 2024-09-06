package org.example;

public class Element {
    int value;

    public Element(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

