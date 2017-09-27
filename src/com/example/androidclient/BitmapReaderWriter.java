package com.example.androidclient;

import java.io.IOException;

public class BitmapReaderWriter {

    private byte[] byteArray;

    public void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {

        int bufferLength = in.readInt();

        byte[] byteArray = new byte[bufferLength];

        int pos = 0;
        do {
            int read = in.read(byteArray, pos, bufferLength - pos);

            if (read != -1) {
                pos += read;
            } else {
                break;
            }

        } while (pos < bufferLength);

        this.byteArray = byteArray;
    }

    public void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeInt(byteArray.length);
        out.write(byteArray);
    }
}
