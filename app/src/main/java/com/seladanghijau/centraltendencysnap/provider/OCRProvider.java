package com.seladanghijau.centraltendencysnap.provider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;

/**
 * Created by seladanghijau on 14/8/2016.
 */
public class OCRProvider {
    public static final String TESSDATA_DIR = "centraltendencysnap";
    public static final String TESSDATA_DIR_PATH = Environment.getExternalStorageDirectory().getPath() +  "/" + TESSDATA_DIR;
    public static final String TESSDATA_ENG = "eng.traineddata";
    public static final String LANGUAGE = "eng";

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_IMAGE_UPLOAD = 2;
    public static final int PIC_CROP = 3;

    public static Bitmap toGrayscale(Bitmap image) {
        int width, height;
        Paint grayscalePaint;
        Bitmap grayscale;
        Canvas grayscaleCanvas;
        ColorMatrix colorMatrix;
        ColorMatrixColorFilter colorMatrixColorFilter;

        width = image.getWidth();
        height = image.getHeight();
        grayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        grayscaleCanvas = new Canvas(grayscale);
        grayscalePaint = new Paint();
        colorMatrix = new ColorMatrix();

        colorMatrix.setSaturation(0);
        colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        grayscalePaint.setColorFilter(colorMatrixColorFilter);
        grayscaleCanvas.drawBitmap(image, 0, 0, grayscalePaint);

        return grayscale;
    }
}
