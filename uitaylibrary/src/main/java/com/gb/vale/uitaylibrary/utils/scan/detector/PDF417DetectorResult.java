package com.gb.vale.uitaylibrary.utils.scan.detector;

import com.gb.vale.uitaylibrary.utils.scan.bitmap.BitMatrix;
import com.gb.vale.uitaylibrary.utils.scan.extra.ResultPoint;

import java.util.List;

public final class PDF417DetectorResult {

    private final BitMatrix bits;
    private final List<ResultPoint[]> points;

    public PDF417DetectorResult(BitMatrix bits, List<ResultPoint[]> points) {
        this.bits = bits;
        this.points = points;
    }

    public BitMatrix getBits() {
        return bits;
    }

    public List<ResultPoint[]> getPoints() {
        return points;
    }

}
