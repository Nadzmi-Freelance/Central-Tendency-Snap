package com.seladanghijau.centraltendencysnap.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.seladanghijau.centraltendencysnap.R;
import com.seladanghijau.centraltendencysnap.provider.Calculator;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Compute extends AppCompatActivity {
    // views
    TextView tvMean, tvMedian, tvMode, tvStandardDeviation, tvVariance;

    // vars
    int[] ungroupData;
    Calculator calculator;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);

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
    }

    private void initVars() {
        ungroupData = getIntent().getIntArrayExtra("ungroup-data");
        calculator = new Calculator(ungroupData);

        double mean = calculator.mean();
        int median = calculator.median();
        ArrayList<Integer> mode = calculator.mode();
        double standardDeviation = calculator.standardDeviation();
        double variance = calculator.variance();

        tvMean.setText("Mean: " + mean);
        tvMedian.setText("Median: " + median);
        tvMode.setText("Mode: ");
        tvStandardDeviation.setText("Standard Deviation: " + standardDeviation);
        tvVariance.setText("Variance: " + variance);
        for(int x=0 ; x<mode.size() ; x++) {
            if(x+1 > mode.size())
                tvMode.append("" + mode.get(x));
            else
                tvMode.append(mode.get(x) + ", ");
        }
    }
    // ---------------------------------------------------------------------------------------------
}
