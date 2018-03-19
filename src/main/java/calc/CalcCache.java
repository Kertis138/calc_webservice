package calc;

import java.util.HashMap;
import java.util.Map;

public class CalcCache {
    private int max_cache_size = 50;
    private Map<String, String> map = new HashMap<>();

    public CalcCache() { }
    public CalcCache(int max_cache_size) {
        this.max_cache_size = max_cache_size;
    }

    public void cache(String key, String value) {
        if (map.size() >= max_cache_size)
            return;
        Log.info("New Cache: %s - %s", key,value);
        map.put(key,value);
    }

    public String getCache(String key) {
        String value = map.get(key);
        if (value != null)
            Log.info("Cached value: %s", value);
        return value;
    }

    public void clearCache() {
        map.clear();
    }
}
