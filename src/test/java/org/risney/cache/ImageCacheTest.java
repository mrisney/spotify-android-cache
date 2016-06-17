package org.risney.cache;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.nio.ByteBuffer;

import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.risney.cache.policies.EvictionPolicy;
import org.risney.cache.utils.ConversionUtils;
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
	public void testBasicCacheProperties() throws Exception {

		
		final int MAX_IMAGES = 5;
		final int MAX_BYTES = 507250;

		ImageCache imageCache = new ImageCache.builder(EvictionPolicy.LRU)
				.maxBytes(MAX_BYTES)
				.maxImages(MAX_IMAGES)
				.build();

		assertThat(imageCache.getEvictionPolicy(), is(EvictionPolicy.LRU));
		
		
		String testFile = "src/test/resources/test100k.db";
		ByteBuffer testBytesValue = ConversionUtils.readToBuffer(testFile);

		ByteBuffer keyOne = ConversionUtils.stringToByteBuffer("one");
		ByteBuffer keyTwo = ConversionUtils.stringToByteBuffer("two");
		ByteBuffer keyThree = ConversionUtils.stringToByteBuffer("three");
		ByteBuffer keyFour = ConversionUtils.stringToByteBuffer("four");

		imageCache.putIfAbsent(keyOne, testBytesValue);
		
		ByteBuffer keyOneValue = imageCache.get(keyOne);
		assertThat(keyOneValue, is(testBytesValue));
		
	
		imageCache.putIfAbsent(keyTwo, testBytesValue);
		imageCache.putIfAbsent(keyThree, testBytesValue);
		imageCache.putIfAbsent(keyFour, testBytesValue);
		
		imageCache.remove(keyFour);
		ByteBuffer keyFourValue = imageCache.get(keyFour);
		assertNull(keyFourValue);
		
		ByteBuffer nonExistentValue = imageCache.get(ConversionUtils.stringToByteBuffer("nothing"));
		assertNull(nonExistentValue);
		boolean notExists = imageCache.containsKey(ConversionUtils.stringToByteBuffer("nothing"));
		assertThat(notExists, is(false));
		
		
		
		boolean exists = imageCache.containsKey(ConversionUtils.stringToByteBuffer("one"));
		assertThat(notExists, is(false));
		
		
		assertThat(imageCache.getNumberOfBytes(), Matchers.lessThan(MAX_BYTES));
		assertThat(imageCache.size(), Matchers.lessThan(MAX_IMAGES));
	
		
		
	}
	
	
	@Test
	public void testMaxValuesCache() throws Exception {

		final int MAX_IMAGES = 3;

		ImageCache imageCache = new ImageCache.builder(EvictionPolicy.LRU).maxImages(MAX_IMAGES).build();

		assertThat(imageCache.getMaxImages(), is(MAX_IMAGES));

		String testFile = "src/test/resources/test100k.db";
		ByteBuffer testBytesValue = ConversionUtils.readToBuffer(testFile);

		ByteBuffer keyOne = ConversionUtils.stringToByteBuffer("one");
		ByteBuffer keyTwo = ConversionUtils.stringToByteBuffer("two");
		ByteBuffer keyThree = ConversionUtils.stringToByteBuffer("three");
		ByteBuffer keyFour = ConversionUtils.stringToByteBuffer("four");

		imageCache.put(keyOne, testBytesValue);
		imageCache.put(keyTwo, testBytesValue);
		imageCache.put(keyThree, testBytesValue);
		imageCache.put(keyFour, testBytesValue);

		assertThat(imageCache.size(), is(MAX_IMAGES));

	}

	@Test
	public void testMaxBytesCache() throws Exception {

		// 300 kb = 307200 bytes + bytes for key string values
		final int MAX_BYTES = 307250;

		ImageCache imageCache = new ImageCache.builder(EvictionPolicy.LRU).maxBytes(MAX_BYTES).build();

		assertThat(imageCache.getMaxBytes(), is(MAX_BYTES));

		logger.info("Number of bytes {}", imageCache.getNumberOfBytes());
		logger.info("Max number of bytes {}", imageCache.getMaxBytes());

		String testFile = "src/test/resources/test100k.db";
		ByteBuffer testBytesValue = ConversionUtils.readToBuffer(testFile);

		ByteBuffer keyOne = ConversionUtils.stringToByteBuffer("one");
		ByteBuffer keyTwo = ConversionUtils.stringToByteBuffer("two");
		ByteBuffer keyThree = ConversionUtils.stringToByteBuffer("three");
		ByteBuffer keyFour = ConversionUtils.stringToByteBuffer("four");

		imageCache.put(keyOne, testBytesValue);
		imageCache.put(keyTwo, testBytesValue);
		imageCache.put(keyThree, testBytesValue);
		imageCache.put(keyFour, testBytesValue);

		assertThat(imageCache.size(), is(3));
		assertThat(imageCache.getNumberOfBytes(), Matchers.lessThan(MAX_BYTES));

		logger.info("Number of bytes {}", imageCache.getNumberOfBytes());
		logger.info("Max number of bytes {}", imageCache.getMaxBytes());

	}

	@Test
	public void testLRUCache() throws Exception {

		final int MAX_IMAGES = 3;
		final int MAX_BYTES = 307250;

		ImageCache imageCache = new ImageCache.builder(EvictionPolicy.LRU)
				.maxBytes(MAX_BYTES)
				.maxImages(MAX_IMAGES)
				.build();

		String testFile = "src/test/resources/test100k.db";
		ByteBuffer testBytesValue = ConversionUtils.readToBuffer(testFile);

		ByteBuffer keyOne = ConversionUtils.stringToByteBuffer("one");
		ByteBuffer keyTwo = ConversionUtils.stringToByteBuffer("two");
		ByteBuffer keyThree = ConversionUtils.stringToByteBuffer("three");
		ByteBuffer keyFour = ConversionUtils.stringToByteBuffer("four");

		imageCache.put(keyOne, testBytesValue);
		imageCache.put(keyTwo, testBytesValue);
		imageCache.put(keyThree, testBytesValue);

		assertThat(imageCache.size(), is(MAX_IMAGES));
		
	
		imageCache.put(keyFour, testBytesValue);
		assertThat(imageCache.getNumberOfBytes(), Matchers.lessThan(MAX_BYTES));
				
		ByteBuffer keyFourValue = imageCache.get(keyFour);
		assertNotNull(keyFourValue);
		imageCache.put(keyOne, testBytesValue);

		ByteBuffer keyOneValue = imageCache.get(keyOne);

		assertNotNull(keyOneValue);

		ByteBuffer keyTwoValue = imageCache.get(keyTwo);
		assertNull(keyTwoValue);

	}
	
	@Test
	public void testLFUCache() throws Exception {
		
		final int MAX_IMAGES = 3;
		final int MAX_BYTES = 307250;

		ImageCache imageCache = new ImageCache.builder(EvictionPolicy.LFU)
				.maxBytes(MAX_BYTES)
				.maxImages(MAX_IMAGES)
				.build();

		String testFile = "src/test/resources/test100k.db";
		ByteBuffer testBytesValue = ConversionUtils.readToBuffer(testFile);

		ByteBuffer keyOne = ConversionUtils.stringToByteBuffer("one");
		ByteBuffer keyTwo = ConversionUtils.stringToByteBuffer("two");
		ByteBuffer keyThree = ConversionUtils.stringToByteBuffer("three");
		ByteBuffer keyFour = ConversionUtils.stringToByteBuffer("four");

		imageCache.put(keyOne, testBytesValue);
		imageCache.put(keyTwo, testBytesValue);
		imageCache.put(keyThree, testBytesValue);

		assertThat(imageCache.size(), is(MAX_IMAGES));
		
		ByteBuffer keyOneValue = imageCache.get(keyOne);
		ByteBuffer keyTwoValue = imageCache.get(keyTwo);
		
		imageCache.put(keyFour, testBytesValue);
		assertThat(imageCache.getNumberOfBytes(), Matchers.lessThan(MAX_BYTES));
				
		ByteBuffer keyFourValue = imageCache.get(keyFour);
		assertNotNull(keyFourValue);
		
		ByteBuffer keyThreeValue = imageCache.get(keyThree);
		assertNull(keyThreeValue);
	}
	
	
	@Test
	public void testFIFOCache() throws Exception {
		
		final int MAX_IMAGES = 3;
		final int MAX_BYTES = 307250;

		ImageCache imageCache = new ImageCache.builder(EvictionPolicy.FIFO)
				.maxBytes(MAX_BYTES)
				.maxImages(MAX_IMAGES)
				.build();

		String testFile = "src/test/resources/test100k.db";
		ByteBuffer testBytesValue = ConversionUtils.readToBuffer(testFile);

		ByteBuffer keyOne = ConversionUtils.stringToByteBuffer("one");
		ByteBuffer keyTwo = ConversionUtils.stringToByteBuffer("two");
		ByteBuffer keyThree = ConversionUtils.stringToByteBuffer("three");
		ByteBuffer keyFour = ConversionUtils.stringToByteBuffer("four");

		imageCache.put(keyOne, testBytesValue);
		imageCache.put(keyTwo, testBytesValue);
		imageCache.put(keyThree, testBytesValue);

		assertThat(imageCache.size(), is(MAX_IMAGES));
		
		imageCache.put(keyFour, testBytesValue);
		assertThat(imageCache.getNumberOfBytes(), Matchers.lessThan(MAX_BYTES));
				
		ByteBuffer keyFourValue = imageCache.get(keyFour);
		assertNotNull(keyFourValue);
		
		ByteBuffer keyOneValue = imageCache.get(keyOne);
		assertNull(keyOneValue);
	}
	
	
	@Test
	public void testMaxSizeEvictionCache() throws Exception {
		
		
		final int MAX_IMAGES = 4;
		
		//1.2 MB = 1258291.2 B
			
		final int MAX_BYTES = 1258291;

		ImageCache imageCache = new ImageCache.builder(EvictionPolicy.SIZE)
				.maxBytes(MAX_BYTES)
				.maxImages(MAX_IMAGES)
				.build();

		String testFile = "src/test/resources/test100k.db";
		String testBiggerFile = "src/test/resources/test1Mb.db";
		
		ByteBuffer testBytesValue = ConversionUtils.readToBuffer(testFile);
		ByteBuffer testBiggerBytesValue = ConversionUtils.readToBuffer(testBiggerFile);
		

		ByteBuffer keyOne = ConversionUtils.stringToByteBuffer("one");
		ByteBuffer keyTwo = ConversionUtils.stringToByteBuffer("two");
		ByteBuffer keyThree = ConversionUtils.stringToByteBuffer("three");
		ByteBuffer keyFour = ConversionUtils.stringToByteBuffer("four");

		imageCache.put(keyOne, testBytesValue);
		imageCache.put(keyTwo, testBiggerBytesValue);
		imageCache.put(keyThree, testBytesValue);
		imageCache.put(keyFour, testBytesValue);
		
		
		ByteBuffer keyFourValue = imageCache.get(keyFour);
		assertNotNull(keyFourValue);
		
		ByteBuffer keyTwoValue = imageCache.get(keyTwo);
		assertNull(keyTwoValue);
		
		assertThat(imageCache.getNumberOfBytes(), Matchers.lessThan(MAX_BYTES));
		assertThat(imageCache.size(), Matchers.lessThan(MAX_IMAGES));
	}
	
}
