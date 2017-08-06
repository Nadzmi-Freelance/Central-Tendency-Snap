package com.seladanghijau.centraltendencysnap.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.seladanghijau.centraltendencysnap.R;
import com.seladanghijau.centraltendencysnap.dto.XInput;
import com.seladanghijau.centraltendencysnap.provider.CalculatorGroupedData;
import com.seladanghijau.centraltendencysnap.provider.CalculatorUngroupData;

public class ComputeGroupDataMV extends AppCompatActivity implements View.OnClickListener {
    // views var
    TextView tvStandardDeviationTitle, tvStandardDeviationStep, tvStandardDeviationAnswer;
    TextView tvVarianceTitle, tvVarianceStep, tvVarianceAnswer;
    TextView tvCVTitle, tvCVStep, tvCVAnswer;
    Button btnSet2;

    // standard vars
    double cvSet1;
    String xInput, yInput;
    XInput xInputList;
    int[] yInputList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_group_data_mv);

        initVars();
        initViews();
        initListener();
        mainProcess();
    }

    // initialization ------------------------------------------------------------------------------
    private void initViews() {
        tvStandardDeviationTitle = (TextView) findViewById(R.id.tvStandardDeviationTitle);
        tvStandardDeviationStep = (TextView) findViewById(R.id.tvStandardDeviationStep);
        tvStandardDeviationAnswer = (TextView) findViewById(R.id.tvStandardDeviationAnswer);
        tvVarianceTitle = (TextView) findViewById(R.id.tvVarianceTitle);
        tvVarianceStep = (TextView) findViewById(R.id.tvVarianceStep);
        tvVarianceAnswer = (TextView) findViewById(R.id.tvVarianceAnswer);
        tvCVTitle = (TextView) findViewById(R.id.tvCVTitle);
        tvCVStep = (TextView) findViewById(R.id.tvCVStep);
        tvCVAnswer = (TextView) findViewById(R.id.tvCVAnswer);
        btnSet2 = (Button) findViewById(R.id.btnSet2);
    }

    private void initVars() {
        xInput = getIntent().getStringExtra("xInputList");
        yInput = getIntent().getStringExtra("yInputList");

        xInputList = CalculatorGroupedData.extractXInput(xInput);
        yInputList = CalculatorGroupedData.extractYInput(yInput);
    }

    private void initListener() {
        btnSet2.setOnClickListener(this);
    }
    // ---------------------------------------------------------------------------------------------

    // process -------------------------------------------------------------------------------------
    private void mainProcess() {
        tvStandardDeviationTitle.setText("Standard Deviation: " + CalculatorGroupedData.standardDeviation(xInputList, yInputList));
        tvStandardDeviationStep.setText(CalculatorGroupedData.standardDeviationStep(xInput, yInput, xInputList, yInputList));
        tvStandardDeviationAnswer.setText(CalculatorGroupedData.standardDeviationAnswer(xInputList, yInputList));

        tvVarianceTitle.setText("Variance: " + CalculatorGroupedData.variance(xInputList, yInputList));
        tvVarianceStep.setText(CalculatorGroupedData.varianceStep(xInput, yInput, xInputList, yInputList));
        tvVarianceAnswer.setText(CalculatorGroupedData.varianceAnswer(xInputList, yInputList));

        tvCVTitle.setText("Coefficient Variance: " + CalculatorGroupedData.cv(xInputList, yInputList));
        tvCVStep.setText(CalculatorGroupedData.cvStep(xInput, yInput, xInputList, yInputList));
        tvCVAnswer.setText(CalculatorGroupedData.cvAnswer(xInputList, yInputList));
    }
    // ---------------------------------------------------------------------------------------------

    // listener ------------------------------------------------------------------------------------
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSet2:
                Intent gotoSet2CV;

                // carry cvSet1 to cvSet2 activity
                gotoSet2CV = new Intent(this, GroupDataCVSet2.class)
                        .putExtra("cvSet1", cvSet1);

                startActivity(gotoSet2CV);
                break;
        }
    }
    // ---------------------------------------------------------------------------------------------
}
