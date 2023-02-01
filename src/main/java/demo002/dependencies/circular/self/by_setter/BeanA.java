package demo002.dependencies.circular.self.by_setter;

public class BeanA {

    private BeanA beanA;

    public void setBeanA(BeanA beanA) {
        this.beanA = beanA;
    }
}
