package demo002.dependencies.circular.by_setters;

public class BeanB {

    private BeanA beanA;

    public void setBeanA(BeanA beanA) {
        this.beanA = beanA;
    }
}
