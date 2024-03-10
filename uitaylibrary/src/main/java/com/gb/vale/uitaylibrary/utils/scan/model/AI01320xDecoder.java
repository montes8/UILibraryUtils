package com.gb.vale.uitaylibrary.utils.scan.model;

import com.gb.vale.uitaylibrary.utils.scan.bitmap.BitArray;
import com.gb.vale.uitaylibrary.utils.scan.model.AI013x0xDecoder;

final class AI01320xDecoder extends AI013x0xDecoder {

    AI01320xDecoder(BitArray information) {
        super(information);
    }

    @Override
    protected void addWeightCode(StringBuilder buf, int weight) {
        if (weight < 10000) {
            buf.append("(3202)");
        } else {
            buf.append("(3203)");
        }
    }

    @Override
    protected int checkWeight(int weight) {
        if (weight < 10000) {
            return weight;
        }
        return weight - 10000;
    }

}

