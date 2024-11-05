package com.github.barteksc.pdfviewer.compressor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class DeflateFile {

    public static void compressFile(String sourceFile, String compressedFile) throws IOException {
        FileInputStream fis = new FileInputStream(sourceFile);
        FileOutputStream fos = new FileOutputStream(compressedFile);
        Deflater deflater = new Deflater();

        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            deflater.setInput(buffer, 0, bytesRead);
        }

        deflater.finish();
        byte[] compressedData = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(compressedData);
            fos.write(compressedData, 0, count);
        }

        fis.close();
        fos.close();
        deflater.end();
    }

    public static void decompressFile(String compressedFile, String decompressedFile) throws IOException, DataFormatException {
        FileInputStream fis = new FileInputStream(compressedFile);
        FileOutputStream fos = new FileOutputStream(decompressedFile);
        Inflater inflater = new Inflater();

        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            inflater.setInput(buffer, 0, bytesRead);
        }

        byte[] decompressedData = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(decompressedData);
            fos.write(decompressedData, 0, count);
        }

        fis.close();
        fos.close();
        inflater.end();
    }
}
