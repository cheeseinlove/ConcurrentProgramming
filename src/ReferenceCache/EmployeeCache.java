package ReferenceCache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

public class EmployeeCache{
    static private EmployeeCache cache;//一个cache实例
    private Hashtable<String,EmployeeRef> employeeRefs;//用于cache内容的存储
    private ReferenceQueue<Employee> q;//垃圾reference的队列

    //继承softreference，使得每一个实例都具有可识别的标识
    //并且该标识与其在hashmap内的key相同

    private class EmployeeRef extends SoftReference<Employee>{
        private String _key = "";

        public EmployeeRef(Employee em, ReferenceQueue<? super Employee> q) {
            super(em, q);
            _key=em.getID();
        }
    }


    //构建一个缓存器实例
    private EmployeeCache(){
        employeeRefs = new Hashtable<String,EmployeeRef>();
        q = new ReferenceQueue<Employee>();
    }

    //取得缓存器实例
    public static EmployeeCache getInstance(){
        if(cache == null){
            cache = new EmployeeCache();
        }
        return cache;
    }

    //以软引用的方式对一个employee对象的实例进行引用并保存该引用
    private void cacheEmployee(Employee em){
        cleanCache();//清除垃圾引用
        EmployeeRef ref = new EmployeeRef(em,q);
        employeeRefs.put(em.getID(),ref);
    }

    //依据所指定的ID号，重新获取相应的employee对象的实例
    public Employee getEmployee(String ID){
        Employee em = null;
        //缓存中是否有该employee实例的软引用，如果有，从软引用中取得
        if(employeeRefs.containsKey(ID)){
            EmployeeRef ref = employeeRefs.get(ID);
            em = ref.get();
        }

        //如果没有软引用，或者从软引用中取得的实例是null
        //重新构建一个实例，并保存对这个新建实例的软引用
        if(em==null){
            em=new Employee(ID);
            System.out.println("retrieve from employeeinfocenter.id"+ID);
            this.cacheEmployee(em);
        }
        return em;
    }


    //清除那些所软引用的employee对象已经被回收的employeeRef对象
    private void cleanCache() {
        EmployeeRef ref = null;
        while((ref= (EmployeeRef) q.poll())!=null){
            employeeRefs.remove(ref._key);
        }
    }

    //清除cache内的全部内容
    public void clearCache(){
        cleanCache();
        employeeRefs.clear();
        System.gc();
        System.runFinalization();
    }
}
