package com.seladanghijau.centraltendencysnap.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.seladanghijau.centraltendencysnap.R;
import com.seladanghijau.centraltendencysnap.provider.CalculatorUngroupData;

public class ComputeUngroupDataMV extends AppCompatActivity {
    // views
    TextView tvStandardDeviationTitle, tvStandardDeviationStep, tvStandardDeviationAnswer;
    TextView tvVarianceTitle, tvVarianceStep, tvVarianceAnswer;
    TextView tvCVTitle, tvCVStep, tvCVAnswer;

    // vars
    int[] ungroupData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_ungroup_data_mv);

        initViews();
        initVars();
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
    }

    private void initVars() {
        ungroupData = getIntent().getIntArrayExtra("ungroup-data");
    }
    // ---------------------------------------------------------------------------------------------

    // process -------------------------------------------------------------------------------------
    private void mainProcess() {
        tvStandardDeviationTitle.setText("Standard Deviation: " + CalculatorUngroupData.standardDeviation(ungroupData));
        tvStandardDeviationStep.setText(CalculatorUngroupData.standardDeviationStep(ungroupData));
        tvStandardDeviationAnswer.setText(CalculatorUngroupData.standardDeviationAnswer(ungroupData));

        tvVarianceTitle.setText("Variance: " + CalculatorUngroupData.variance(ungroupData));
        tvVarianceStep.setText(CalculatorUngroupData.varianceStep(ungroupData));
        tvVarianceAnswer.setText(CalculatorUngroupData.varianceAnswer(ungroupData));

        tvCVTitle.setText("Coefficient Variance: " + CalculatorUngroupData.cv(CalculatorUngroupData.standardDeviation(ungroupData), CalculatorUngroupData.mean(ungroupData)));
        tvCVStep.setText(CalculatorUngroupData.cvStep(CalculatorUngroupData.standardDeviation(ungroupData), CalculatorUngroupData.mean(ungroupData)));
        tvCVAnswer.setText(CalculatorUngroupData.cvAnswer(CalculatorUngroupData.standardDeviation(ungroupData), CalculatorUngroupData.mean(ungroupData)));
    }
    // ---------------------------------------------------------------------------------------------

    // listener ------------------------------------------------------------------------------------
    @Override
    public void onBackPressed() {
        finish();
    }
    // ---------------------------------------------------------------------------------------------
}
