package com.gb.vale.uitaylibrary.utils.scan.model;

abstract class DecodedObject {

    private final int newPosition;

    DecodedObject(int newPosition) {
        this.newPosition = newPosition;
    }

    final int getNewPosition() {
        return this.newPosition;
    }

}
