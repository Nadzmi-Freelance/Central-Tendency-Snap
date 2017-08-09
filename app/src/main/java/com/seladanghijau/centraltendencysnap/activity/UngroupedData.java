package com.seladanghijau.centraltendencysnap.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.seladanghijau.centraltendencysnap.R;
import com.seladanghijau.centraltendencysnap.asynctask.CopyTraineddataFile;
import com.seladanghijau.centraltendencysnap.asynctask.ExtractTextAsyncTask;
import com.seladanghijau.centraltendencysnap.manager.OCRManager;
import com.seladanghijau.centraltendencysnap.provider.OCRProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class UngroupedData extends AppCompatActivity implements OCRManager, View.OnClickListener {
    // views
    ImageView ivImg;
    EditText etResult;
    Button btnCapture, btnUpload, btnCompute;

    // vars
    String result;
    int[] ungroupDatas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ungrouped_data);

        initViews();
        initOCR();
    }

    // initialization ------------------------------------------------------------------------------
    private void initViews() {
        ivImg = (ImageView) findViewById(R.id.ivImg);
        etResult = (EditText) findViewById(R.id.etResult);
        btnCapture = (Button) findViewById(R.id.btnCapture);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnCompute = (Button) findViewById(R.id.btnCompute);

        btnCapture.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        btnCompute.setOnClickListener(this);
    }

    private void initOCR() {
        onInit();
    }
    // ---------------------------------------------------------------------------------------------

    // util methods --------------------------------------------------------------------------------
    private boolean checkTrainedData() {
        return new File(OCRProvider.TESSDATA_DIR_PATH + "/tessdata/" + OCRProvider.TESSDATA_ENG).exists();
    }

    private void copyTrainedData() throws Exception {
        InputStream from;
        OutputStream to;
        File outFile;

        outFile = new File(OCRProvider.TESSDATA_DIR_PATH + "/tessdata/" + OCRProvider.TESSDATA_ENG);
        if(outFile.getParentFile().mkdirs()) {
            from = getAssets().open("eng.traineddata");
            to = new FileOutputStream(outFile);

            new CopyTraineddataFile(from, to).execute();
        }
    }

    public void performCrop(Uri imageUri) {
        Intent cropIntent;

        try {
            cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(imageUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("scale", true);
            cropIntent.putExtra("noFaceDetection", true);
            cropIntent.putExtra("return-data", true);

            startActivityForResult(cropIntent, OCRProvider.PIC_CROP);
        } catch (Exception e) { e.printStackTrace(); }
    }
    // ---------------------------------------------------------------------------------------------

    // listener ------------------------------------------------------------------------------------
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCapture:
                Intent cameraIntent;

                cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(cameraIntent, OCRProvider.REQUEST_IMAGE_CAPTURE);
                break;
            case R.id.btnUpload:
                Intent uploadIntent;

                uploadIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(uploadIntent, OCRProvider.REQUEST_IMAGE_UPLOAD);
                break;
            case R.id.btnCompute:
                String result;
                String[] tokenizedResult;
                AlertDialog.Builder alertDialogBuilder;
                AlertDialog alertDialog;

                // tokenize string --> get data from string
                result = OCRProvider.getTokenizedString(etResult.getText().toString());
                tokenizedResult = result.trim().split(",");
                ungroupDatas = new int[tokenizedResult.length];
                for(int x=0 ; x<tokenizedResult.length ; x++)
                    ungroupDatas[x] = Integer.parseInt(tokenizedResult[x].trim());

                // alert dialog
                // setup alert dialog
                alertDialogBuilder = new AlertDialog.Builder(this); // alert for user to choose MT/MV
                alertDialogBuilder.setPositiveButton(R.string.goto_mt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent computeGroupDataMT;

                        // carry data to MT
                        computeGroupDataMT = new Intent(getApplicationContext(), ComputeUngroupDataMT.class)
                                .putExtra("ungroup-data", ungroupDatas);

                        startActivity(computeGroupDataMT); // goto MT
                    }
                });
                alertDialogBuilder.setNegativeButton(R.string.goto_mv, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent computeGroupDataMV;

                        // carry data to MV
                        computeGroupDataMV = new Intent(getApplicationContext(), ComputeUngroupDataMV.class)
                                .putExtra("ungroup-data", ungroupDatas);

                        startActivity(computeGroupDataMV); // goto MV
                    }
                });

                // show alert dialog
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
        }
    }
    // ---------------------------------------------------------------------------------------------

    // manager methods -----------------------------------------------------------------------------
    public void onInit() {
        try {
            if(!checkTrainedData())
                copyTrainedData();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void onLoad(String result) {
        this.result = result;

        etResult.setText(result);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case OCRProvider.REQUEST_IMAGE_CAPTURE:
                    Uri imgUri;

                    imgUri = data.getData();
                    performCrop(imgUri);
                    break;
                case OCRProvider.REQUEST_IMAGE_UPLOAD:
                    Uri imageUri;

                    imageUri = data.getData();
                    performCrop(imageUri);
                    break;
                case  OCRProvider.PIC_CROP:
                    Bundle extras;
                    Bitmap croppedImg;

                    extras = data.getExtras();
                    croppedImg = extras.getParcelable("data");
                    croppedImg = OCRProvider.toGrayscale(croppedImg);

                    ivImg.setImageBitmap(croppedImg);
                    new ExtractTextAsyncTask(this, this, croppedImg).execute();

                    ivImg.invalidate();
                    break;
            }
        }
    }
    // ---------------------------------------------------------------------------------------------
}
