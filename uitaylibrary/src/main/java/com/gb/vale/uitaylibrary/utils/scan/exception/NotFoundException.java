package com.gb.vale.uitaylibrary.utils.scan.exception;

public final class NotFoundException extends ReaderException {

    private static final NotFoundException INSTANCE = new NotFoundException();
    static {
        INSTANCE.setStackTrace(NO_TRACE); // since it's meaningless
    }

    private NotFoundException() {
        // do nothing
    }

    public static NotFoundException getNotFoundInstance() {
        return INSTANCE;
    }

}
