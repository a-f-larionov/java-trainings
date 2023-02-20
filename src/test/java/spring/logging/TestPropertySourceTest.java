package spring.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = "logging.level.root=WARN")
public class TestPropertySourceTest {

    @Test
    public void testLevel() {
        // given
        var getLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        // when
        var logger = (ch.qos.logback.classic.Logger) getLogger;

        // then
        assertThat(logger.getLevel()).isEqualTo(Level.WARN);
    }

    @Test
    public void setLevel() {
        // given
        var getLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        var logger = (ch.qos.logback.classic.Logger) getLogger;
        logger.setLevel(Level.ERROR);

        //when
        logger.setLevel(Level.OFF);

        // then
        assertThat(logger.getLevel()).isEqualTo(Level.OFF);
    }
}
