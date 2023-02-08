package learning.dependencies.circular.by_constructors;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext(
                "dependencies/circular/by-constructors.xml");
    }
}

