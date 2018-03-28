package Singleton;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {
    //需要代理的原始类
    private Object object;
    public CglibProxy(Object object){
        this.object=object;
    }
    public Object Proxy(){
        //创建加强器
        Enhancer enhancer=new Enhancer();
        //设置需要加强的类
        enhancer.setSuperclass(object.getClass());
        //设置回调
        enhancer.setCallback(this);
        enhancer.setClassLoader(object.getClass().getClassLoader());
        return enhancer.create();
    }
//    @Override
//    public Object intercept(Object arg0, Method arg1, Object[] arg2,
//                            MethodProxy arg3) throws Throwable {
//        //这里实现加强
//        Object invoke=arg3.invoke(object, arg2);
//        return invoke.toString().toUpperCase();
//    }
//    private Enhancer enhancer = new Enhancer();
//    public Object getProxy(Class clazz){
//        //设置需要创建子类的类
//        enhancer.setSuperclass(clazz);
//        enhancer.setCallback(this);
//        //通过字节码技术动态创建子类实例
//        return enhancer.create();
//    }
    //实现MethodInterceptor接口方法
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        System.out.println("前置代理");
        //通过代理类调用父类中的方法
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("后置代理");
        return result;
    }


}
