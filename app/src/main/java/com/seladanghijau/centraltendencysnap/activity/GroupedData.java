package com.seladanghijau.centraltendencysnap.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.seladanghijau.centraltendencysnap.R;

public class GroupedData extends AppCompatActivity implements View.OnClickListener {
    // views
    EditText etXInput, etYInput;
    Button btnCompute;

    // variables
    String xInputList, yInputList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouped_data);

        initViews();
        initVars();
    }

    // initialization ------------------------------------------------------------------------------
    private void initViews() {
        etXInput = (EditText) findViewById(R.id.etXInput);
        etYInput = (EditText) findViewById(R.id.etYInput);
        btnCompute = (Button) findViewById(R.id.btnCompute);

        if (btnCompute != null)
            btnCompute.setOnClickListener(this);
    }

    private void initVars() {
        xInputList = "";
        yInputList = "";
    }
    // ---------------------------------------------------------------------------------------------

    // listener ------------------------------------------------------------------------------------
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCompute:
                AlertDialog.Builder alertDialogBuilder;
                AlertDialog alertDialog;

                // get input string
                xInputList = etXInput.getText().toString().trim();
                yInputList = etYInput.getText().toString().trim();

                // check if string is empty
                if(xInputList.isEmpty() || yInputList.isEmpty()) {
                    // setup alert dialog
                    alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setMessage(R.string.exp_input_empty);

                    // show alert dialog
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    // setup alert dialog
                    alertDialogBuilder = new AlertDialog.Builder(this); // alert for user to choose MT/MV
                    alertDialogBuilder.setPositiveButton(R.string.goto_mt, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent computeGroupDataMT;

                            // carry data to MT
                            computeGroupDataMT = new Intent(getApplicationContext(), ComputeGroupDataMT.class)
                                    .putExtra("xInputList", xInputList)
                                    .putExtra("yInputList", yInputList);

                            startActivity(computeGroupDataMT); // goto MT
                        }
                    });
                    alertDialogBuilder.setNegativeButton(R.string.goto_mv, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent computeGroupDataMV;

                            // carry data to MV
                            computeGroupDataMV = new Intent(getApplicationContext(), ComputeGroupDataMV.class)
                                    .putExtra("xInputList", xInputList)
                                    .putExtra("yInputList", yInputList);

                            startActivity(computeGroupDataMV); // goto MV
                        }
                    });

                    // show alert dialog
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                break;
        }
    }
    // ---------------------------------------------------------------------------------------------
}
