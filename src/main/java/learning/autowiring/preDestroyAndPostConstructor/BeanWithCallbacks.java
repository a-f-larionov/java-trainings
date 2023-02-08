package learning.autowiring.preDestroyAndPostConstructor;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BeanWithCallbacks {

    private boolean postConstructCalled = false;
    private boolean preDestroyCalled = false;

    @PostConstruct
    public void postConstruct() {
        postConstructCalled = true;
    }

    @PreDestroy
    public void preDestroy() {
        preDestroyCalled = true;
    }
}
