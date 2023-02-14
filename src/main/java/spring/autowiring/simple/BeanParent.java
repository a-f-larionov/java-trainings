package spring.autowiring.simple;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@RequiredArgsConstructor
public class BeanParent {

    @Setter
    private BeanChild beanByPropertyValues;

    @Setter
    private BeanChild beanByType;

    @Setter
    private BeanChild beanByName;

    @Autowired
    private BeanChild beanByAnnotation;

    private final BeanChild beanByConstructor;

    public BeanParent() {
        this.beanByConstructor = null;
    }
}
