package com.gb.vale.uitaylibrary.utils.scan.model;

final class BlockParsedResult {

    private final DecodedInformation decodedInformation;
    private final boolean finished;

    BlockParsedResult(boolean finished) {
        this(null, finished);
    }

    BlockParsedResult(DecodedInformation information, boolean finished) {
        this.finished = finished;
        this.decodedInformation = information;
    }

    DecodedInformation getDecodedInformation() {
        return this.decodedInformation;
    }

    boolean isFinished() {
        return this.finished;
    }
}
