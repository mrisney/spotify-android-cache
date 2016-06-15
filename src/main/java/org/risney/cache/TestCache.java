package org.risney.cache;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

//import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCache {

	
	// https://androidresearch.wordpress.com/2013/04/07/caching-objects-in-android-internal-storage/
	private static final Logger logger = LoggerFactory.getLogger(TestCache.class);
	private static final Charset STANDARD_CHARSET = Charset.forName("UTF-8");
	private static final int MAX_SIZE = 3;
	private static final EvictionPolicy EVICTION_POLICY = EvictionPolicy.LRU;
	public static void main(String[] args) {

		try {
			
			logger.debug("creating cache with max number of entries {}, with eviction policy {}",MAX_SIZE,EVICTION_POLICY);
			SimpleMapCache cache = new SimpleMapCache(MAX_SIZE, EVICTION_POLICY);

			ByteBuffer key1 = StringToByteBuffer("one");
			ByteBuffer key2 = StringToByteBuffer("two");
			ByteBuffer key3 = StringToByteBuffer("three");
			ByteBuffer key4 = StringToByteBuffer("four");

			ByteBuffer val1 = StringToByteBuffer("cat");
			ByteBuffer val2 = StringToByteBuffer("dog");
			ByteBuffer val3 = StringToByteBuffer("horse");
			ByteBuffer val4 = StringToByteBuffer("goat");

			cache.put(key1, val1);
			logger.debug("Putting key {}, value = {} into cache",ByteBufferToString(key1),ByteBufferToString(val1));
			cache.put(key2, val2);
			logger.debug("Putting key {}, value = {} into cache",ByteBufferToString(key2),ByteBufferToString(val2));
			cache.put(key3, val3);
			logger.debug("Putting key {}, value = {} into cache",ByteBufferToString(key3),ByteBufferToString(val3));
			cache.put(key4, val4);
			logger.debug("Putting key {}, value = {} into cache",ByteBufferToString(key4),ByteBufferToString(val4));
			
			cache.get(key1);
			cache.get(key1);
			cache.get(key2);
			cache.get(key2);
			cache.get(key3);
			cache.get(key4);

			ByteBuffer retrivedCacheKey1 = cache.get(key1);
			if (retrivedCacheKey1 != null)
				logger.debug("retrieved from key {} value = {}",  ByteBufferToString(key1), ByteBufferToString(retrivedCacheKey1));

			ByteBuffer retrivedCacheKey2 = cache.get(key2);
			if (retrivedCacheKey2 != null)
				logger.debug("retrieved from key {} value = {}",  ByteBufferToString(key2), ByteBufferToString(retrivedCacheKey2));
			ByteBuffer retrivedCacheKey3 = cache.get(key3);
			if (retrivedCacheKey3 != null)
				logger.debug("retrieved from key {} value = {}",  ByteBufferToString(key3), ByteBufferToString(retrivedCacheKey3));

			ByteBuffer retrivedCacheKey4 = cache.get(key4);
			if (retrivedCacheKey4 != null)
				logger.debug("retrieved from key {} value = {}",  ByteBufferToString(key4), ByteBufferToString(retrivedCacheKey4));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public static ByteBuffer StringToByteBuffer(String msg) {
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

}
