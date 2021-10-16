package task1.mapscomparing.threadsafemapimplementation;

import java.util.List;
import java.util.Set;

public interface MyThreadSafeMap<K, V> {

    V get(K key);

    void put(K key, V value);

    void remove(K key);

    Set<K> getKeys();

    List<V> getValues();
}
