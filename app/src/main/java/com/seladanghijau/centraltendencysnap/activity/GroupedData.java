package com.seladanghijau.centraltendencysnap.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.seladanghijau.centraltendencysnap.R;
import com.seladanghijau.centraltendencysnap.provider.CalculatorGroupData;

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
                Intent computeGroupData;

                xInputList = etXInput.getText().toString().trim();
                yInputList = etYInput.getText().toString().trim();

                computeGroupData = new Intent(this, ComputeGroupData.class);
                computeGroupData.putExtra("xInputList", xInputList);
                computeGroupData.putExtra("yInputList", yInputList);

                startActivity(computeGroupData);
                break;
        }
    }
    // ---------------------------------------------------------------------------------------------
}
