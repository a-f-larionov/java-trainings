package demo002.dependencies;

import demo002.beans.BeanDependsOnChildEmpty;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class IdRef {

    @Test
    public void idRef() {
        var context = new ClassPathXmlApplicationContext(
                "dependencies/idref.xml");
        BeanDependsOnChildEmpty beanA1 = context.getBean("beanA1", BeanDependsOnChildEmpty.class);
        BeanDependsOnChildEmpty beanA2 = context.getBean("beanA2", BeanDependsOnChildEmpty.class);
        BeanDependsOnChildEmpty beanA3 = context.getBean("beanA3", BeanDependsOnChildEmpty.class);
        assertThat(beanA1).isNotNull();
        assertThat(beanA1.getBeanChildEmpty()).isNotNull();

        assertThat(beanA1.getBeanChildEmptyId()).isNull();
        assertThat(beanA2.getBeanChildEmptyId()).isNull();
        assertThat(beanA3.getBeanChildEmptyId()).isEqualTo("child");

    }
}
