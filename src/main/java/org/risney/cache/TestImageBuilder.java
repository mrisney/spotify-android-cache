package org.risney.cache;

import java.nio.ByteBuffer;

import org.risney.cache.utils.ConversionUtils;

public class TestImageBuilder {

	public static void main(String[] args) {
		ImageCache imageCache = new ImageCache.builder(EvictionPolicy.LRU)
				.maxImages(2)
				.maxBytes(133120)
				.build();
		System.out.println(imageCache.getEvictionPolicy());
		System.out.println(imageCache.getMaxBytes());
		System.out.println(imageCache.getMaxImages());

		ByteBuffer key1 = ConversionUtils.StringToByteBuffer("marc.baby.jpg");
		ByteBuffer key2 = ConversionUtils.StringToByteBuffer("sonny.jpg");
		

		String imageOne = "/Users/marcrisney/Projects/spotify-android-cache-exercise/marc.baby.jpg";
		try {
			ByteBuffer imageOneBB = ConversionUtils.readToBuffer(imageOne);
			imageCache.put(key1, imageOneBB);
		} catch (Exception e) {

		}
		String imageTwo = "/Users/marcrisney/Projects/spotify-android-cache-exercise/sonny.jpg";
		try {
			ByteBuffer imageTwoBB = ConversionUtils.readToBuffer(imageTwo);
			imageCache.put(key2, imageTwoBB);
		} catch (Exception e) {

		}

	}

}
