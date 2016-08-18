package com.seladanghijau.centraltendencysnap.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.seladanghijau.centraltendencysnap.R;
import com.seladanghijau.centraltendencysnap.asynctask.CopyTraineddataFile;
import com.seladanghijau.centraltendencysnap.asynctask.ExtractTextAsyncTask;
import com.seladanghijau.centraltendencysnap.manager.OCRManager;
import com.seladanghijau.centraltendencysnap.provider.OCRProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements OCRManager, View.OnClickListener {
    // views
    ImageView ivImg;
    EditText etResult;
    Button btnCapture, btnUpload, btnCompute;

    // vars
    String result;
    int[] ungroupDatas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                String[] tokenizedResult;

                tokenizedResult = etResult.getText().toString().split(", ");
                ungroupDatas = new int[tokenizedResult.length];
                for(int x=0 ; x<tokenizedResult.length ; x++) {
                    ungroupDatas[x] = Integer.parseInt(tokenizedResult[x]);
                }

                startActivity(new Intent(this, Compute.class).putExtra("ungroup-data", ungroupDatas));
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
        if (requestCode == OCRProvider.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap;

            imageBitmap = (Bitmap) data.getExtras().get("data");

            ivImg.setImageBitmap(imageBitmap);
            new ExtractTextAsyncTask(this, this, imageBitmap).execute();
        } else if (requestCode == OCRProvider.REQUEST_IMAGE_UPLOAD && resultCode == RESULT_OK) {
            Uri imageUri;
            Bitmap imageBitmap;

            try {
                imageUri = data.getData();
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                ivImg.setImageBitmap(imageBitmap);
                new ExtractTextAsyncTask(this, this, imageBitmap).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // ---------------------------------------------------------------------------------------------
}
