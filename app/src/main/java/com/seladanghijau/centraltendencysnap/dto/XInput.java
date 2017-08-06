package com.seladanghijau.centraltendencysnap.dto;

/**
 * Created by seladanghijau on 14/9/2016.
 */
public class XInput {
    private int[] lcl, ucl;
    private double[] lcb, ucb, midpoint;
    private int size;
    private double classWidth;

    // constructor
    public XInput() {
        lcl = null;
        ucl = null;
        size = 0;

        setLCB();
        setUCB();
        setClassWidth();
    }

    public XInput(int[] lcl, int[] ucl) {
        this.lcl = lcl;
        this.ucl = ucl;
        this.size = ucl.length;

        setLCB();
        setUCB();
        setClassWidth();
        setMidpoint();
    }

    // getter
    public int getLCL(int x) { return lcl[x]; }
    public int getUCL(int x) { return ucl[x]; }
    public double getLCB(int x) { return lcb[x]; }
    public double getUCB(int x) { return ucb[x]; }
    public int getSize() { return size; }
    public double getClassWidth() { return  classWidth; }
    public double[] getMidpoint() { return midpoint; }

    // setter
    private void setLCL(int[] lcl) { this.lcl = lcl; }
    private void setUCL(int[] ucl) { this.ucl = ucl; }

    private void setLCB() {
        lcb = new double[lcl.length];

        if(ucl[0] != lcl[1]) {
            for(int x=0 ; x<lcl.length ; x++) {
                lcb[x] = lcl[x] - 0.5;
            }
        } else {
            for (int x = 0; x < lcl.length; x++) {
                lcb[x] = (double) ucl[x];
            }
        }
    }

    private void setUCB() {
        ucb = new double[ucl.length];

        if(ucl[0] != lcl[1]) {
            for(int x=0 ; x<ucl.length ; x++) {
                ucb[x] = ucl[x] + 0.5;
            }
        } else {
            for(int x=0 ; x<lcl.length ; x++) {
                ucb[x] = (double) ucl[x];
            }
        }
    }

    private void setClassWidth() {
        classWidth = ucb[0] - lcb[0];
    }

    private void setMidpoint() {
        midpoint = new double[size];

        for(int x=0 ; x<size ; x++)
            midpoint[x] = (Double.parseDouble("" + ucl[x]) + Double.parseDouble("" + lcl[x])) / 2;
    }
}
