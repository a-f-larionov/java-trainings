package learning.autowiring.by_annotations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("beanParent")
@Getter
public class BeanParentWithAutowiredNullable implements BeanParentInterface {

    @Autowired
    @Nullable
    private BeanChildInterface beanChild;
}
