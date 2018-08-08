import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectTest {
    public int id = 1;

    public void print(String name,int age) {
        System.out.println(name + " " + id+age);
    }

    public static void main(String... agrs) {
        Class r = ReflectTest.class;
        Method[] ms = r.getMethods();
//        for (Method m : ms) {
//            Class[] paramTypes = m.getParameterTypes();
//            System.out.print(m.getName()+": ");
//            for (Class type : paramTypes) {
//                System.out.print(type.getTypeName()+" " );
//            }
//            System.out.println();
//        }
        Field[] fields=r.getFields();
        for (Field field : fields) {
//得到成员变量的类型的类类型
            Class fieldType = field.getType();
            String typeName = fieldType.getName();
//得到成员变量的名称
            String fieldName = field.getName();
            System.out.println(typeName+" "+fieldName);
        }
    }
}
