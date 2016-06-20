###### Introduction
This is a Java Cache library Using ByteBuffers and configurable through

###### Quick Start

``` java

import org.risney.cache.policies.EvictionPolicy;
import org.risney.cache.utils.ConversionUtils;
import org.risney.cache.ImageCache;


public class QuickStart {

    // set the initial size to 5 images, and 1/2 a KB  
    final int MAX_IMAGES = 5;
    final int MAX_BYTES = 507250;

  public static void main(String[] args) throws Exception {
      
      // uses builder pattern
      ImageCache imageCache = new ImageCache.builder(EvictionPolicy.LRU)
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
```
