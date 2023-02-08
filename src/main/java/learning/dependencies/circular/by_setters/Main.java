package learning.dependencies.circular.by_setters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext(
                "dependencies/circular/by-setters.xml");

    }
}
