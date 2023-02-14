package spring.values;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("bean")
@Getter
public class BeanValues {

    @Value("5")
    private int x;

    @Value("${notExistentValue}")
    private String notExistentValue;
}
