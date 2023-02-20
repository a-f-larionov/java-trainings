package spring.transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "logging.level.root=DEBUG")
public class TxansactionalTest {

    @Autowired
    private MyService myService;

    @Test
    public void test1() {

        myService.test1();
        myService.test2();
        int x = 5;
        x++;

    }
}

