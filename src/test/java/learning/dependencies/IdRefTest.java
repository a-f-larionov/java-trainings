package learning.dependencies;

import learning.beans.BeanDependsOnChildEmpty;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class IdRefTest {

    @Test
    public void idRef() {
        // given
        var cntx = new ClassPathXmlApplicationContext(
                "dependencies/idref.xml");
        // wh
        var beanA1 = cntx.getBean("beanA1", BeanDependsOnChildEmpty.class);
        var beanA2 = cntx.getBean("beanA2", BeanDependsOnChildEmpty.class);
        var beanA3 = cntx.getBean("beanA3", BeanDependsOnChildEmpty.class);

        // then
        assertThat(beanA1).isNotNull();
        assertThat(beanA1.getBeanChildEmpty()).isNotNull();

        assertThat(beanA1.getBeanChildEmptyId()).isNull();
        assertThat(beanA2.getBeanChildEmptyId()).isNull();
        assertThat(beanA3.getBeanChildEmptyId()).isEqualTo("child");
        cntx.close();
    }
}
