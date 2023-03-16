package spring.jdbcTemplate;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class jdbcTemplateTest {

    @Test
    public void test1() {

        var cntxt = new AnnotationConfigApplicationContext(Config.class.getPackageName());

        Wrapper bean = cntxt.getBean(Wrapper.class);

        bean.jdbcTemplate.query(
                "SELECT 1 as col1",
                r -> {
                });
    }

}
