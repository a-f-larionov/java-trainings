package demo002.dependencies.circular.by_constructors;

public class BeanB {

    private BeanA beanA;

    public BeanB(BeanA beanA) {
        this.beanA = beanA;
    }
}
