/**
 * Created by Administrator on 2015/12/18.
 */
public class jiayi<D extends Hello,T> {
    protected D hello;
    String sayHello(){
       return this.hello.a;
    }

    public static void main(String[] args) {
        System.out.println(new jiayi().hello.a);
    }
}


