package com.example.androidclient;

import java.io.IOException;

public class BitmapReaderWriter {

    private byte[] byteArray;

    public void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {

        System.out.println("In readObject()");

        Object object = in.readObject();

        System.out.println("object = " + object);

        int bufferLength = (Integer) object;

        System.out.println("bufferLength = " + bufferLength);

        byte[] byteArray = new byte[bufferLength];

        System.out.println("~~~~~~~~~~~~~~~~~");

        for (int i = 0; i < bufferLength; ++i) {
            byteArray[i] = (Byte) in.readObject();
        }

//        int pos = 0;
//        do {
//            int read = in.read(byteArray, pos, bufferLength - pos);
//
//            System.out.println(read);
//
//            if (read != -1) {
//                pos += read;
//            } else {
//                break;
//            }
//
//        } while (pos < bufferLength);

        System.out.println("~~~~~~~~~~~~~~~~~");

        StringBuilder s = new StringBuilder();

        for(Byte b : byteArray) {
            s.append(b);
        }

        System.out.println(s);

        this.byteArray = byteArray;
    }

    public void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(byteArray.length);
        for (byte aByteArray : byteArray) {
            out.writeObject(aByteArray);
        }
//        out.writeObject(byteArray);
    }
}
