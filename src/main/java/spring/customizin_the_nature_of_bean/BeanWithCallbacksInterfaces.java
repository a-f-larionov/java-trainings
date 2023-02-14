package spring.customizin_the_nature_of_bean;

import lombok.Getter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BeanWithCallbacksInterfaces implements InitializingBean, DisposableBean {

    private boolean afterPropertiesSetCalled = false;
    private boolean destroyCalled = false;

    @Override
    public void afterPropertiesSet() {
        afterPropertiesSetCalled = true;
    }

    @Override
    public void destroy() {
        destroyCalled = true;
    }
}
