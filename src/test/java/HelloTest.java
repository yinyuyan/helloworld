import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.SchemaOutputResolver;

/**
 * Created by Administrator on 2015/12/7.
 */
public class HelloTest {
    @After
    public void setUp(){
        System.out.println("setup end....");
    }

    @Test
    public void sayHello() {
        System.out.println(new Hello().sayHello());
    }
}
