package org.risney.cache.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Utility class allows ByteBuffer to be used as an InputStream

 * @author marcrisney
 *
 */
public class ByteBufferInputStream extends InputStream {

	private int bbisInitPos;
	private int bbisLimit;
	private ByteBuffer bbisBuffer;

	public ByteBufferInputStream(ByteBuffer buffer) {
		this(buffer, buffer.limit() - buffer.position());
	}

	public ByteBufferInputStream(ByteBuffer buffer, int limit) {
		bbisBuffer = buffer;
		bbisLimit = limit;
		bbisInitPos = bbisBuffer.position();
	}

	@Override
	public int read() throws IOException {
		if (bbisBuffer.position() - bbisInitPos > bbisLimit) return -1;
		return bbisBuffer.get();
	}
}