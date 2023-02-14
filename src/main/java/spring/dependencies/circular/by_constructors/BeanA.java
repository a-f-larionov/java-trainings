package spring.dependencies.circular.by_constructors;

public class BeanA {

    private BeanB beanB;

    public BeanA(BeanB beanB) {
        this.beanB = beanB;
    }
}
