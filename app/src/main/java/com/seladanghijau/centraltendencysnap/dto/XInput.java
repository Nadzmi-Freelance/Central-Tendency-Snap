package com.seladanghijau.centraltendencysnap.dto;

/**
 * Created by seladanghijau on 14/9/2016.
 */
public class XInput {
    private int lcl, ucl;

    // constructor
    public XInput() {
        lcl = 0;
        ucl = 0;
    }

    public XInput(int lcl, int ucl) {
        this.lcl = lcl;
        this.ucl = ucl;
    }

    // getter
    public int getLCL() { return lcl; }
    public int getUCL() { return ucl; }

    // setter
    public void setLCL(int lcl) { this.lcl = lcl; }
    public void setUCL(int ucl) { this.ucl = ucl; }
}
