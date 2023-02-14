package spring.autowiring.predefined_beans;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Getter
@Component("bean")
public class BeanWithPredefined {

    private final ApplicationContext applicationContext;
    private final Environment environment;
    private final BeanFactory beanFactory;
}

