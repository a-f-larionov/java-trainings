package spring.beans;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class MultipleBeans {

    @Test
    public void oneClassManyBeans() {
        // given
        var configLocation = "beans/one-class-many-beans.xml";

        // when
        var cntx = new ClassPathXmlApplicationContext(configLocation);
        BeanEmpty beanA = cntx.getBean("beanA", BeanEmpty.class);
        BeanEmpty beanB = cntx.getBean("beanB", BeanEmpty.class);

        // then
        assertThat(beanA)
                .isInstanceOf(BeanEmpty.class)
                .isNotSameAs(beanB);
        assertThat(beanB)
                .isInstanceOf(BeanEmpty.class)
                .isNotSameAs(beanA);
    }
}