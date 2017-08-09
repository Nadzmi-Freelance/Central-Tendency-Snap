package com.seladanghijau.centraltendencysnap.asynctask;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.OutputStream;


public class CopyTraineddataFile extends AsyncTask<Void, Void, Void> {
    private InputStream from;
    private OutputStream to;

    public CopyTraineddataFile(InputStream from, OutputStream to) {
        this.from = from;
        this.to = to;
    }

    protected Void doInBackground(Void... params) {
        byte[] buffer;
        int reader;

        try {
            buffer = new byte[1024];
            reader = from.read(buffer);
            while(reader != -1) {
                to.write(buffer, 0, reader);
                reader = from.read(buffer);
            }

            to.flush();
            to.close();
            from.close();
        } catch (Exception e) { e.printStackTrace(); }

        return null;
    }
}
