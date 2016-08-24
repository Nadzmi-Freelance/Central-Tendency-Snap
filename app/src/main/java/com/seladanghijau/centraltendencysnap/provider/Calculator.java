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
        String info, init, step1, answer;
        String xList;
        int n, sumX;
        int[] x;

        sumX = 0;
        xList = "";
        x = ungroupData;
        n = ungroupData.length;
        for(int a=0 ; a<n ; a++) {
            sumX += x[a];

            if(a == n-1)
                xList += x[a];
            else
                xList += x[a] + ", ";
        }

        info = "\t\tMean = " + Html.fromHtml("&#8721;") + "x / n";
        init = "\t\tx = " + xList + "\n\t" +
                "\t\t" + Html.fromHtml("&#8721;") + "x = " + sumX + "\n\t" +
                "\t\tn = " + n;
        step1 = "\t\t" + Html.fromHtml("&#8721;") + "x / n\n" +
                "\t\t\t= " + sumX + " / " + n + "\n" +
                "\t\t\t= " + ((double) sumX / n);
        answer = "\t" + Html.fromHtml("&there4;") + " Mean = " + mean();

        return info + "\n\n" + init + "\n\n" + step1 + "\n\n" + answer;
    }

    public String medianStep() {
        String init, step1, step2, step3, step4, answer;
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

        init = "\t\tx = " + xList + "\n" +
                "\t\tn = " + varN;
        step1 = "\t\tArrange values in ascending order :\n\n" +
                "\t\t\t x = " + sortedXList;
        if(varN % 2 != 0) {
            step2 = "\t\tFind median position:\n\n" +
                    "\t\t\tn is odd:\n" +
                    "\t\t\t= (n + 1) / 2";
            step3 = "\t\t\t= (" + varN + " + 1) / 2";
        } else {
            step2 = "\t\tFind median position:\n\n" +
                    "\t\t\tn is even:\n" +
                    "\t\t\t= (n/2 + 1)";
            step3 = "\t\t\t= (" + varN + "/2 +1)\n" +
                    "\t\t\t= " + ((varN / 2) + 1);
        }
        step4 = "\t\tMedian is at position " + ((varN / 2) + 1);
        answer = "\t" + Html.fromHtml("&there4;") + " Median = " + median();

        return init + "\n\n" + step1 + "\n\n" + step2 + "\n" + step3 + "\n\n" + step4 + "\n\n" + answer;
    }

    public String modeStep() {
        String info, init, answer;
        String modeList, xList;
        int[] varX;

        xList = "";
        modeList = "";
        varX = ungroupData.clone();
        for(int x=0 ; x<varX.length ; x++) {
            if(x == varX.length-1)
                xList += varX[x];
            else
                xList += varX[x] + ", ";
        }
        for(int x=0 ; x<mode().size() ; x++) {
            Integer varMode = mode().get(x);

            if(x == mode().size()-1)
                modeList += varMode;
            else
                modeList += varMode + ", ";
        }

        info = "\t\tMode = Value that most frequently occurs in the given data.";
        init = "\t\tx = " + xList;
        answer = "\t" + Html.fromHtml("&there4;") + "Mode = " + modeList;

        return info + "\n\n" + init + "\n\n" + answer;
    }

    public String standardDeviationStep() {
        String formula, xList;
        String info, init, step1, step2, answer;
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
        for(int x=0 ; x<varN ; x++) {
            if(x == varN-1)
                xList += "" + varX[x];
            else
                xList += "" + varX[x] + ", ";
        }

        formula = "S = " + Html.fromHtml("&radic;") +
                "[" +
                    "(1 / (n - 1))" +
                    " * " +
                    "(" +
                        "(" +
                        Html.fromHtml("&sum;") + "x2" +
                        " - " +
                        "(" +
                            "(" + Html.fromHtml("&sum;") + "x)2" +
                        ") / n" +
                    ")" +
                "]";

        info = "\t\t" + formula;
        init = "\t\tx = " + xList + "\n" +
                "\t\tn = " + varN + "\n" +
                "\t\t" + Html.fromHtml("&sum;") + "x = " + sumX + "\n" +
                "\t\t" + Html.fromHtml("&sum;") + "x2 = " + sumX2 + "\n" +
                "\t\t(" + Html.fromHtml("&sum;") + "x)2 = " + sum2X;
        step1 = "\t\tS = " + Html.fromHtml("&radic;") +
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
        step2 = "\t\tS = " + standardDeviation();
        answer = "\t" + Html.fromHtml("&there4;") + "S = " + standardDeviation();

        return info + "\n\n" + init + "\n\n" + step1 + "\n" + step2 + "\n\n" + answer;
    }

    public String varianceStep() {
        String info, init, answer;
        double S, S2;

        S = standardDeviation();
        S2 = variance();

        info = "\t\tS = Standard Deviation\n" +
                "\t\tVariance = S X S";
        init = "\t\tS = " + S + "\n" +
                "\t\tS X S = " + Math.pow(S, 2);
        answer = "\t" + Html.fromHtml("&there4;") + " Variance = " + S2;

        return info + "\n\n" + init + "\n\n" + answer;
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
