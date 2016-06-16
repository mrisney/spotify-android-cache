package org.risney.cache;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CacheEntry {

	

	private static final AtomicLong idGenerator = new AtomicLong(0L);

	private final long id;
	private final long entryDate;
	private volatile long lastHitDate;
	private final AtomicInteger hitCount = new AtomicInteger(0);
	

	public CacheEntry() {
		entryDate = System.currentTimeMillis();
		lastHitDate = entryDate;
		id = idGenerator.getAndIncrement();

	}

	public long getEntryDate() {
		return entryDate;
	}

	public long getLastHitDate() {
		return lastHitDate;
	}

	public int getHitCount() {
		return hitCount.get();
	}

	public void hit() {
		hitCount.getAndIncrement();
		lastHitDate = System.currentTimeMillis();
	}

	public long getId() {
		return id;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (entryDate ^ (entryDate >>> 32));
		result = prime * result + ((hitCount == null) ? 0 : hitCount.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (lastHitDate ^ (lastHitDate >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheEntry other = (CacheEntry) obj;
		if (entryDate != other.entryDate)
			return false;
		if (hitCount == null) {
			if (other.hitCount != null)
				return false;
		} else if (!hitCount.equals(other.hitCount))
			return false;
		if (id != other.id)
			return false;
		if (lastHitDate != other.lastHitDate)
			return false;
		return true;
	}

}