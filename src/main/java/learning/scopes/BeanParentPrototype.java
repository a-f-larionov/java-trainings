package learning.scopes;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BeanParentPrototype {

    @Autowired
    private BeanSingleton firstBeanSingleton;

    @Autowired
    private BeanSingleton secondBeanSingleton;

    @Autowired
    private BeanPrototype firstBeanPrototype;

    @Autowired
    private BeanPrototype secondBeanPrototype;
}
