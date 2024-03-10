package com.gb.vale.uitaylibrary.utils.scan.detector;

import com.gb.vale.uitaylibrary.utils.scan.bitmap.BinaryBitmap;
import com.gb.vale.uitaylibrary.utils.scan.enum_scan.DecodeHintType;
import com.gb.vale.uitaylibrary.utils.scan.exception.NotFoundException;
import com.gb.vale.uitaylibrary.utils.scan.extra.Result;

import java.util.Map;

public interface MultipleBarcodeReader {

    Result[] decodeMultiple(BinaryBitmap image) throws NotFoundException;

    Result[] decodeMultiple(BinaryBitmap image,
                            Map<DecodeHintType,?> hints) throws NotFoundException;

}
