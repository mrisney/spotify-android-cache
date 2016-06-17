package org.risney.cache;

public class TestImageBuilder {

	public static void main(String[] args) {
		ImageCache imageCache = new ImageCache.ImageCacheBuilder(EvictionPolicy.LRU)
											.maxImages(2)
											.maxBytes(2)
											.build();
		
		System.out.println(imageCache.getEvictionPolicy());
		System.out.println(imageCache.getMaxBytes());
		System.out.println(imageCache.getMaxBytes());

	}

}
