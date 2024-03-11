package com.gb.vale.uitaylibrary.utils.scan.enum_scan;

/**
 * Data object to specify the minimum and maximum number of rows and columns for a PDF417 barcode.
 *
 * @author qwandor@google.com (Andrew Walbran)
 */
public final class Dimensions {

    private final int minCols;
    private final int maxCols;
    private final int minRows;
    private final int maxRows;

    public Dimensions(int minCols, int maxCols, int minRows, int maxRows) {
        this.minCols = minCols;
        this.maxCols = maxCols;
        this.minRows = minRows;
        this.maxRows = maxRows;
    }

    public int getMinCols() {
        return minCols;
    }

    public int getMaxCols() {
        return maxCols;
    }

    public int getMinRows() {
        return minRows;
    }

    public int getMaxRows() {
        return maxRows;
    }

}
