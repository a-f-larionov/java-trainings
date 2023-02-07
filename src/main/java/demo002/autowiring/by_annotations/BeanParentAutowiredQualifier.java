package demo002.autowiring.by_annotations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("beanParent")
@Getter
public class BeanParentAutowiredQualifier implements BeanParentInterface {

    @Autowired
    @Qualifier("qualifierValue")
    public BeanChildInterface beanChild;
}
