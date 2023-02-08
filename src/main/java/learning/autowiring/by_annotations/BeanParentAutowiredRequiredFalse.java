package learning.autowiring.by_annotations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("beanParent")
@Getter
public class BeanParentAutowiredRequiredFalse implements BeanParentInterface {

    @Autowired(required = false)
    private BeanChildInterface beanChild;
}
