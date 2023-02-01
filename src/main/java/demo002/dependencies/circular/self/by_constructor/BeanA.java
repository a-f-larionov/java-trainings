package demo002.dependencies.circular.self.by_constructor;

public class BeanA {

    private BeanA beanA;

    public BeanA(BeanA beanA) {
        this.beanA = beanA;
    }
}
