package com.seladanghijau.centraltendencysnap.provider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;
import android.widget.ListView;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seladanghijau on 15/8/2016.
 */
public class Calculator {
    private Context context;
    private int[] ungroupData;

    public Calculator(Context context, int[] ungroupData) {
        this.context = context;
        this.ungroupData = ungroupData;
    }

    // operations ----------------------------------------------------------------------------------
    public double mean() {
        int[] tempData;
        int sum;

        sum = 0;
        tempData = ungroupData.clone();
        for(int x=0 ; x<tempData.length ; x++) {
            sum += tempData[x];
        }

        return (sum / tempData.length);
    }

    public int median() {
        return convertToInt(Math.ceil(new DescriptiveStatistics(convertToDoubleArray(ungroupData)).getPercentile(50.0)));
    }

    public List<Integer> mode() {
        List<Integer> maxValues;
        int maxCount;

        maxCount = 0;
        maxValues = new ArrayList<>();
        for (int i = 0; i < ungroupData.length; ++i) {
            int count;

            count = 0;
            for (int j = 0; j < ungroupData.length; ++j) {
                if (ungroupData[j] == ungroupData[i])
                    ++count;
            }

            if (count == maxCount)
                maxValues.add(ungroupData[i]);

            if (count > maxCount) {
                maxCount = count;
                maxValues.clear();
                maxValues.add(ungroupData[i]);
            }
        }

        return maxValues;
    }

    public double standardDeviation() {
        return new StandardDeviation().evaluate(convertToDoubleArray(ungroupData));
    }

    public double variance() {
        return Math.pow(standardDeviation(), 2);
    }
    // ---------------------------------------------------------------------------------------------

    // step methods --------------------------------------------------------------------------------
    public String meanStep() {
        String info, init, step1;
        String xList, sumXList;
        int n, sumX;
        int[] x;

        sumX = 0;
        xList = "";
        sumXList = "";
        x = ungroupData;
        n = ungroupData.length;
        for(int a=0 ; a<n ; a++) {
            sumX += x[a];

            if(a == n-1) {
                xList += x[a];
                sumXList += x[a];
            } else {
                xList += x[a] + ", ";
                sumXList += x[a] + " + ";
            }
        }

        info = "\tMean = " + Html.fromHtml("&#8721;") + "x / n";
        init = "\tx = " + xList + "\n" +
                "\t" + Html.fromHtml("&#8721;") + "x = " + sumXList + "\n" +
                "\t\t  = " + sumX + "\n" +
                "\tn = " + n;
        step1 = "\t" + Html.fromHtml("&#8721;") + "x / n\n" +
                "\t\t= " + sumX + " / " + n + "\n" +
                "\t\t= " + ((double) sumX / n);

        return info + "\n\n" + init + "\n\n" + step1;
    }

    public String medianStep() {
        String init, step1, step2, step3, step4;
        String xList, sortedXList;
        int varN;
        int[] varX, sortedX;

        xList = "";
        sortedXList = "";
        varN = ungroupData.length;
        varX = ungroupData.clone();
        sortedX = bubbleSortAscending();
        for(int x=0 ; x<varN ; x++) {
            if(x == varN-1) {
                xList += "" + varX[x];
                sortedXList += "" + sortedX[x];
            } else {
                xList += "" + varX[x] + ", ";
                sortedXList += "" + sortedX[x] + ", ";
            }
        }

        init = "\tx = " + xList + "\n" +
                "\tn = " + varN;
        step1 = "\tArrange values in ascending order :\n\n" +
                "\t\t x = " + sortedXList;
        if(varN % 2 != 0) {
            step2 = "\tFind median position:\n\n" +
                    "\t\tn is odd:\n" +
                    "\t\t= (n + 1) / 2";
            step3 = "\t\t= (" + varN + " + 1) / 2";
        } else {
            step2 = "\tFind median position:\n\n" +
                    "\t\tn is even:\n" +
                    "\t\t= (n/2 + 1)";
            step3 = "\t\t= (" + varN + "/2 +1)\n" +
                    "\t\t= " + ((varN / 2) + 1);
        }
        step4 = "\tMedian is at position " + ((varN / 2) + 1);


        return init + "\n\n" + step1 + "\n\n" + step2 + "\n" + step3 + "\n\n" + step4;
    }

    public String modeStep() {
        String info, init;
        String xList;
        int[] varX;

        xList = "";
        varX = ungroupData.clone();
        for(int x=0 ; x<varX.length ; x++) {
            if(x == varX.length-1)
                xList += varX[x];
            else
                xList += varX[x] + ", ";
        }

        info = "\tMode = Value that most frequently occurs in the given data.";
        init = "\tx = " + xList;

        return info + "\n\n" + init;
    }

