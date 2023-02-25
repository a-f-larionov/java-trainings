package core.threads;

import lombok.SneakyThrows;

import java.util.List;

class Operator {

    private List<String> sequence;

    public State state = State.INITIAL;

    public Operator(List<String> sequence) {
        this.sequence = sequence;
    }

    @SneakyThrows
    public void monitorWait() {
        log("monitorWait(){");
        synchronized (this) {
            log("state == INITIAL");
            if (state == State.INITIAL) {
                log("state = " + State.WAIT);
                state = State.WAIT;
                log("wait");
                this.wait();
            }
            log("}");
        }
    }

    public void monitorNotify() {
        log("monitorNotify(){");
        synchronized (this) {
            log("state = " + State.UPDATED);
            state = State.UPDATED;
            log("notify");
            this.notify();
            log("}");
        }
    }

    @SneakyThrows
    public void getALittleSleep() {
        log("getALittleSleep(){");
        log("sleep");
        Thread.sleep(100);
        log("}");
    }

    @SneakyThrows
    public void synchronizedSleep() {
        log("synchronizedSleep(){");
        synchronized (this) {
            log("start sync");
            log("sleep");
            Thread.sleep(1000);
            log("end sync");
        }
        log("}");
    }

    @SneakyThrows
    public void syncSleepWait() {
        log("syncSleepWait(){");
        synchronized (this) {
            log("start sync");
            log("sleep");
            Thread.sleep(1000);
            log("wait");
            this.wait();
            log("end sync");
        }
        log("}");
    }

    @SneakyThrows
    public void syncSleepNotifySleep() {
        log("syncSleepNotify(){");
        synchronized (this) {
            log("start sync");
            log("sleep");
            Thread.sleep(1000);
            log("notify");
            this.notify();
            log("sleep");
            Thread.sleep(1000);
            log("end sync");
        }
        log("}");
    }


    public void illegalWait() throws Exception{
        this.wait();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    private void log(String title) {
        sequence.add(Thread.currentThread().getName() + " " + title);
    }
}
