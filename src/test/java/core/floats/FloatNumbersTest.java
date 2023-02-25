package core.floats;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FloatNumbersTest {

    @Test
    public void precisionLost() {
        // given when then
        assertThat(100_000_000F == 100_000_001F).isTrue();
    }
}
