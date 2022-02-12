package com.example.simpledatabase;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class AppendingObjectOutputStream extends ObjectOutputStream {
    // source: https://stackoverflow.com/a/1195078/8166854
    // answered Jul 28 2009 at 15:58 by Andreas Dolk
    // just for appending an existing OutputStream
    public AppendingObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        // do not write a header, but reset:
        // this line added after another question
        // showed a problem with the original
        reset();
    }

}
