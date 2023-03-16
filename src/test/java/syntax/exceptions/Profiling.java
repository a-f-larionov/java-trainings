package syntax.exceptions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class Profiling {

    private int[] intArray;

    private Exception exception = new Exception();

    @Test
    public void profiling() {

        var manager = new StatisticManager(1_000_000);

        manager.measure("empty", this::empty);
        manager.measure("newObject", this::newObject);
        manager.measure("newArray", this::newArray);
        manager.measure("newArrayList", this::newArrayList);
        manager.measure("mathSqrt", this::mathSqrt);
        manager.measure("newException", this::newException);
        manager.measure("throwConstantException", this::throwConstantException);
        manager.measure("mathSqrtAndNewException", this::mathSqrtAndNewException);
        manager.measure("mathSqrtAndThrowNewException", this::mathSqrtAndThrowNewException);

        manager.printReport();
    }

    private void empty() {

    }

    private void newArray() {
        intArray = new int[20000];
    }

    private void newArrayList() {
        var arr = new ArrayList<String>();
    }

    private void newObject() {
        new Object();
    }

    private void mathSqrt() {
        Math.sqrt(1000);
    }

    private void newException() {
        new Exception();
    }

    private void mathSqrtAndNewException() {
        Math.sqrt(1000);
        new Exception();
    }

    private void mathSqrtAndThrowNewException() {
        try {
            Math.sqrt(1000);
            throw new Exception();
        } catch (Exception ignore) {

        }
    }

    private void throwConstantException() {
        try {

            throw exception;
        } catch (Exception ignore) {

        }
    }

}
