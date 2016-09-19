package com.seladanghijau.centraltendencysnap.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.seladanghijau.centraltendencysnap.R;
import com.seladanghijau.centraltendencysnap.dto.XInput;
import com.seladanghijau.centraltendencysnap.provider.GroupedDataProvider;

import java.util.List;

public class ComputeGroupData extends AppCompatActivity {
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
        setContentView(R.layout.activity_compute_group_data);

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

        xInputList = GroupedDataProvider.extractXInput(xInput);
        yInputList = GroupedDataProvider.extractYInput(yInput);
    }

    private void process() {
        tvMeanTitle.setText("Mean: " + GroupedDataProvider.mean(xInputList, yInputList));
        tvMeanStep.setText(GroupedDataProvider.meanStep(xInput, yInput, xInputList, yInputList));
        tvMeanAnswer.setText(GroupedDataProvider.meanAnswer(xInputList, yInputList));

        tvMedianTitle.setText("Median: " + GroupedDataProvider.median(xInputList, yInputList));
        tvMedianStep.setText(GroupedDataProvider.medianStep(xInput, yInput, xInputList, yInputList));
        tvMedianAnswer.setText(GroupedDataProvider.medianAnswer(xInputList, yInputList));

        tvModeTitle.setText("Mode: " + GroupedDataProvider.mode(xInputList, yInputList));
        tvModeStep.setText(GroupedDataProvider.modeStep(xInput, yInput, xInputList, yInputList));
        tvModeAnswer.setText(GroupedDataProvider.modeAnswer(xInputList, yInputList));
    }
    // ---------------------------------------------------------------------------------------------
}
