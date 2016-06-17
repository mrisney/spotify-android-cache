package org.risney.cache;

public class ImageCache {
	// http://www.conversion-metric.org/filesize/megabytes-to-bytes
	private static final int MAX_IMAGES = 100;
	private static final int MAX_BYTES = 524288;

	private final EvictionPolicy evictionPolicy; // required
	protected int maxImages = MAX_IMAGES; // optional
	protected int maxBytes = MAX_BYTES; // optional

	private ImageCache(ImageCacheBuilder builder) {
		this.evictionPolicy = builder.evictionPolicy;
		this.maxImages = builder.maxImages;
		this.maxBytes = builder.maxBytes;
	}

	public EvictionPolicy getEvictionPolicy() {
		return evictionPolicy;
	}

	public int getMaxImages() {
		return maxImages;
	}

	public int getMaxBytes() {
		return maxBytes;
	}

	public static class ImageCacheBuilder {
		private final EvictionPolicy evictionPolicy;
		protected int maxImages;
		protected int maxBytes;

		public ImageCacheBuilder(EvictionPolicy evictionPolicy) {
			this.evictionPolicy = evictionPolicy;
			this.maxImages = MAX_IMAGES;
			this.maxBytes = MAX_BYTES;
		}
		public ImageCacheBuilder maxImages(int maxImages) {
			this.maxImages = maxImages;
			return this;
		}

		public ImageCacheBuilder maxBytes(int maxBytes) {
			this.maxBytes = maxBytes;
			return this;
		}

		public ImageCache build() {
			return new ImageCache(this);
		}
	}
}
