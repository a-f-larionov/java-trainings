package demo002;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.Lifecycle;
import org.springframework.context.LifecycleProcessor;
import org.springframework.context.Phased;
import org.springframework.context.SmartLifecycle;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class Demo002Application {

	public static void main(String[] args) {


		SpringApplication.run(Demo002Application.class, args);
	}
@org.springframework.transaction.annotation.EnableTransactionManagement
	public class MyClass{

	}
	@Transactional
	public void method(){

	}
}
