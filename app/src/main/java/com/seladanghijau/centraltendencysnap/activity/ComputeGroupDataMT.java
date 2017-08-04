package com.seladanghijau.centraltendencysnap.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.seladanghijau.centraltendencysnap.R;
import com.seladanghijau.centraltendencysnap.dto.XInput;
import com.seladanghijau.centraltendencysnap.provider.CalculatorGroupedData;

public class ComputeGroupDataMT extends AppCompatActivity {
    // views
    TextView tvMeanTitle, tvMeanStep, tvMeanAnswer;
    TextView tvMedianTitle, tvMedianStep, tvMedianAnswer;
    TextView tvModeTitle, tvModeStep, tvModeAnswer;

    // vars
    String xInput, yInput;
    XInput xInputList;
    int[] yInputList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_group_data_mt);

        initViews();
        initVars();
        process();
    }

    // initialization ------------------------------------------------------------------------------
    private void initViews() {
        tvMeanTitle = (TextView) findViewById(R.id.tvMeanTitle);
        tvMeanStep = (TextView) findViewById(R.id.tvMeanStep);
        tvMeanAnswer = (TextView) findViewById(R.id.tvMeanAnswer);

        tvMedianTitle = (TextView) findViewById(R.id.tvMedianTitle);
        tvMedianStep = (TextView) findViewById(R.id.tvMedianStep);
        tvMedianAnswer = (TextView) findViewById(R.id.tvMedianAnswer);

        tvModeTitle = (TextView) findViewById(R.id.tvModeTitle);
        tvModeStep = (TextView) findViewById(R.id.tvModeStep);
        tvModeAnswer = (TextView) findViewById(R.id.tvModeAnswer);
    }

    private void initVars() {
        xInput = getIntent().getStringExtra("xInputList");
        yInput = getIntent().getStringExtra("yInputList");

        xInputList = CalculatorGroupedData.extractXInput(xInput);
        yInputList = CalculatorGroupedData.extractYInput(yInput);
    }

    private void process() {
        tvMeanTitle.setText("Mean: " + CalculatorGroupedData.mean(xInputList, yInputList));
        tvMeanStep.setText(CalculatorGroupedData.meanStep(xInput, yInput, xInputList, yInputList));
        tvMeanAnswer.setText(CalculatorGroupedData.meanAnswer(xInputList, yInputList));

        tvMedianTitle.setText("Median: " + CalculatorGroupedData.median(xInputList, yInputList));
        tvMedianStep.setText(CalculatorGroupedData.medianStep(xInput, yInput, xInputList, yInputList));
        tvMedianAnswer.setText(CalculatorGroupedData.medianAnswer(xInputList, yInputList));

        tvModeTitle.setText("Mode: " + CalculatorGroupedData.mode(xInputList, yInputList));
        tvModeStep.setText(CalculatorGroupedData.modeStep(xInput, yInput, xInputList, yInputList));
        tvModeAnswer.setText(CalculatorGroupedData.modeAnswer(xInputList, yInputList));
    }
    // ---------------------------------------------------------------------------------------------
}
