package com.seladanghijau.centraltendencysnap.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // views
    Button btnGroupData, btnUngroupData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    // initialization ------------------------------------------------------------------------------
    private void initViews() {
        btnGroupData = (Button) findViewById(R.id.btnGroupData);
        btnUngroupData = (Button) findViewById(R.id.btnUngroupData);

        btnGroupData.setOnClickListener(this);
        btnUngroupData.setOnClickListener(this);
    }
    // ---------------------------------------------------------------------------------------------

    // listener ------------------------------------------------------------------------------------
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGroupData:
                startActivity(new Intent(this, GroupedData.class));
                break;
            case R.id.btnUngroupData:
                startActivity(new Intent(this, UngroupedData.class));
                break;
        }
    }
    // ---------------------------------------------------------------------------------------------
}
