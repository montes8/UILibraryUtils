package com.gb.vale.uitaylibrary.utils.scan.model;

final class DecodedChar extends DecodedObject {

    private final char value;

    static final char FNC1 = '$'; // It's not in Alphanumeric neither in ISO/IEC 646 charset

    DecodedChar(int newPosition, char value) {
        super(newPosition);
        this.value = value;
    }

    char getValue() {
        return this.value;
    }

    boolean isFNC1() {
        return this.value == FNC1;
    }

}
