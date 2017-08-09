package com.seladanghijau.centraltendencysnap.provider;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Environment;


public class OCRProvider {
    private static final String TESSDATA_DIR = "centraltendencysnap";
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

    // FIXME: 29/8/2016 - function to corect the input
    public static String getTokenizedString(String data) {
        String result;

        if(data.contains("\n")) {
            String[] dataPart;
            String tempResult;

            tempResult = "";
            dataPart = data.split("\n");
            for (String aDataPart : dataPart)
                tempResult += " " + aDataPart.trim();

            result = tempResult;
        } else
            result = data;

        result = finalRepair(result);

        return result;
    }

    private static String finalRepair(String data) {
        String[] dataparts;
        String tempResult;

        tempResult = data;
        dataparts = tempResult.split(",");

        tempResult = "";
        for(int x=0 ; x<dataparts.length ; x++) {
            if(dataparts[x].equalsIgnoreCase("s"))
                dataparts[x] = "5";

            if(x == dataparts.length-1)
                tempResult += dataparts[x].trim();
            else
                tempResult += dataparts[x].trim() + ", ";
        }

        return tempResult;
    }
}
