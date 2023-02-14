package spring.values;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:app.prop")
public class BeanConfig {

    @Value("just-value")
    private String justPassValue;

    @Value("55.55")
    private double doubleValue;

    @Value("${values.value1}")
    private int value1;

    @Value("${values.notAvailable}")
    private String notAvailable;

    @Value("${values.notAvailable:defaultValue}")
    private String defaultValue;

    @Value("#{ 5 + 5 }")
    private int spEl;

    @Value("#{ { 'a':1, 'b': 2 }}")
    private Object spElJson;
}
