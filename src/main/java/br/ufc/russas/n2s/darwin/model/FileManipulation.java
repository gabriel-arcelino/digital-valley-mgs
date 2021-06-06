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
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Wallison
 */
@SuppressWarnings("restriction")
public class FileManipulation {
	public static String fileToString(final File file) {
		String result = null;
		DataInputStream in = null;
		try {
			final File f = file;
			final byte[] buffer = new byte[(int) f.length()];
			in = new DataInputStream(new FileInputStream(f));
			in.readFully(buffer);
			result = new String(buffer);
			in.close();
		} catch (IOException e) {
			throw new RuntimeException("IO problem in fileToString", e);
		}
		return result;
	}

	public static File getFileStream(final InputStream initialStream, final String ext) throws IOException {
		final byte[] buffer = new byte[initialStream.available()];
		initialStream.read(buffer);
		final File file = File.createTempFile("index", ext);
		final OutputStream outStream = new FileOutputStream(file);
		outStream.write(buffer);
		outStream.close();
		return file;
	}

	public static InputStream getStreamFromURL(final String uri) throws MalformedURLException, IOException {
		final URL url = new URL(uri);
		final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.addRequestProperty("Request-Method", "GET");
		connection.addRequestProperty("Authorization", "Basic " + Base64.encodeBase64("user:password".getBytes()));
		connection.setDoInput(true);
		connection.setDoOutput(false);
		connection.connect();
		connection.disconnect();
		return connection.getInputStream();
	}

	public static byte[] getBytes(final File file) throws FileNotFoundException, IOException {
		final byte[] bytesArray = new byte[(int) file.length()];
		final FileInputStream fis = new FileInputStream(file);
		fis.read(bytesArray);
		fis.close();
		return bytesArray;
	}

	public static byte[] getByte(final File file) {
		try {
			final FileInputStream fis = new FileInputStream(file);
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			final byte[] buf = new byte[1024];
			try {
				int readNum;
				while ((readNum = fis.read(buf)) != -1) {
					bos.write(buf, 0, readNum);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			final byte[] bytes = bos.toByteArray();
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return null;
	}
}