/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.model;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Wallison
 */
@SuppressWarnings("restriction")
public class FileManipulation {
    
    public static String fileToString(File file) {
        String result = null;
        DataInputStream in = null;
        try {
            File f = file;
            byte[] buffer = new byte[(int) f.length()];
            in = new DataInputStream(new FileInputStream(f));
            in.readFully(buffer);
            result = new String(buffer);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException("IO problem in fileToString", e);
        }
        return result;
    }
    
    public static File getFileStream(InputStream initialStream, String ext) throws IOException{
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);
        File file = File.createTempFile("index", ext);
        OutputStream outStream = new FileOutputStream(file);
        outStream.write(buffer);
        outStream.close();
        return file;
    }
    

	public static InputStream getStreamFromURL(String uri) throws MalformedURLException, IOException{
		BASE64Encoder enc = new sun.misc.BASE64Encoder();
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("Request-Method", "GET");
        connection.addRequestProperty("Authorization", "Basic " + enc.encode("user:password".getBytes()));
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.connect();
        connection.disconnect();
        return connection.getInputStream();
    }
    
    public static byte[] getBytes(File file) throws FileNotFoundException, IOException {
        byte[] bytesArray = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray);
        fis.close();
        return bytesArray;
    } 
    
    public static byte[] getByte(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                }
            } catch (IOException ex) {
               ex.printStackTrace();
            }
            byte[] bytes = bos.toByteArray();

            return  bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
        e.printStackTrace();}
        return null;
    }

    
}