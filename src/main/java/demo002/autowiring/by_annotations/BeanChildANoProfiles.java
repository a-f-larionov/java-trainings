package demo002.autowiring.by_annotations;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("no-profiles")
public class BeanChildANoProfiles implements BeanChildInterface {
}
