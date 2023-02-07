package demo002.autowiring.by_annotations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("beanParent")
@Getter
public class BeanParentWithAutowired implements BeanParentInterface {

    @Autowired
    public BeanChildInterface beanChild;
}
