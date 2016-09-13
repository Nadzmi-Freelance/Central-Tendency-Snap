package com.seladanghijau.centraltendencysnap.activity;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.seladanghijau.centraltendencysnap.R;
import com.seladanghijau.centraltendencysnap.provider.Calculator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ComputeUngroupData extends AppCompatActivity {
    // views
    TextView tvMean, tvMedian, tvMode, tvStandardDeviation, tvVariance, tvQuartile;
    TextView tvMeanStep, tvMedianStep, tvModeStep, tvStandardDeviationStep, tvVarianceStep, tvQuartileStep;
    TextView tvMeanAnswer, tvMedianAnswer, tvModeAnswer, tvStandardDeviationAnswer, tvVarianceAnswer, tvQuartileAnswer;
    ImageView ivWhiskersDiagram;

    // vars
    int[] ungroupData;
    Calculator calculator;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_ungroup_data);

        initViews();
        initVars();
    }

    // initialize ----------------------------------------------------------------------------------
    private void initViews() {
        tvMean = (TextView) findViewById(R.id.tvMean);
        tvMedian = (TextView) findViewById(R.id.tvMedian);
        tvMode = (TextView) findViewById(R.id.tvMode);
        tvStandardDeviation = (TextView) findViewById(R.id.tvStandardDeviation);
        tvVariance = (TextView) findViewById(R.id.tvVariance);
        tvQuartile = (TextView) findViewById(R.id.tvQuartile);

        tvMeanStep = (TextView) findViewById(R.id.tvMeanStep);
        tvMedianStep = (TextView) findViewById(R.id.tvMedianStep);
        tvStandardDeviationStep = (TextView) findViewById(R.id.tvStandardDeviationStep);
        tvVarianceStep = (TextView) findViewById(R.id.tvVarianceStep);
        tvModeStep = (TextView) findViewById(R.id.tvModeStep);
        tvQuartileStep = (TextView) findViewById(R.id.tvQuartileStep);

        tvMeanAnswer = (TextView) findViewById(R.id.tvMeanAnswer);
        tvMedianAnswer = (TextView) findViewById(R.id.tvMedianAnswer);
        tvModeAnswer = (TextView) findViewById(R.id.tvModeAnswer);
        tvStandardDeviationAnswer = (TextView) findViewById(R.id.tvStandardDeviationAnswer);
        tvVarianceAnswer = (TextView) findViewById(R.id.tvVarianceAnswer);
        tvQuartileAnswer = (TextView) findViewById(R.id.tvQuartileAnswer);
    }

    private void initVars() {
        ungroupData = getIntent().getIntArrayExtra("ungroup-data");
        calculator = new Calculator(this, ungroupData);

        double mean = calculator.mean();
        int median = calculator.median();
        List<Integer> mode = calculator.mode();
        double standardDeviation = calculator.standardDeviation();
        double variance = calculator.variance();

        tvMean.setText("Mean: " + mean);
        tvMedian.setText("Median: " + median);
        tvStandardDeviation.setText("Standard Deviation: " + standardDeviation);
        tvVariance.setText("Variance: " + variance);
        tvQuartile.setText("Quartile :" +
                "\n\tFirst Quartile (Q1): " + calculator.firstQuartile() +
                "\n\tThird Quartile (Q3): " + calculator.thirdQuartile());
        tvMode.setText("Mode: ");
        for(int x=0 ; x<mode.size() ; x++) {
            if(x == mode.size()-1)
                tvMode.append("" + mode.get(x));
            else
                tvMode.append(mode.get(x) + ", ");
        }

        tvMeanStep.setText(calculator.meanStep());
        tvMedianStep.setText(calculator.medianStep());
        tvModeStep.setText(calculator.modeStep());
        tvStandardDeviationStep.setText(calculator.standardDeviationStep());
        tvVarianceStep.setText(calculator.varianceStep());
        tvQuartileStep.setText("\n\tQ1:\n" + calculator.firstQuartileStep() + "\n\n\tQ3:\n" + calculator.thirdQuartileStep());

        tvMeanAnswer.setText(calculator.meanAnswer());
        tvMedianAnswer.setText(calculator.medianAnswer());
        tvModeAnswer.setText(calculator.modeAnswer());
        tvStandardDeviationAnswer.setText(calculator.standardDeviationAnswer());
        tvVarianceAnswer.setText(calculator.varianceAnswer());
        tvQuartileAnswer.setText(calculator.firstQuartileAnswer() + "\n" + calculator.thirdQuartileAnswer());
    }
    // ---------------------------------------------------------------------------------------------

    // listener ------------------------------------------------------------------------------------
    public void onBackPressed() {
        finish();
    }
    // ---------------------------------------------------------------------------------------------
}
