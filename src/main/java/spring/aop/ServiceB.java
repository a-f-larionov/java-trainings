package spring.aop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
class ServiceB {

    private boolean isMethodBCalled = false;
    private boolean isAspectCalled = false;

    public void methodB() {
        isMethodBCalled = true;
    }
}
