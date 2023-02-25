package core.threads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

public class WaitNotifyTest {

    ArrayList<String> sequence = new ArrayList<>();

    private final Operator operator = new Operator(sequence);

    @BeforeEach
    public void beforeEach() {
        sequence.clear();
        operator.setState(State.INITIAL);
    }

    @Test
    @Disabled
    // @todo
    public void getALittleSleep() throws InterruptedException {
        // given
        var t = new Thread(operator::getALittleSleep, "t1");
        // when - then
        t.start();
        assertThat(sequence).isEqualTo(new ArrayList<>(List.of(
                "t1 getALittleSleep(){",
                "t1 sleep"
        )));
        t.join();
        assertThat(sequence).isEqualTo(List.of(
                "t1 getALittleSleep(){",
                "t1 sleep",
                "t1 }"
        ));
    }

    @Test
    public void waitAndThenNotify() throws InterruptedException {
        // given
        var t1 = new Thread(operator::monitorWait, "t1");
        var t2 = new Thread(operator::monitorNotify, "t2");

        // when
        t1.start();
        while (operator.getState() == State.INITIAL) ;
        t2.start();
        t1.join();
        t2.join();

        // then
        assertThat(sequence).isEqualTo(List.of(
                "t1 monitorWait(){",
                "t1 state == INITIAL",
                "t1 state = WAIT",
                "t1 wait",
                "t2 monitorNotify(){",
                "t2 state = UPDATED",
                "t2 notify",
                "t2 }",
                "t1 }"
        ));
    }

    @Test
    public void synchronizedIsSequenced() throws InterruptedException {
        // given
        var t1 = new Thread(operator::synchronizedSleep, "t1");
        var t2 = new Thread(operator::synchronizedSleep, "t2");

        // when
        t1.start();
        Thread.sleep(10);
        t2.start();
        t1.join();
        t2.join();

        // then
        assertThat(sequence).isEqualTo(List.of(
                "t1 synchronizedSleep(){",
                "t1 start sync",
                "t1 sleep",
                "t2 synchronizedSleep(){",
                "t1 end sync",
                "t1 }",
                "t2 start sync",
                "t2 sleep",
                "t2 end sync",
                "t2 }"
        ));
    }

    @Test
    public void syncSleepWaitSleepNotify() throws InterruptedException {
        // given
        var t1 = new Thread(operator::syncSleepWait, "t1");
        var t2 = new Thread(operator::syncSleepNotifySleep, "t2");

        // when
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.join();
        t2.join();

        // then
        assertThat(sequence).isEqualTo(List.of(
                "t1 syncSleepWait(){",
                "t1 start sync",
                "t1 sleep",

                "t2 syncSleepNotify(){",
                "t1 wait",

                "t2 start sync",
                "t2 sleep",
                "t2 notify",
                "t2 sleep",
                "t2 end sync",
                "t2 }",

                "t1 end sync",
                "t1 }"

        ));
    }


    @Test
    public void illegalMonitorState() throws InterruptedException {
        // given - when - then
        assertThatException()
                .isThrownBy(this::wait)
                .isInstanceOf(IllegalMonitorStateException.class);
    }
}

