package ReferenceCache;

public class TestCache {
    public static void main(String[] args) {
        EmployeeCache cache = EmployeeCache.getInstance();
        System.out.println("start");
        System.out.println("k1 start:"+System.currentTimeMillis());
        System.out.println(cache.getEmployee("1").getID());
        System.out.println("k1 end:"+System.currentTimeMillis());
        System.out.println("k2 start:"+System.currentTimeMillis());
        System.out.println(cache.getEmployee("2").getID());
        System.out.println("k2 end:"+System.currentTimeMillis());
        System.out.println("k3 start:"+System.currentTimeMillis());
        System.out.println(cache.getEmployee("1").getID());
        System.out.println("k3 end:"+System.currentTimeMillis());
        System.out.println("k4 start:"+System.currentTimeMillis());
        System.out.println(cache.getEmployee("2").getID());
        System.out.println("k4 end:"+System.currentTimeMillis());
        cache.clearCache();
        System.out.println("after clear");
        System.out.println("k5 start:"+System.currentTimeMillis());
        System.out.println(cache.getEmployee("1").getID());
        System.out.println("k5 end:"+System.currentTimeMillis());
        System.out.println("k6 start:"+System.currentTimeMillis());
        System.out.println(cache.getEmployee("2").getID());
        System.out.println("k6 end:"+System.currentTimeMillis());
    }
}
