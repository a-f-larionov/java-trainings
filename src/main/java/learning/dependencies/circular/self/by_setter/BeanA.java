package learning.dependencies.circular.self.by_setter;

public class BeanA {

    private BeanA beanA;

    public void setBeanA(BeanA beanA) {
        this.beanA = beanA;
    }
}
