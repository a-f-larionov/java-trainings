package spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
class AspectB {

    @Before(value = "execution(* spring.aop.ServiceB.*(..) )")
    public void beforeAdvice(JoinPoint joinPoint) {

        ((ServiceB) joinPoint.getTarget()).setAspectCalled(true);

    }
}
