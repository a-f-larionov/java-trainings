package demo002.annotations.documented;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Service("name)")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface MyDocumented {
}
