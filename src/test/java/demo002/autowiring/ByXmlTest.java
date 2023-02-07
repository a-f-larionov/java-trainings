package demo002.autowiring;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ByXmlTest {

    @Test
    public void qualifierByXmlDefintion() {
        // given
        var cntx = new ClassPathXmlApplicationContext("autowiring/qualifier.xml");
        cntx.getBean("beanParent");

        cntx.close();
    }
}
