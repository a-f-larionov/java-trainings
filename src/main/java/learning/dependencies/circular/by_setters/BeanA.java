package learning.dependencies.circular.by_setters;

public class BeanA {

    private BeanB beanB;

    public void setBeanB(BeanB beanB) {
        this.beanB = beanB;
    }
}
