package com.xebia.yakshop.models;

public enum SexInternal {

    M("m"),
    F("f");

    public final String label;

    private SexInternal(String label) {
        this.label = label;
    }
}
