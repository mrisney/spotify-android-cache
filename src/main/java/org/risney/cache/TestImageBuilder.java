package org.risney.cache;

import java.nio.ByteBuffer;

import org.risney.cache.utils.ConversionUtils;
import org.risney.cache.utils.eviction.EvictionPolicy;

public class TestImageBuilder {

	public static void main(String[] args) {

		ImageCache imageCache = new ImageCache.builder(EvictionPolicy.LFU).maxImages(2).maxBytes(13312000).build();

		System.out.println(imageCache.getEvictionPolicy());
		System.out.println(imageCache.getMaxBytes());
		System.out.println(imageCache.getMaxImages());

		ByteBuffer key1 = ConversionUtils.StringToByteBuffer("marc.baby.jpg");
		ByteBuffer key2 = ConversionUtils.StringToByteBuffer("sonny.jpg");
		ByteBuffer key3 = ConversionUtils.StringToByteBuffer("sonny3.jpg");

		String imageOne = "/Users/marcrisney/Projects/spotify-android-cache-exercise/marc.baby.jpg";
		String imageTwo = "/Users/marcrisney/Projects/spotify-android-cache-exercise/sonny.jpg";

		try {
			ByteBuffer imageTwoBB = ConversionUtils.readToBuffer(imageTwo);
			imageCache.put(key3, imageTwoBB);
			imageCache.get(key3);
		} catch (Exception e) {

		}

		try {
			ByteBuffer imageOneBB = ConversionUtils.readToBuffer(imageOne);
			imageCache.put(key1, imageOneBB);
		} catch (Exception e) {

		}

		try {
			ByteBuffer imageTwoBB = ConversionUtils.readToBuffer(imageTwo);
			imageCache.put(key2, imageTwoBB);
		} catch (Exception e) {

		}

	}

}
