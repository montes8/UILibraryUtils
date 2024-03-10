package com.gb.vale.uitaylibrary.utils.scan.extra;

import com.gb.vale.uitaylibrary.utils.scan.Reader;
import com.gb.vale.uitaylibrary.utils.scan.bitmap.BitArray;
import com.gb.vale.uitaylibrary.utils.scan.enum_scan.BarcodeFormat;
import com.gb.vale.uitaylibrary.utils.scan.enum_scan.DecodeHintType;
import com.gb.vale.uitaylibrary.utils.scan.exception.NotFoundException;
import com.gb.vale.uitaylibrary.utils.scan.exception.ReaderException;
import com.gb.vale.uitaylibrary.utils.scan.model.CodaBarReader;
import com.gb.vale.uitaylibrary.utils.scan.model.Code128Reader;
import com.gb.vale.uitaylibrary.utils.scan.model.Code39Reader;
import com.gb.vale.uitaylibrary.utils.scan.model.Code93Reader;
import com.gb.vale.uitaylibrary.utils.scan.model.ITFReader;
import com.gb.vale.uitaylibrary.utils.scan.model.RSS14Reader;
import com.gb.vale.uitaylibrary.utils.scan.model.RSSExpandedReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class MultiFormatOneDReader extends OneDReader {

    private final OneDReader[] readers;

    public MultiFormatOneDReader(Map<DecodeHintType,?> hints) {
        @SuppressWarnings("unchecked")
        Collection<BarcodeFormat> possibleFormats = hints == null ? null :
                (Collection<BarcodeFormat>) hints.get(DecodeHintType.POSSIBLE_FORMATS);
        boolean useCode39CheckDigit = hints != null &&
                hints.get(DecodeHintType.ASSUME_CODE_39_CHECK_DIGIT) != null;
        Collection<OneDReader> readers = new ArrayList<>();
        if (possibleFormats != null) {
            if (possibleFormats.contains(BarcodeFormat.EAN_13) ||
                    possibleFormats.contains(BarcodeFormat.UPC_A) ||
                    possibleFormats.contains(BarcodeFormat.EAN_8) ||
                    possibleFormats.contains(BarcodeFormat.UPC_E)) {
                readers.add(new MultiFormatUPCEANReader(hints));
            }
            if (possibleFormats.contains(BarcodeFormat.CODE_39)) {
                readers.add(new Code39Reader(useCode39CheckDigit));
            }
            if (possibleFormats.contains(BarcodeFormat.CODE_93)) {
                readers.add(new Code93Reader());
            }
            if (possibleFormats.contains(BarcodeFormat.CODE_128)) {
                readers.add(new Code128Reader());
            }
            if (possibleFormats.contains(BarcodeFormat.ITF)) {
                readers.add(new ITFReader());
            }
            if (possibleFormats.contains(BarcodeFormat.CODABAR)) {
                readers.add(new CodaBarReader());
            }
            if (possibleFormats.contains(BarcodeFormat.RSS_14)) {
                readers.add(new RSS14Reader());
            }
            if (possibleFormats.contains(BarcodeFormat.RSS_EXPANDED)) {
                readers.add(new RSSExpandedReader());
            }
        }
        if (readers.isEmpty()) {
            readers.add(new MultiFormatUPCEANReader(hints));
            readers.add(new Code39Reader());
            readers.add(new CodaBarReader());
            readers.add(new Code93Reader());
            readers.add(new Code128Reader());
            readers.add(new ITFReader());
            readers.add(new RSS14Reader());
            readers.add(new RSSExpandedReader());
        }
        this.readers = readers.toArray(new OneDReader[readers.size()]);
    }

    @Override
    public Result decodeRow(int rowNumber,
                            BitArray row,
                            Map<DecodeHintType,?> hints) throws NotFoundException {
        for (OneDReader reader : readers) {
            try {
                return reader.decodeRow(rowNumber, row, hints);
            } catch (ReaderException re) {
                // continue
            }
        }

        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public void reset() {
        for (Reader reader : readers) {
            reader.reset();
        }
    }

}
