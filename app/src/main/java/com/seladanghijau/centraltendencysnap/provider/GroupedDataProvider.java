package com.seladanghijau.centraltendencysnap.provider;

import android.text.Html;

import com.seladanghijau.centraltendencysnap.dto.XInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seladanghijau on 14/9/2016.
 */
public class GroupedDataProvider {
    // calculate methods ---------------------------------------------------------------------------
    public static double mean(XInput xInput, int[] yInput) {
        int[] midpoint;
        int sumFX, totalF;
        double result;

        sumFX = 0;
        totalF = 0;
        midpoint = new int[xInput.getSize()];
        for(int x=0 ; x<xInput.getSize() ; x++) {
            midpoint[x] = (xInput.getLCL(x) + xInput.getUCL(x) ) / 2;
            sumFX += midpoint[x] * yInput[x];
            totalF += yInput[x];
        }

        result = (double) sumFX / totalF;

        return result;
    }

    // FIXME: 14/9/2016 - implement provider utk median & mode
    public static double median(XInput xInput, int[] yInput) {
        int n, medianClassIndex;
        double medianPos, c, medLCB;
        int nBef, medF;
        double result;

        // find median class
        n = 0;
        for(int x=0 ; x<yInput.length ; x++) {
            n += yInput[x];
        }

        double tempMedF = 0;
        medianPos = (double) n / 2;
        medianClassIndex = 0;
        for(int x=0 ; x<yInput.length ; x++) {
            tempMedF += yInput[x];
            if(tempMedF >= medianPos) {
                medianClassIndex = x;
                break;
            }
        }

        // get lcl median class
        medLCB = xInput.getLCB(medianClassIndex);

        // find cumulative frequency before median class
        nBef = 0; // cumulative f before median class
        for(int x=0 ; x<medianClassIndex ; x++) {
            nBef += yInput[x];
        }

        // get frequency of median class
        medF = yInput[medianClassIndex];

        // calculate class size
        c = xInput.getClassWidth();

        // find median based on median class
        result = medLCB + (((n / 2.0) - nBef) / medF) * c;

        return result;
    }

    public static double mode(XInput xInput, int[] yInput) {
        double del1, del2, result, c, lMode;
        int highestF, highestFIndex;

        // find highest frequency to obtain modal class
        highestF = 0;
        highestFIndex = 0;
        for(int x=0 ; x<yInput.length ; x++) {
            if(yInput[x] > highestF) {
                highestF = yInput[x];
                highestFIndex = x;
            }
        }

        // find lower class boundary
        lMode = xInput.getLCB(highestFIndex);

        // find delta 1 (diff mode f & f before it)
        del1 = yInput[highestFIndex] - yInput[highestFIndex-1];

        // find delta 2 (diff between mode f & f after it)
        del2 = yInput[highestFIndex] - yInput[highestFIndex+1];

        // find class size
        c = xInput.getClassWidth();

        // calculate
        result = lMode + ((del1 / (del1 + del2)) * c);

        return result;
    }
    // ---------------------------------------------------------------------------------------------

