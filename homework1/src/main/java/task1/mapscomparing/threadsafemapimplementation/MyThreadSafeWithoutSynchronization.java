package task1.mapscomparing.threadsafemapimplementation;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class MyThreadSafeWithoutSynchronization<K, V> implements MyThreadSafeMap<K, V> {
    private volatile Set<MyEntry<K, V>> data;

    public MyThreadSafeWithoutSynchronization() {
        data = new CopyOnWriteArraySet<>();
    }

    public MyThreadSafeWithoutSynchronization(Collection<MyEntry<K, V>> data) {
        this.data = new CopyOnWriteArraySet<>(data);
    }

    @Override
    public V get(K key) {
        if (isNull(key)) {
            throw new IllegalArgumentException("Key is null");
        }
        Optional<V> myEntry = data.stream()
                .filter(entry -> key.equals(entry.key))
                .map(entry -> entry.value)
                .findAny();
        return myEntry.isPresent() ? myEntry.get() : null;
    }

    @Override
    public void put(K key, V value) {
        if (isNull(key) || isNull(value)) {
            throw new IllegalArgumentException("Key or value is null");
        }
        data.add(new MyEntry<>(key, value));
    }

    @Override
    public void remove(K key) {
        if (isNull(key)) {
            throw new IllegalArgumentException("Key is null");
        }
        data.removeIf(entry -> key.equals(entry.key));
    }

    @Override
    public Set<K> getKeys() {
        return data.stream()
                .map(entry -> entry.key)
                .collect(Collectors.toSet());
    }

    @Override
    public List<V> getValues() {
        return data.stream()
                .map(entry -> entry.value)
                .collect(Collectors.toList());
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
                return it.hasNext();

            }

            public MyEntry<K, V> next() {
                return it.next();
            }

            public void remove() {

                it.remove();
            }
        };
    }
}
