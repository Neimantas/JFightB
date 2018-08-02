package main.Services.Impl;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CacheTest {

    Cache cache = Cache.getInstance();
    String key = "test";

    @Test
    public void getInsertedObject() {

        Integer integer = new Integer(12);
        cache.put(key, integer);
        Object object = cache.get(key);
        assertTrue(!object.equals(null));
    }

    @Test
    public void removeInsertedObject() {
        cache.remove(key);
        Object object = cache.get(key);
        assertTrue(object.equals(null));
    }
}
