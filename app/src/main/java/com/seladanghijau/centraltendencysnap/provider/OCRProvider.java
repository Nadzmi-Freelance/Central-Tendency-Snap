package com.seladanghijau.centraltendencysnap.provider;

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
}
