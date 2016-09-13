package com.seladanghijau.centraltendencysnap.provider;

import com.seladanghijau.centraltendencysnap.dto.XInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seladanghijau on 14/9/2016.
 */
public class GroupedDataProvider {
    public static List<XInput> extractXInput(String xInput) {
        String tempXInput;
        String[] splitX;
        XInput[] xInputs;
        List<XInput> result;

        result = new ArrayList<>();
        tempXInput = checkXInput(xInput); // check the raw string
        xInputs = extractXUclLcl(tempXInput);

        for(int x=0 ; x<xInput.length() ; x++) {
            result.add(xInputs[x]);
        }

        return result; // return array of integer for x-values
    }

    public static int[] extractYInput(String yInput) {
        String tempYInput;
        String[] splitY;
        int[] result;

        result = null;
        tempYInput = checkYInput(yInput); // check the raw string
        splitY = tempYInput.split(", ");
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

    public static XInput[] extractXUclLcl(String xInput) {
        XInput[] result;
        String[] uclLcl;

        uclLcl = xInput.split(", ");
        result = new XInput[uclLcl.length];
        for(int x=0 ; x<uclLcl.length ; x++) {
            String[] uclLclString;

            uclLclString = uclLcl[x].split("-");
            result[x].setLCL(Integer.parseInt(uclLclString[0]));
            result[x].setUCL(Integer.parseInt(uclLclString[1]));
        }

        return result;
    }
}
