package spring.dependencies.circular.self.by_setter;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {

        var context = new ClassPathXmlApplicationContext(
                "dependencies/circular/self-by-setter.xml");
    }
}
