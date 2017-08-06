package com.seladanghijau.centraltendencysnap.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seladanghijau.centraltendencysnap.R;
import com.seladanghijau.centraltendencysnap.dto.XInput;
import com.seladanghijau.centraltendencysnap.provider.CalculatorGroupedData;
import com.seladanghijau.centraltendencysnap.provider.CalculatorUngroupData;

public class ComputeGroupDataMV extends AppCompatActivity implements View.OnClickListener {
    // views var
    TextView tvStandardDeviationTitle, tvStandardDeviationStep, tvStandardDeviationAnswer;
    TextView tvVarianceTitle, tvVarianceStep, tvVarianceAnswer;
    TextView tvCVTitle, tvCVStep, tvCVAnswer, tvCV2Title, tvCV2Step, tvCV2Answer, tvCVCompareConc;
    Button btnSet2, btnCompareCV;
    LinearLayout llCVSet2, llInputCVSet2;
    EditText set2Mean, set2StandardDev, set2Variance;

    // standard vars
    double cvSet1, cvSet2;
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
        tvCV2Title = (TextView) findViewById(R.id.tvCV2Title);
        tvCV2Step = (TextView) findViewById(R.id.tvCV2Step);
        tvCV2Answer = (TextView) findViewById(R.id.tvCV2Answer);
        tvCVCompareConc = (TextView) findViewById(R.id.tvCVCompareConc);
        btnSet2 = (Button) findViewById(R.id.btnSet2);
        btnCompareCV = (Button) findViewById(R.id.btnCompareCV);
        llCVSet2 = (LinearLayout) findViewById(R.id.llCVSet2);
        llInputCVSet2 = (LinearLayout) findViewById(R.id.llInputCVSet2);
        set2Mean = (EditText) findViewById(R.id.set2Mean);
        set2StandardDev = (EditText) findViewById(R.id.set2StandardDev);
        set2Variance = (EditText) findViewById(R.id.set2Variance);
    }

    private void initVars() {
        xInput = getIntent().getStringExtra("xInputList");
        yInput = getIntent().getStringExtra("yInputList");

        xInputList = CalculatorGroupedData.extractXInput(xInput);
        yInputList = CalculatorGroupedData.extractYInput(yInput);
    }

    private void initListener() {
        btnSet2.setOnClickListener(this);
        btnCompareCV.setOnClickListener(this);
    }
    // ---------------------------------------------------------------------------------------------

    // process -------------------------------------------------------------------------------------
    private void mainProcess() {
        llCVSet2.setVisibility(View.GONE);
        llInputCVSet2.setVisibility(View.GONE);

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
                if(btnSet2.isShown())
                    btnSet2.setVisibility(View.GONE);
                if(!llInputCVSet2.isShown())
                    llInputCVSet2.setVisibility(View.VISIBLE);
                break;
            case R.id.btnCompareCV:
                double mean2, stdev2, variance2;

                if(!llCVSet2.isShown())
                    llCVSet2.setVisibility(View.VISIBLE);
                if(llInputCVSet2.isShown())
                    llInputCVSet2.setVisibility(View.GONE);

                // get mean, stdev & var
                mean2 = Double.parseDouble("" + set2Mean.getText());
                stdev2 = Double.parseDouble("" + set2StandardDev.getText());
                variance2 = Double.parseDouble("" + set2Variance.getText());

                // init cvs
                cvSet1 = CalculatorGroupedData.cv(xInputList, yInputList);
                cvSet2 = 0;

                // calc cv2
                if(!("" + set2StandardDev.getText()).isEmpty())
                    cvSet2 = CalculatorGroupedData.cv(stdev2, mean2);
                else
                    cvSet2 = CalculatorGroupedData.cv(Math.sqrt(variance2), mean2);

                tvCV2Title.setText("Coefficient Variance: " + CalculatorGroupedData.cv(stdev2, mean2));
                tvCV2Step.setText(CalculatorGroupedData.cvStep(stdev2, mean2));
                tvCV2Answer.setText(CalculatorGroupedData.cvAnswer(stdev2, mean2));
                tvCVCompareConc.setText("\n" + CalculatorGroupedData.cvCompare(cvSet1, cvSet2));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(!btnSet2.isShown())
            btnSet2.setVisibility(View.VISIBLE);
        if(llCVSet2.isShown())
            llCVSet2.setVisibility(View.GONE);
        if(llInputCVSet2.isShown())
            llInputCVSet2.setVisibility(View.GONE);
    }
    // ---------------------------------------------------------------------------------------------
}
