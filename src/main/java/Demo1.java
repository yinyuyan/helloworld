/**
 * Created by Administrator on 2015/12/4.
 */
public class Demo1 {
    public static void main(String[] args) throws InterruptedException {
        A a=new A();
        B b=new B();
        Thread aa=new Thread(a);
        Thread bb=new Thread(b);
        aa.start();
        bb.start();
        aa.sleep(1000);
    }

}
class A implements Runnable{
    public void run(){
        for(int i=0;i<5;i++){
            System.out.println("aaaa");
     //       sleep(1);
        }
    }
}
class B implements Runnable{
    public void run(){
        for(int i=0;i<5;i++){
            System.out.println("bbbb");
        }
    }
}
