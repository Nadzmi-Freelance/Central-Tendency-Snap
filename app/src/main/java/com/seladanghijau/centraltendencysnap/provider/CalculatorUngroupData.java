package com.seladanghijau.centraltendencysnap.provider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;
import android.widget.ListView;

import org.apache.commons.math3.distribution.IntegerDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class CalculatorUngroupData {
    // operations ----------------------------------------------------------------------------------
    public static double mean(int[] ungroupData) {
        int[] tempData;
        int sum;

        sum = 0;
        tempData = ungroupData.clone();
        for(int x=0 ; x<tempData.length ; x++) {
            sum += tempData[x];
        }

        return (sum / tempData.length);
    }

    public static int median(int[] ungroupData) {
        return convertToInt(Math.ceil(new DescriptiveStatistics(convertToDoubleArray(ungroupData)).getPercentile(50.0)));
    }

    public static List<Integer> mode(int[] ungroupData) {
        List<Integer> maxValues;
        int maxCount;

        maxCount = 0;
        maxValues = new ArrayList<>();
        for (int i = 0; i<ungroupData.length; ++i) {
            int count;

            count = 0;
            for (int j = 0; j<ungroupData.length; ++j) {
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

        for(int x=0 ; x<maxValues.size() ; x++) {
            int count;

            count = 0;
            for(int y=1 ; y<maxValues.size() ; y++) {
                if(maxValues.get(x).equals(maxValues.get(y))) {
                    count++;

                    if(x != 0)
                        maxValues.remove(x);
                }
            }

            if(count == 0) {
                maxValues = null;
                break;
            }
        }

        return maxValues;
    }

    public static double standardDeviation(int[] ungroupData) {
        DecimalFormat df;

        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        return Double.parseDouble(df.format(new StandardDeviation().evaluate(convertToDoubleArray(ungroupData))));
    }

    public static double variance(int[] ungroupData) {
        DecimalFormat df;

        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        return Double.parseDouble(df.format(Math.pow(standardDeviation(ungroupData), 2)));
    }

    public static double cv(double stdev, double mean) {
        DecimalFormat df;

        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        return Double.parseDouble(df.format((stdev / mean) * 100));
    }
    // ---------------------------------------------------------------------------------------------

    // step methods --------------------------------------------------------------------------------
    public static String meanStep(int[] ungroupData) {
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

    public static String medianStep(int[] ungroupData) {
        String init, step1, step2, step3, step4;
        String xList, sortedXList;
        int varN;
        int[] varX, sortedX;

        xList = "";
        sortedXList = "";
        varN = ungroupData.length;
        varX = ungroupData.clone();
        sortedX = bubbleSortAscending(ungroupData);
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

    public static String modeStep(int[] ungroupData) {
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

    public static String standardDeviationStep(int[] ungroupData) {
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
        step2 = "\tS = " + standardDeviation(ungroupData);

        return info + "\n\n" + init + "\n\n" + step1 + "\n" + step2;
    }

    public static String varianceStep(int[] ungroupData) {
        String info, init;
        double S;

        S = standardDeviation(ungroupData);

        info = "\tS = Standard Deviation\n" +
                "\tVariance = S^2";
        init = "\tS = " + S + "\n" +
                "\tS^2 = " + Math.pow(S, 2);

        return info + "\n\n" + init;
    }

    public static String cvStep(double stdev, double mean) {
        String info, init, formula;
        String step1, step2;

        formula = "(S / " + Html.fromHtml("x&#772;") + ") * 100";
        info = "\tS = Standard deviation" + "\n" +
                "\t" + Html.fromHtml("x&#772;") + " = mean" + "\n" +
                "\tCoefficient Variance(CV) = " + formula;
        init = "\tCV = (" + stdev + " / " + mean + ") * 100";
        step1 = "\t = (" + (stdev / mean) + ") * 100";
        step2 = "\t = " + ((stdev / mean) * 100);

        return info + "\n\n" + init + "\n" + step1 + "\n" + step2;
    }
    // ---------------------------------------------------------------------------------------------

    // answer methods ------------------------------------------------------------------------------
    public static String meanAnswer(int[] ungroupData) {
        String answer;

        answer = "\t" + Html.fromHtml("&there4;") + " Mean = " + mean(ungroupData);

        return answer;
    }

    public static String medianAnswer(int[] ungroupData) {
        String answer;

        answer = "\t" + Html.fromHtml("&there4;") + " Median = " + median(ungroupData);

        return answer;
    }

    public static String modeAnswer(int[] ungroupData) {
        String modeList, answer;

        modeList = "";
        if(mode(ungroupData) != null) {
            for(int x=0 ; x<mode(ungroupData).size() ; x++) {
                Integer varMode = mode(ungroupData).get(x);

                if(x == mode(ungroupData).size()-1)
                    modeList += varMode;
                else
                    modeList += varMode + ", ";
            }
        } else
            modeList = "No mode";

        answer = "\t" + Html.fromHtml("&there4;") + " Mode = " + modeList;

        return answer;
    }

    public static String standardDeviationAnswer(int[] ungroupData) {
        String answer;

        answer = "\t" + Html.fromHtml("&there4;") + " S = " + standardDeviation(ungroupData);

        return answer;
    }

    public static String varianceAnswer(int[] ungroupData) {
        String answer;
        double S2;

        S2 = variance(ungroupData);
        answer = "\t" + Html.fromHtml("&there4;") + " Variance = " + S2;

        return answer;
    }

    public static String cvAnswer(double stdev, double mean) { return "\t" + Html.fromHtml("&there4;") + " " + String.valueOf(cv(stdev, mean)); }
    // ---------------------------------------------------------------------------------------------

    // util methods --------------------------------------------------------------------------------
    private static int[] bubbleSortAscending(int[] ungroupData) {
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

    private static int convertToInt(double x) {
        return ((int) x);
    }

    private static double[] convertToDoubleArray(int[] no) {
        double[] tempData;

        tempData = new double[no.length];
        for(int x=0 ; x<no.length ; x++) {
            tempData[x] = (double) no[x];
        }

        return tempData;
    }
    // ---------------------------------------------------------------------------------------------
}
