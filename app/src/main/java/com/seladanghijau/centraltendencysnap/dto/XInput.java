package com.seladanghijau.centraltendencysnap.dto;

/**
 * Created by seladanghijau on 14/9/2016.
 */
public class XInput {
    private int[] lcl, ucl;
    private double[] lcb, ucb;
    private int size;
    private double classWidth;

    // constructor
    public XInput() {
        lcl = null;
        ucl = null;

        setLCB();
        setUCB();
        setClassWidth();
    }

    public XInput(int[] lcl, int[] ucl) {
        this.lcl = lcl;
        this.ucl = ucl;

        setLCB();
        setUCB();
        setClassWidth();
    }

    // getter
    public int getLCL(int x) { return lcl[x]; }
    public int getUCL(int x) { return ucl[x]; }
    public double getLCB(int x) { return lcb[x]; }
    public double getUCB(int x) { return ucb[x]; }
    public int getSize() { return ucl.length; }
    public double getClassWidth() { return  classWidth; }

    // setter
    public void setLCL(int[] lcl) { this.lcl = lcl; }
    public void setUCL(int[] ucl) { this.ucl = ucl; }

    public void setLCB() {
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

    public void setUCB() {
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

    public void setClassWidth() {
        classWidth = ucb[0] - lcb[0];
    }
}
