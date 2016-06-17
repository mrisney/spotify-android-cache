package org.risney.cache;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.nio.ByteBuffer;

import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.risney.cache.utils.ConversionUtils;
import org.risney.cache.utils.eviction.EvictionPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageCacheTest {
	Logger logger = LoggerFactory.getLogger(getClass());

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testDefaultCacheSetup() throws Exception {

		ImageCache imageCache = new ImageCache.builder(EvictionPolicy.LRU).build();

		int defaultNumberOfImages = imageCache.getDefaultMaxImages();
		assertThat("default images " + defaultNumberOfImages, imageCache.getMaxImages(), is(defaultNumberOfImages));

		
		int defaultMaxBytes = imageCache.getDefaultMaxBytes();
		assertThat(imageCache.getMaxBytes(), is(defaultMaxBytes));
	
		// 5mb = 5242880 bytes
		assertThat(imageCache.getMaxBytes(), is(5242880));
	}

	
	@Test
	public void testMaxBytesCache() throws Exception {
		
		//300 kb = 307200 bytes + bytes for key string values
		int maxBytes = 307250;
		
		ImageCache imageCache = new ImageCache.builder(EvictionPolicy.LRU)
				.maxBytes(maxBytes)
				.build();
		
		
		assertThat(imageCache.getMaxBytes(), is(maxBytes));
		
		
		String testFile = "src/test/resources/test100k.db";
		ByteBuffer testBytesValue =ConversionUtils.readToBuffer(testFile);
		
		
		ByteBuffer keyOne = ConversionUtils.StringToByteBuffer("one");
		ByteBuffer keyTwo = ConversionUtils.StringToByteBuffer("two");
		ByteBuffer keyThree = ConversionUtils.StringToByteBuffer("three");
		ByteBuffer keyFour = ConversionUtils.StringToByteBuffer("four");
		imageCache.put(keyOne, testBytesValue);
		imageCache.put(keyTwo, testBytesValue);
		
		imageCache.get(keyOne);
		
		imageCache.get(keyOne);
		imageCache.put(keyThree, testBytesValue);
		imageCache.put(keyFour, testBytesValue);
		
		
		assertThat(imageCache.size(), is(3));
		assertThat(imageCache.getNumberOfBytes(), Matchers.lessThan(maxBytes));
		

		logger.info("Number of bytes {}",imageCache.getNumberOfBytes());
		logger.info("Max number of bytes {}",imageCache.getMaxBytes());
		
	}
}
