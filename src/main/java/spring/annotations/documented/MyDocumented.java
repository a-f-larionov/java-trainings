package spring.annotations.documented;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Service("name)")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface MyDocumented {
}
