package org.risney.cache.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ConversionUtils {

	private static final Charset STANDARD_CHARSET = Charset.forName("UTF-8");
	private static final int BYTES_PER_READ = 1000;

	public static ByteBuffer stringToByteBuffer(String msg) {
        checkNotEmpty(msg);
        
		return ByteBuffer.wrap(msg.getBytes(STANDARD_CHARSET));
	}

	public static String ByteBufferToString(ByteBuffer buffer) {
		String msg = "";
		try {
			byte[] bytes;
			if (buffer.hasArray()) {
				bytes = buffer.array();
			} else {
				bytes = new byte[buffer.remaining()];
				buffer.get(bytes);
			}
			msg = new String(bytes, STANDARD_CHARSET);
		} catch (Exception e) {

		}
		return msg;
	}

	public static ByteBuffer readToBuffer(String filename) throws IOException {
        checkNotEmpty(filename);
        
		File file = new File(filename);
		ByteBuffer bb = ByteBuffer.allocate((int) file.length());
		FileInputStream fis = new FileInputStream(filename);

		int bytesRead = 0;
		byte[] buf = new byte[BYTES_PER_READ];

		while (bytesRead != -1) {
			bb.put(buf, 0, bytesRead);
			bytesRead = fis.read(buf);
		}
		fis.close();
		return bb;
	}

    private static void checkNotEmpty(String string)
    {
        if (string == null || string.isEmpty())
        {
            throw new IllegalArgumentException("Illegal Empty String");
        }
    }
}
