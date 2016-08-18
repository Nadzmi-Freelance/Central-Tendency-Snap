package com.seladanghijau.centraltendencysnap.provider;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seladanghijau on 15/8/2016.
 */
public class Calculator {
    private int[] ungroupData;

    public Calculator(int[] ungroupData) {
        this.ungroupData = ungroupData;
    }

    // operations ----------------------------------------------------------------------------------
    public double mean() {
        int[] tempData;
        int sum;

        sum = 0;
        tempData = ungroupData;
        for(int x=0 ; x<tempData.length ; x++) {
            sum += tempData[x];
        }

        return (sum / tempData.length);
    }

    public int median() {
        return convertToInt(Math.ceil(new DescriptiveStatistics(convertToDoubleArray(ungroupData)).getPercentile(50.0)));
    }

    public ArrayList<Integer> mode() {
        ArrayList<Integer> maxValues;
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

    // util methods --------------------------------------------------------------------------------
    public int[] bubbleSortAscending() {
        int[] tempData;

        tempData = ungroupData;
        for(int x=0 ; x<tempData.length ; x++) {
            for(int y=0 ; y<tempData.length ; y++) {
                if(y+1 > tempData.length)
                    if(tempData[y] > tempData[y-1])
                        swapNumber(y, y-1);
                else
                    if(tempData[y] > tempData[y+1])
                        swapNumber(y, y+1);
            }
        }

        return tempData;
    }

    public void swapNumber(int x, int y) {
        int temp;

        temp = ungroupData[x];
        ungroupData[x] = ungroupData[y];
        ungroupData[y] = temp;
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