    // steps methods -------------------------------------------------------------------------------
    public static String meanStep(String xList, String yList, XInput xInput, int[] yInput) {
        String info, init, step1, step2;
        String totalFrequency, midPointXList, sumFXList, totalFXList;
        int[] midpointX, sumFXArray;
        int n, sumFX;

        n = 0;
        sumFX = 0;
        midpointX = new int[xInput.getSize()];
        sumFXArray = new int[xInput.getSize()];
        totalFrequency = "";
        midPointXList = "";
        sumFXList = "";
        totalFXList = "";
        for(int x=0 ; x<yInput.length ; x++) {
            n += yInput[x];
            midpointX[x] = (xInput.getLCL(x) + xInput.getUCL(x)) / 2;
            sumFXArray[x] += midpointX[x] * yInput[x];
            sumFX += sumFXArray[x];

            if (x == (yInput.length - 1)) {
                totalFrequency += yInput[x];
                midPointXList += midpointX[x];
                sumFXList += sumFXArray[x];
                totalFXList += "(" + midpointX[x] + " X " + yInput[x] + ")";
            } else {
                totalFrequency += yInput[x] + " + ";
                midPointXList += midpointX[x] + ", ";
                sumFXList += sumFXArray[x] + " + ";
                totalFXList += "(" + midpointX[x] + " X " + yInput[x] + ") + ";
            }
        }

        info = "Mean = " + Html.fromHtml("&sum;") + "fx / n\n" +
                "\tf = frequency (y)\n" +
                "\tx = midpoint values (x)\n" +
                "\t\t= (LCLi + UCLi) / 2; where i is the number of class\n" +
                "\tn = total frequency (" + Html.fromHtml("&sum;") + "y)";
        init = "x = " + xList + "\n" +
                "Midpoint x = " + midPointXList + "\n" +
                "f = " + yList + "\n" +
                "n = " + totalFrequency + "\n" +
                "\t= " + n;
        step1 = "Mean = " + Html.fromHtml("&sum;") + "fx / n\n" +
                "\t" + Html.fromHtml("&sum;") + "fx = " + totalFXList + "\n" +
                "\t\t= " + sumFXList + "\n" +
                "\t\t= " + sumFX;
        step2 = "\t" + Html.fromHtml("&sum;") + "fx / n = " + sumFX + " / " + n + "\n" +
                "\t\t= " + mean(xInput, yInput);

        return info + "\n\n" + init + "\n\n" + step1 + "\n\n" + step2;
    }
    // ---------------------------------------------------------------------------------------------

    //  asnwer method ------------------------------------------------------------------------------
    public static String meanAnswer(XInput xInput, int[] yInput) { return Html.fromHtml("&there4;") + " " + String.valueOf(mean(xInput, yInput)); }
    // ---------------------------------------------------------------------------------------------

    // util methods --------------------------------------------------------------------------------
    public static XInput extractXInput(String xInput) {
        String tempXInput;
        XInput xInputs;

        tempXInput = checkXInput(xInput); // check the raw string
        xInputs = extractXUclLcl(tempXInput);

        return xInputs; // return array of integer for x-values
    }

    public static int[] extractYInput(String yInput) {
        String tempYInput;
        String[] splitY;
        int[] result;

        tempYInput = checkYInput(yInput); // check the raw string
        splitY = tempYInput.split(", ");
        result = new int[splitY.length];
        for(int x=0 ; x<splitY.length ; x++) {
            result[x] = Integer.parseInt(splitY[x]);
        }

        return result; // return array of integer for y-values
    }

    public static String checkYInput(String yInput) {
        String tempYInput;
        String[] trimedYInputs;

        tempYInput = "";
        trimedYInputs = yInput.split(",");
        for(int x=0 ; x<trimedYInputs.length ; x++) {
            trimedYInputs[x] = trimedYInputs[x].trim();

            if(x == trimedYInputs.length-1)
                tempYInput += trimedYInputs[x];
            else
                tempYInput += trimedYInputs[x] + ", ";
        }

        return tempYInput;
    }

    public static String checkXInput(String xInput) {
        String tempXInput;
        String[] trimedXInputs;

        tempXInput = "";
        trimedXInputs = xInput.split(",");
        for(int x=0 ; x<trimedXInputs.length ; x++) {
            trimedXInputs[x] = trimedXInputs[x].trim();

            if(x == trimedXInputs.length-1)
                tempXInput += trimedXInputs[x];
            else
                tempXInput += trimedXInputs[x] + ", ";
        }

        return tempXInput;
    }

    public static XInput extractXUclLcl(String xInput) {
        String[] uclLcl;
        int[] lcl, ucl;

        uclLcl = xInput.split(", ");
        lcl = new int[uclLcl.length];
        ucl = new int[uclLcl.length];
        for(int x=0 ; x<uclLcl.length ; x++) {
            String[] uclLclString;

            uclLclString = uclLcl[x].split("-");
            lcl[x] = Integer.parseInt(uclLclString[0]);
            ucl[x] = Integer.parseInt(uclLclString[1]);
        }

        return new XInput(lcl, ucl);
    }
    // ---------------------------------------------------------------------------------------------
}
