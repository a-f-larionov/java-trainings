package demo002.autowiring.by_annotations;

import org.springframework.stereotype.Component;

@Component("qualifierValue")
public class BeanChildWithQuilifierName implements BeanChildInterface {
}
