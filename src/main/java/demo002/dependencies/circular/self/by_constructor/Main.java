package demo002.dependencies.circular.self.by_constructor;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {

        var context = new ClassPathXmlApplicationContext(
                "dependencies/circular/self-by-constructor.xml");
    }
}
