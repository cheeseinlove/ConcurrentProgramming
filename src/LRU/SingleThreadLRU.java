package LRU;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/*
1. 新数据插入到链表头部；
2. 每当缓存命中（即缓存数据被访问），则将数据移到链表头部；
3. 当链表满的时候，将链表尾部的数据丢弃
 */
public class SingleThreadLRU<K, V> {
    private LinkedList<K> list;
    private HashMap<K, V> map;
    private int capacity;

    public SingleThreadLRU(int capacity) {
        this.list = new LinkedList<>();
        this.map = new HashMap<>();
        this.capacity = capacity;
    }


    public void add(K key, V value) {
        //若存在缓存只更新list位置
        if (map.containsKey(key)) {
            get(key);
            //更新
            map.put(key, value);
        } else {

            //缓存列表不用淘汰
            if (list.size() < capacity) {
                list.addFirst(key);
                map.put(key, value);
            } else {
                list.addFirst(key);
                //淘汰策略
                K old = list.removeLast();
                map.remove(old);
            }

        }
    }

    public V get(K key) {
        if (map.containsKey(key)) {
            Iterator<K> it = list.listIterator();
            while (it.hasNext()) {
                if (it.next() == key) {
                    it.remove();
                    break;
                }
            }

            list.addFirst(key);
            return map.get(key);
        }

        return null;
    }

}