    public String standardDeviationStep() {
        String formula, xList, sumXList, sumX2List;
        String info, init, step1, step2;
        int varN, sumX, sumX2, sum2X;
        int[] varX, varX2;

        sumX = 0;
        sumX2 = 0;
        sum2X = 0;
        varX = ungroupData.clone();
        varN = varX.length;
        varX2 = new int[varN];
        for(int x=0 ; x<varN ; x++) {
            varX2[x] = varX[x] * varX[x];
            sumX += varX[x];
            sumX2 += varX2[x];
        }
        sum2X = sumX2 * sumX2;

        xList = "";
        sumXList = "";
        sumX2List = "";
        for(int x=0 ; x<varN ; x++) {
            if(x == varN-1) {
                xList += "" + varX[x];
                sumXList += "" + varX[x];
                sumX2List += "" + varX2[x];
            } else {
                xList += "" + varX[x] + ", ";
                sumXList += "" + varX[x] + " + ";
                sumX2List += "" + varX2[x] + " + ";
            }
        }

        formula = "S = " + Html.fromHtml("&radic;") +
                "[" +
                    "(1 / (n - 1))" +
                    " * " +
                    "(" +
                        "(" +
                        Html.fromHtml("&sum;") + "x^2" +
                        " - " +
                        "(" +
                            "(" + Html.fromHtml("&sum;") + "x)^2" +
                        ") / n" +
                    ")" +
                "]";

        info = "\t" + formula;
        init = "\tx = " + xList + "\n" +
                "\tn = " + varN + "\n" +
                "\t" + Html.fromHtml("&sum;") + "x = " + sumXList + "\n" +
                "\t\t  = " + sumX + "\n" +
                "\t" + Html.fromHtml("&sum;") + "x^2 = " + sumX2List + "\n" +
                "\t\t\t   = " + sumX2 + "\n" +
                "\t(" + Html.fromHtml("&sum;") + "x)^2 = (" + sumXList + ")^2\n" +
                "\t\t\t\t  = " + sum2X;
        step1 = "\tS = " + Html.fromHtml("&radic;") +
                "[" +
                    "(1 / (" + varN+ " - 1))" +
                    " * " +
                    "(" +
                        "(" +
                            sumX2 +
                            " - " +
                            "(" +
                                sum2X +
                            ") / " + varN +
                    ")" +
                "]";
        step2 = "\tS = " + standardDeviation();

        return info + "\n\n" + init + "\n\n" + step1 + "\n" + step2;
    }

    public String varianceStep() {
        String info, init, answer;
        double S;

        S = standardDeviation();

        info = "\tS = Standard Deviation\n" +
                "\tVariance = S^2";
        init = "\tS = " + S + "\n" +
                "\tS^2 = " + Math.pow(S, 2);

        return info + "\n\n" + init;
    }

    public String meanAnswer() {
        String answer;

        answer = "\t" + Html.fromHtml("&there4;") + " Mean = " + mean();

        return answer;
    }

    public String medianAnswer() {
        String answer;

        answer = "\t" + Html.fromHtml("&there4;") + " Median = " + median();

        return answer;
    }

    public String modeAnswer() {
        String modeList, answer;

        modeList = "";
        for(int x=0 ; x<mode().size() ; x++) {
            Integer varMode = mode().get(x);

            if(x == mode().size()-1)
                modeList += varMode;
            else
                modeList += varMode + ", ";
        }
        answer = "\t" + Html.fromHtml("&there4;") + " Mode = " + modeList;

        return answer;
    }

    public String standardDeviationAnswer() {
        String answer;

        answer = "\t" + Html.fromHtml("&there4;") + " S = " + standardDeviation();

        return answer;
    }

    public String varianceAnswer() {
        String answer;
        double S2;

        S2 = variance();
        answer = "\t" + Html.fromHtml("&there4;") + " Variance = " + S2;

        return answer;
    }
    // ---------------------------------------------------------------------------------------------

    // util methods --------------------------------------------------------------------------------
    public int[] bubbleSortAscending() {
        int[] tempData;

        tempData = ungroupData.clone();
        for (int i=(tempData.length - 1) ; i>=0 ; i--) {
            for (int j=1 ; j<=i ; j++) {
                if (tempData[j-1] > tempData[j]) {
                    int temp = tempData[j-1];

                    tempData[j-1] = tempData[j];
                    tempData[j] = temp;
                }
            }
        }

        return tempData;
    }

    public double convertToDouble(int x) {
        return ((double) x);
    }

    public int convertToInt(double x) {
        return ((int) x);
    }

    public double[] convertToDoubleArray(int[] no) {
        double[] tempData;

        tempData = new double[no.length];
        for(int x=0 ; x<no.length ; x++) {
            tempData[x] = (double) no[x];
        }

        return tempData;
    }

    public int[] convertToIntArray(double[] no) {
        int[] tempData;

        tempData = new int[no.length];
        for(int x=0 ; x<no.length ; x++) {
            tempData[x] = (int) no[x];
        }

        return tempData;
    }
    // ---------------------------------------------------------------------------------------------
}
