import java.nio.ByteBuffer;

import org.risney.cache.ImageCache;
import org.risney.cache.policies.EvictionPolicy;
import org.risney.cache.utils.ConversionUtils;

/**
 * example usage
 */

public class QuickStart {

	public static void main(String[] args) throws Exception {

		// set the initial size to 5 images, and 1/2 a KB
		final int MAX_IMAGES = 5;
		final int MAX_BYTES = 507250;

		// uses builder pattern
		
		ImageCache imageCache = new ImageCache.Builder(EvictionPolicy.LRU)
				.maxBytes(MAX_BYTES)
				.maxImages(MAX_IMAGES)
				.build();
		
		String testFile = "src/test/resources/test100k.db";
		ByteBuffer testBytesValue = ConversionUtils.readToBuffer(testFile);
		ByteBuffer sampleKey = ConversionUtils.stringToByteBuffer("test");

		imageCache.putIfAbsent(sampleKey, testBytesValue);
		ByteBuffer retrievedTestValue = imageCache.get(sampleKey);

	}
}
