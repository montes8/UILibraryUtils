package com.gb.vale.uitaylibrary.utils.scan.detector;

import com.gb.vale.uitaylibrary.utils.scan.Reader;
import com.gb.vale.uitaylibrary.utils.scan.bitmap.BinaryBitmap;
import com.gb.vale.uitaylibrary.utils.scan.enum_scan.BarcodeFormat;
import com.gb.vale.uitaylibrary.utils.scan.enum_scan.DecodeHintType;
import com.gb.vale.uitaylibrary.utils.scan.enum_scan.ResultMetadataType;
import com.gb.vale.uitaylibrary.utils.scan.exception.FormatException;
import com.gb.vale.uitaylibrary.utils.scan.exception.NotFoundException;
import com.gb.vale.uitaylibrary.utils.scan.extra.Result;
import com.gb.vale.uitaylibrary.utils.scan.extra.ResultPoint;
import com.gb.vale.uitaylibrary.utils.scan.extra.ResultPointCallback;
import com.gb.vale.uitaylibrary.utils.scan.model.AztecDetectorResult;
import com.gb.vale.uitaylibrary.utils.scan.model.DecoderResult;

import java.util.List;
import java.util.Map;

public final class AztecReader implements Reader {

    /**
     * Locates and decodes a Data Matrix code in an image.
     *
     * @return a String representing the content encoded by the Data Matrix code
     * @throws NotFoundException if a Data Matrix code cannot be found
     * @throws FormatException if a Data Matrix code cannot be decoded
     */
    @Override
    public Result decode(BinaryBitmap image) throws NotFoundException, FormatException {
        return decode(image, null);
    }

    @Override
    public Result decode(BinaryBitmap image, Map<DecodeHintType,?> hints)
            throws NotFoundException, FormatException {

        NotFoundException notFoundException = null;
        FormatException formatException = null;
        Detector detector = new Detector(image.getBlackMatrix());
        ResultPoint[] points = null;
        DecoderResult decoderResult = null;
        try {
            AztecDetectorResult detectorResult = detector.detect(false);
            points = detectorResult.getPoints();
            decoderResult = new Decoder().decode(detectorResult);
        } catch (NotFoundException e) {
            notFoundException = e;
        } catch (FormatException e) {
            formatException = e;
        }
        if (decoderResult == null) {
            try {
                AztecDetectorResult detectorResult = detector.detect(true);
                points = detectorResult.getPoints();
                decoderResult = new Decoder().decode(detectorResult);
            } catch (NotFoundException | FormatException e) {
                if (notFoundException != null) {
                    throw notFoundException;
                }
                if (formatException != null) {
                    throw formatException;
                }
                throw e;
            }
        }

        if (hints != null) {
            ResultPointCallback rpcb = (ResultPointCallback) hints.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
            if (rpcb != null) {
                for (ResultPoint point : points) {
                    rpcb.foundPossibleResultPoint(point);
                }
            }
        }

        Result result = new Result(decoderResult.getText(),
                decoderResult.getRawBytes(),
                decoderResult.getNumBits(),
                points,
                BarcodeFormat.AZTEC,
                System.currentTimeMillis());

        List<byte[]> byteSegments = decoderResult.getByteSegments();
        if (byteSegments != null) {
            result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
        }
        String ecLevel = decoderResult.getECLevel();
        if (ecLevel != null) {
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ecLevel);
        }

        return result;
    }

    @Override
    public void reset() {
        // do nothing
    }

}
