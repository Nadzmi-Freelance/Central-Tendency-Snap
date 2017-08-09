package com.seladanghijau.centraltendencysnap.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.seladanghijau.centraltendencysnap.R;
import com.seladanghijau.centraltendencysnap.provider.CalculatorUngroupData;

import java.util.List;

public class ComputeUngroupDataMT extends AppCompatActivity {
    // views
    TextView tvMean, tvMedian, tvMode;
    TextView tvMeanStep, tvMedianStep, tvModeStep;
    TextView tvMeanAnswer, tvMedianAnswer, tvModeAnswer;

    // vars
    int[] ungroupData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_ungroup_data_mt);

        initViews();
        initVars();
    }

    // initialize ----------------------------------------------------------------------------------
    private void initViews() {
        tvMean = (TextView) findViewById(R.id.tvMean);
        tvMedian = (TextView) findViewById(R.id.tvMedian);
        tvMode = (TextView) findViewById(R.id.tvMode);

        tvMeanStep = (TextView) findViewById(R.id.tvMeanStep);
        tvMedianStep = (TextView) findViewById(R.id.tvMedianStep);
        tvModeStep = (TextView) findViewById(R.id.tvModeStep);

        tvMeanAnswer = (TextView) findViewById(R.id.tvMeanAnswer);
        tvMedianAnswer = (TextView) findViewById(R.id.tvMedianAnswer);
        tvModeAnswer = (TextView) findViewById(R.id.tvModeAnswer);
    }

    @SuppressLint("SetTextI18n")
    private void initVars() {
        ungroupData = getIntent().getIntArrayExtra("ungroup-data");

        double mean = CalculatorUngroupData.mean(ungroupData);
        int median = CalculatorUngroupData.median(ungroupData);
        List<Integer> mode = CalculatorUngroupData.mode(ungroupData);

        tvMean.setText("Mean: " + mean);
        tvMedian.setText("Median: " + median);
        if(mode != null) {
            tvMode.setText("Mode: ");

            for(int x=0 ; x<mode.size() ; x++) {
                if(x == mode.size()-1)
                    tvMode.append("" + mode.get(x));
                else
                    tvMode.append(mode.get(x) + ", ");
            }
        } else
            tvMode.setText("Mode: No mode");

        tvMeanStep.setText(CalculatorUngroupData.meanStep(ungroupData));
        tvMedianStep.setText(CalculatorUngroupData.medianStep(ungroupData));
        tvModeStep.setText(CalculatorUngroupData.modeStep(ungroupData));

        tvMeanAnswer.setText(CalculatorUngroupData.meanAnswer(ungroupData));
        tvMedianAnswer.setText(CalculatorUngroupData.medianAnswer(ungroupData));
        tvModeAnswer.setText(CalculatorUngroupData.modeAnswer(ungroupData));
    }
    // ---------------------------------------------------------------------------------------------

    // listener ------------------------------------------------------------------------------------
    public void onBackPressed() {
        finish();
    }
    // ---------------------------------------------------------------------------------------------
}
