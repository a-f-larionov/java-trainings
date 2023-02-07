package demo002.value;

import demo002.values.BeanConfig;
import demo002.values.BeanValues;
import org.junit.jupiter.api.Test;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueTest {

    @Test
    public void simpleValues() {
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(BeanValues.class);
        cntx.refresh();

        var bean = cntx.getBean("bean", BeanValues.class);

        assertThat(bean.getX()).isEqualTo(5);
        assertThat(bean.getNotExistentValue()).isEqualTo("${notExistentValue}");
    }

    @Test
    public void defaultEnvironmentBeans() {
        //given
        var cntx = new GenericApplicationContext();
        cntx.refresh();

        // when - then
        assertThat(cntx.getBeanDefinitionCount()).isEqualTo(0);
        assertThat(cntx.getBeanNamesForType(Object.class)).hasSize(7);

        assertThat(cntx.getBean("environment")).isInstanceOf(StandardEnvironment.class);
        assertThat(cntx.getBean("systemProperties")).isInstanceOf(Properties.class);
        assertThat(cntx.getBean("systemEnvironment")).isInstanceOf(Collections.unmodifiableMap(Map.of()).getClass());
    }

    @Test
    public void defultSystemPropertySources() {
        var cnxt = new GenericApplicationContext();

        var sources = cnxt.getEnvironment().getPropertySources();

        assertThat(sources.get("systemProperties"))
                .isInstanceOf(PropertiesPropertySource.class);
        assertThat(sources.get("systemEnvironment"))
                .isInstanceOf(SystemEnvironmentPropertySource.class);
    }

    @Test
    public void addOwnSource() throws IOException {
        // given
        var cntx = new AnnotationConfigApplicationContext();
        var resource = cntx.getResource("classpath:app.prop");
        var source = new ResourcePropertySource("app.prop", resource);
        var env = cntx.getEnvironment();
        env.getPropertySources().addLast(source);

        env.getPropertySources().addLast(new RandomValuePropertySource());
        // when
        assertThat(env.getProperty("values.value1")).isEqualTo("123");
        assertThat(env.getProperty("random.int")).isNotNull();
    }

    @Test
    public void passJustValue() {
        var beanConfig = getBeanConfig();

        assertThat(beanConfig.getJustPassValue()).isEqualTo("just-value");
    }

    @Test
    public void passValueWithConversion() {
        var beanConfig = getBeanConfig();

        assertThat(beanConfig.getDoubleValue()).isEqualTo(55.55);
    }

    @Test
    public void passFromPropertiesFileWithConversion() {
        var beanConfig = getBeanConfig();

        assertThat(beanConfig.getValue1()).isEqualTo(123);
    }

    @Test
    public void passNotExistentProperty() {
        var beanConfig = getBeanConfig();

        assertThat(beanConfig.getNotAvailable()).isEqualTo("${values.notAvailable}");
    }

    @Test
    public void propertySourcePlaceholderConfigurer() {
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(BeanConfig.class);
        //cntx.register(PropertySourcesPlaceholderConfigurer.class);
        cntx.refresh();
        var beanConfig = cntx.getBean("beanConfig", BeanConfig.class);

        beanConfig.getNotAvailable();
    }

    @Test
    public void defaultValue() {
        var beanConfig = getBeanConfig();

        assertThat(beanConfig.getDefaultValue()).isEqualTo("defaultValue");
    }

    @Test
    public void spEl() {
        var beanConfig = getBeanConfig();

        assertThat(beanConfig.getSpEl()).isEqualTo(10);
    }

    @Test
    public void spElJson() {
        var beanConfig = getBeanConfig();
        var json = beanConfig.getSpElJson();

        assertThat(((Map) json).get("a")).isEqualTo(1);
    }

    private BeanConfig getBeanConfig() {
        var cntx = new AnnotationConfigApplicationContext();
        cntx.register(BeanConfig.class);
        cntx.refresh();
        var beanConfig = cntx.getBean("beanConfig", BeanConfig.class);
        return beanConfig;
    }
}
