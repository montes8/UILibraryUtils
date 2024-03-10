package com.gb.vale.uitaylibrary.utils.scan.model;

import com.gb.vale.uitaylibrary.utils.scan.bitmap.BitArray;
import com.gb.vale.uitaylibrary.utils.scan.enum_scan.BarcodeFormat;
import com.gb.vale.uitaylibrary.utils.scan.exception.NotFoundException;

public final class EAN8Reader extends UPCEANReader {

    private final int[] decodeMiddleCounters;

    public EAN8Reader() {
        decodeMiddleCounters = new int[4];
    }

    @Override
    protected int decodeMiddle(BitArray row,
                               int[] startRange,
                               StringBuilder result) throws NotFoundException {
        int[] counters = decodeMiddleCounters;
        counters[0] = 0;
        counters[1] = 0;
        counters[2] = 0;
        counters[3] = 0;
        int end = row.getSize();
        int rowOffset = startRange[1];

        for (int x = 0; x < 4 && rowOffset < end; x++) {
            int bestMatch = decodeDigit(row, counters, rowOffset, L_PATTERNS);
            result.append((char) ('0' + bestMatch));
            for (int counter : counters) {
                rowOffset += counter;
            }
        }

        int[] middleRange = findGuardPattern(row, rowOffset, true, MIDDLE_PATTERN);
        rowOffset = middleRange[1];

        for (int x = 0; x < 4 && rowOffset < end; x++) {
            int bestMatch = decodeDigit(row, counters, rowOffset, L_PATTERNS);
            result.append((char) ('0' + bestMatch));
            for (int counter : counters) {
                rowOffset += counter;
            }
        }

        return rowOffset;
    }

    @Override
    BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.EAN_8;
    }

}
