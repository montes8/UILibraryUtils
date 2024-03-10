package com.gb.vale.uitaylibrary.utils.scan.model;

import com.gb.vale.uitaylibrary.utils.scan.bitmap.BitArray;
import com.gb.vale.uitaylibrary.utils.scan.model.AI01decoder;

abstract class AI01weightDecoder extends AI01decoder {

    AI01weightDecoder(BitArray information) {
        super(information);
    }

    final void encodeCompressedWeight(StringBuilder buf, int currentPos, int weightSize) {
        int originalWeightNumeric = this.getGeneralDecoder().extractNumericValueFromBitArray(currentPos, weightSize);
        addWeightCode(buf, originalWeightNumeric);

        int weightNumeric = checkWeight(originalWeightNumeric);

        int currentDivisor = 100000;
        for (int i = 0; i < 5; ++i) {
            if (weightNumeric / currentDivisor == 0) {
                buf.append('0');
            }
            currentDivisor /= 10;
        }
        buf.append(weightNumeric);
    }

    protected abstract void addWeightCode(StringBuilder buf, int weight);

    protected abstract int checkWeight(int weight);

}
