package task1.mapscomparing.threadsafemapimplementation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class MyThreadSafeMapWithSynchronization<K, V> implements MyThreadSafeMap<K, V> {
    private volatile Set<MyEntry<K, V>> data;
    private final Object mySynchronization;

    public MyThreadSafeMapWithSynchronization() {
        data = new HashSet<>();
        mySynchronization = this;
    }

    public MyThreadSafeMapWithSynchronization(Collection<MyEntry<K, V>> data) {
        if (isNull(data)) {
            throw new IllegalArgumentException("Initial collection is null");
        }
        this.data = new HashSet<>(data);
        mySynchronization = this;
    }

    @Override
    public V get(K key) {
        if (isNull(key)) {
            throw new IllegalArgumentException("Key is null");
        }
        synchronized (mySynchronization) {
            Optional<MyEntry<K, V>> myEntry = data.stream()
                    .filter(entry -> key.equals(entry.key))
                    .findAny();
            return myEntry.isPresent() ? myEntry.get().value : null;
        }
    }

    @Override
    public void put(K key, V value) {
        if (isNull(key) || isNull(value)) {
            throw new IllegalArgumentException("Key or value is null");
        }
        MyEntry<K, V> entryToAdd = new MyEntry<>(key, value);
        synchronized (mySynchronization) {
            data.removeIf(entry -> key.equals(entry.key));
            data.add(entryToAdd);
        }
    }

    @Override
    public void remove(K key) {
        if (isNull(key)) {
            throw new IllegalArgumentException("Key is null");
        }
        synchronized (mySynchronization) {
            data.removeIf(entry -> key.equals(entry.key));
        }

    }

    @Override
    public Set<K> getKeys() {
        Set<K> keys;
        synchronized (mySynchronization) {
            keys = data.stream()
                    .map(entry -> entry.key)
                    .collect(Collectors.toSet());
        }
        return keys;
    }

    @Override
    public List<V> getValues() {
        List<V> values;
        synchronized (mySynchronization) {
            values = data.stream()
                    .map(entry -> entry.value)
                    .collect(Collectors.toList());
        }
        return values;
    }

    public static class MyEntry<K, V> {
        private final K key;
        private final V value;

        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MyEntry)) return false;
            MyEntry<?, ?> myEntry = (MyEntry<?, ?>) o;
            return Objects.equals(key, myEntry.key) &&
                    Objects.equals(value, myEntry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public String toString() {
            return "MyEntry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    public Iterator<MyEntry<K, V>> iterator() {
        final Iterator<MyEntry<K, V>> it = data.iterator();
        return new Iterator<MyEntry<K, V>>() {
            public boolean hasNext() {
                synchronized (mySynchronization) {
                    return it.hasNext();
                }
            }

            public MyEntry<K, V> next() {
                synchronized (mySynchronization) {
                    return it.next();
                }
            }

            public void remove() {
                synchronized (mySynchronization) {
                    it.remove();
                }
            }
        };
    }
}
