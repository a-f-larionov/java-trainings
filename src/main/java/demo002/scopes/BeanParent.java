package demo002.scopes;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BeanParent {

    @Autowired
    private BeanSingleton firstBeanSingleton;

    @Autowired
    private BeanSingleton secondBeanSingleton;

    @Autowired
    private BeanPrototype firstBeanPrototype;

    @Autowired
    private BeanPrototype secondBeanPrototype;
}