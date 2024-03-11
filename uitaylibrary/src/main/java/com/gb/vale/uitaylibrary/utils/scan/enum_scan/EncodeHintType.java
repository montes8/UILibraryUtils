package com.gb.vale.uitaylibrary.utils.scan.enum_scan;

/**
 * These are a set of hints that you may pass to Writers to specify their behavior.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public enum EncodeHintType {

    ERROR_CORRECTION,

    /**
     * Specifies what character encoding to use where applicable (type {@link String})
     */
    CHARACTER_SET,

    DATA_MATRIX_SHAPE,

    @Deprecated
    MIN_SIZE,


    @Deprecated
    MAX_SIZE,

    /**
     * Specifies margin, in pixels, to use when generating the barcode. The meaning can vary
     * by format; for example it controls margin before and after the barcode horizontally for
     * most 1D formats. (Type {@link Integer}, or {@link String} representation of the integer value).
     */
    MARGIN,

    /**
     * Specifies whether to use compact mode for PDF417 (type {@link Boolean}, or "true" or "false"
     * {@link String} value).
     */
    PDF417_COMPACT,


    PDF417_COMPACTION,


    PDF417_DIMENSIONS,

    /**
     * Specifies the required number of layers for an Aztec code.
     * A negative number (-1, -2, -3, -4) specifies a compact Aztec code.
     * 0 indicates to use the minimum number of layers (the default).
     * A positive number (1, 2, .. 32) specifies a normal (non-compact) Aztec code.
     * (Type {@link Integer}, or {@link String} representation of the integer value).
     */
    AZTEC_LAYERS,

    /**
     * Specifies the exact version of QR code to be encoded.
     * (Type {@link Integer}, or {@link String} representation of the integer value).
     */
    QR_VERSION,
}
