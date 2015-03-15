
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class HashMap<K, V> implements HashMapInterface<K, V> {

    private MapEntry<K, V>[] table;
    private int size;

    public HashMap() {
        size = 0;
        table = new MapEntry[STARTING_SIZE];
    }

    @Override
    public V add(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if (((size + 1) / (double) table.length) > MAX_LOAD_FACTOR) {
            MapEntry<K, V>[] temp = table;
            table = new MapEntry[temp.length * 2];
            size = 0;
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] != null && !temp[i].isRemoved()) {
                    this.add(temp[i].getKey(), temp[i].getValue());
                }
            }
        }
        int keyHC = Math.abs(key.hashCode()) % table.length;
        if (table[keyHC] != null) {
            if (table[keyHC].getKey() == key && !table[keyHC].isRemoved()) {
                MapEntry<K, V> temp = table[keyHC];
                table[keyHC] = new MapEntry<>(key, value);
                return temp.getValue();
            } else {
                int i = keyHC;
                while (table[i] != null && table[i].getKey() != key) {
                    if (table[i].isRemoved()) {
                        table[i] = new MapEntry<>(key, value);
                        size++;
                        return null;
                    } else {
                        if (i == table.length - 1) { i = 0; }
                        i++;
                    }
                }
                table[i] = new MapEntry<>(key, value);
                size++;
            }
        } else {
            table[keyHC] = new MapEntry<K, V>(key, value);
            size++;
        }
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) { throw new IllegalArgumentException(); }
        if (size == 0) { return null; }
        int i = Math.abs(key.hashCode()) % table.length;
        int end = i;
        if (table[i] != null && !table[i].isRemoved()) {
            while (table[i] != null || table[i].isRemoved()) {
                if (table[i].getKey() == key) { break; }
                if (i == table.length - 1) { i = -1; }
                i++;
                if (i == end) { return null; }
            }
        }
        if (table[i] != null && !table[i].isRemoved()) {
            table[i].setRemoved(true);
            size--;
            return table[i].getValue();
        } else { return null; }
    }

    @Override
    public V get(K key) {
        if (key == null) { throw new IllegalArgumentException(); }
        if (table[Math.abs(key.hashCode()) % table.length] != null
                && !table[Math.abs(key.hashCode())
                % table.length].isRemoved()) {
            int i = Math.abs(key.hashCode()) % table.length;
            while (table[i] != null
                    && key != (table[i].getKey())) {
                if (i == table.length - 1) {
                    i = -1;
                }
                i++;
            }
            if (table[i] == null) { return null; }
            return table[i].getValue();
        } else {
            return null;
        }
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public void clear() {
        table = new MapEntry[STARTING_SIZE];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public MapEntry<K, V>[] toArray() {
        return table;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> res = new HashSet();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                res.add(table[i].getKey());
            }
        }
        return res;
    }

    @Override
    public List<V> values() {
        ArrayList<V> res = new ArrayList<V>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                res.add(table[i].getValue());
            }
        }
        return res;
    }

}
