package com.gb.vale.uitaylibrary.utils.scan.detector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

final class BarcodeValue {
    private final Map<Integer,Integer> values = new HashMap<>();

    /**
     * Add an occurrence of a value
     */
    void setValue(int value) {
        Integer confidence = values.get(value);
        if (confidence == null) {
            confidence = 0;
        }
        confidence++;
        values.put(value, confidence);
    }

    /**
     * Determines the maximum occurrence of a set value and returns all values which were set with this occurrence.
     * @return an array of int, containing the values with the highest occurrence, or null, if no value was set
     */
    int[] getValue() {
        int maxConfidence = -1;
        Collection<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer,Integer> entry : values.entrySet()) {
            if (entry.getValue() > maxConfidence) {
                maxConfidence = entry.getValue();
                result.clear();
                result.add(entry.getKey());
            } else if (entry.getValue() == maxConfidence) {
                result.add(entry.getKey());
            }
        }
        return PDF417Common.toIntArray(result);
    }

    public Integer getConfidence(int value) {
        return values.get(value);
    }

}
