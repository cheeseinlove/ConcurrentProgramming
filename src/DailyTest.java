import java.util.concurrent.CountDownLatch;

public class DailyTest {
    public  static void main(String...args){

        String str1=new String("a");
        /*java7之后，字符串常量池中可以放引用了，因此采用
    str1.intern() 后回去常量池中找是否有这个字符串，
    如果有，返回常量池中的字符串引用。
    如果没有，把堆中字符串常量的引用放到常量池中去。
    反正，常量池已经移到堆中去了。
    */
        String a=str1.intern();
        System.out.println(str1==a);
        //此时应该返回字符串常量池中引用
        String b=str1.intern();

        System.out.print(b== "a");

    }
}
