package LRU;

public class LRUTest {
    public static void main(String...args){
        SingleThreadLRU<String,Integer>lru=new SingleThreadLRU<>(4);
        lru.add("a",1);
        lru.add("b",20);
        lru.add("b",2);
        System.out.println(lru.get("b"));
        lru.add("c",3);
        lru.add("d",4);
        lru.add("e",5);
        System.out.println(lru.get("a"));

    }
}
