package learning.autowiring.by_annotations;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class BeanChildAPrimary implements BeanChildInterface {
}
