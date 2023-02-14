package spring.beans;

public class BeanDependsOnChildEmpty {
    private BeanChildEmpty beanChildEmpty;

    private String beanChildEmptyId;

    public void setBeanChildEmpty(BeanChildEmpty beanChildEmpty) {
        this.beanChildEmpty = beanChildEmpty;
    }

    public BeanChildEmpty getBeanChildEmpty() {
        return beanChildEmpty;
    }

    public String getBeanChildEmptyId() {
        return beanChildEmptyId;
    }

    public void setBeanChildEmptyId(String beanChildEmptyId) {
        this.beanChildEmptyId = beanChildEmptyId;
    }
}
