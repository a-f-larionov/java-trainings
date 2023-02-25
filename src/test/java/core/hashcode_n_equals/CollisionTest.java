package core.hashcode_n_equals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CollisionTest {

    @Test
    @Disabled
    public void collisionByHashCode() {
        // given
        int maxSize = 150_000;
        long millis = System.currentTimeMillis();

        Object[] list = new Object[maxSize];

        for (int i = 0; i < maxSize; i++) {
            list[i] = new Object();
        }

        for (int i = 0; i < maxSize; i++) {
            for (int j = 0; j < maxSize; j++) {
                if (i >= j) continue;
                if (list[i].hashCode() == list[j].hashCode()) {
                    System.out.println("hashCode:" + list[i].hashCode() + "\t\t" +
                            "index1: " + i + "\t" +
                            "index2: " + j + "\t\t " +
                            "index distances: " + (j - i));
                }
            }
        }

        System.out.println(System.currentTimeMillis() - millis);
        System.out.println(maxSize);
    }
}
