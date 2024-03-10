package com.gb.vale.uitaylibrary.utils.scan;

import com.gb.vale.uitaylibrary.utils.scan.bitmap.BinaryBitmap;
import com.gb.vale.uitaylibrary.utils.scan.detector.AztecReader;
import com.gb.vale.uitaylibrary.utils.scan.detector.MaxiCodeReader;
import com.gb.vale.uitaylibrary.utils.scan.detector.PDF417Reader;
import com.gb.vale.uitaylibrary.utils.scan.exception.NotFoundException;
import com.gb.vale.uitaylibrary.utils.scan.enum_scan.BarcodeFormat;
import com.gb.vale.uitaylibrary.utils.scan.enum_scan.DecodeHintType;
import com.gb.vale.uitaylibrary.utils.scan.exception.ReaderException;
import com.gb.vale.uitaylibrary.utils.scan.extra.DataMatrixReader;
import com.gb.vale.uitaylibrary.utils.scan.extra.MultiFormatOneDReader;
import com.gb.vale.uitaylibrary.utils.scan.extra.QRCodeReader;
import com.gb.vale.uitaylibrary.utils.scan.extra.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class MultiFormatReader implements Reader {

    private Map<DecodeHintType,?> hints;
    private Reader[] readers;

    /**
     * This version of decode honors the intent of Reader.decode(BinaryBitmap) in that it
     * passes null as a hint to the decoders. However, that makes it inefficient to call repeatedly.
     * Use setHints() followed by decodeWithState() for continuous scan applications.
     *
     * @param image The pixel data to decode
     * @return The contents of the image
     * @throws NotFoundException Any errors which occurred
     */
    @Override
    public Result decode(BinaryBitmap image) throws NotFoundException {
        setHints(null);
        return decodeInternal(image);
    }

    /**
     * Decode an image using the hints provided. Does not honor existing state.
     *
     * @param image The pixel data to decode
     * @param hints The hints to use, clearing the previous state.
     * @return The contents of the image
     * @throws NotFoundException Any errors which occurred
     */
    @Override
    public Result decode(BinaryBitmap image, Map<DecodeHintType,?> hints) throws NotFoundException {
        setHints(hints);
        return decodeInternal(image);
    }

    /**
     * Decode an image using the state set up by calling setHints() previously. Continuous scan
     * clients will get a <b>large</b> speed increase by using this instead of decode().
     *
     * @param image The pixel data to decode
     * @return The contents of the image
     * @throws NotFoundException Any errors which occurred
     */
    public Result decodeWithState(BinaryBitmap image) throws NotFoundException {
        // Make sure to set up the default state so we don't crash
        if (readers == null) {
            setHints(null);
        }
        return decodeInternal(image);
    }

    /**
     * This method adds state to the MultiFormatReader. By setting the hints once, subsequent calls
     * to decodeWithState(image) can reuse the same set of readers without reallocating memory. This
     * is important for performance in continuous scan clients.
     *
     * @param hints The set of hints to use for subsequent calls to decode(image)
     */
    public void setHints(Map<DecodeHintType,?> hints) {
        this.hints = hints;

        boolean tryHarder = hints != null && hints.containsKey(DecodeHintType.TRY_HARDER);
        @SuppressWarnings("unchecked")
        Collection<BarcodeFormat> formats =
                hints == null ? null : (Collection<BarcodeFormat>) hints.get(DecodeHintType.POSSIBLE_FORMATS);
        Collection<Reader> readers = new ArrayList<>();
        if (formats != null) {
            boolean addOneDReader =
                    formats.contains(BarcodeFormat.UPC_A) ||
                            formats.contains(BarcodeFormat.UPC_E) ||
                            formats.contains(BarcodeFormat.EAN_13) ||
                            formats.contains(BarcodeFormat.EAN_8) ||
                            formats.contains(BarcodeFormat.CODABAR) ||
                            formats.contains(BarcodeFormat.CODE_39) ||
                            formats.contains(BarcodeFormat.CODE_93) ||
                            formats.contains(BarcodeFormat.CODE_128) ||
                            formats.contains(BarcodeFormat.ITF) ||
                            formats.contains(BarcodeFormat.RSS_14) ||
                            formats.contains(BarcodeFormat.RSS_EXPANDED);
            // Put 1D readers upfront in "normal" mode
            if (addOneDReader && !tryHarder) {
                readers.add(new MultiFormatOneDReader(hints));
            }
            if (formats.contains(BarcodeFormat.QR_CODE)) {
                readers.add(new QRCodeReader());
            }
            if (formats.contains(BarcodeFormat.DATA_MATRIX)) {
                readers.add(new DataMatrixReader());
            }
            if (formats.contains(BarcodeFormat.AZTEC)) {
                readers.add(new AztecReader());
            }
            if (formats.contains(BarcodeFormat.PDF_417)) {
                readers.add(new PDF417Reader());
            }
            if (formats.contains(BarcodeFormat.MAXICODE)) {
                readers.add(new MaxiCodeReader());
            }
            // At end in "try harder" mode
            if (addOneDReader && tryHarder) {
                readers.add(new MultiFormatOneDReader(hints));
            }
        }
        if (readers.isEmpty()) {
            if (!tryHarder) {
                readers.add(new MultiFormatOneDReader(hints));
            }

            readers.add(new QRCodeReader());
            readers.add(new DataMatrixReader());
            readers.add(new AztecReader());
            readers.add(new PDF417Reader());
            readers.add(new MaxiCodeReader());

            if (tryHarder) {
                readers.add(new MultiFormatOneDReader(hints));
            }
        }
        this.readers = readers.toArray(new Reader[readers.size()]);
    }

    @Override
    public void reset() {
        if (readers != null) {
            for (Reader reader : readers) {
                reader.reset();
            }
        }
    }

    private Result decodeInternal(BinaryBitmap image) throws NotFoundException {
        if (readers != null) {
            for (Reader reader : readers) {
                try {
                    return reader.decode(image, hints);
                } catch (ReaderException re) {
                    // continue
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

}
