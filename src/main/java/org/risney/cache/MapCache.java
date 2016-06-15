package org.risney.cache;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface MapCache {

	MapPutResult putIfAbsent(ByteBuffer key, ByteBuffer value) throws IOException;

	MapPutResult put(ByteBuffer key, ByteBuffer value) throws IOException;

	boolean containsKey(ByteBuffer key) throws IOException;

	ByteBuffer get(ByteBuffer key) throws IOException;

	ByteBuffer remove(ByteBuffer key) throws IOException;

}