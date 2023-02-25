package core.strings;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringPoolTest {

    @Test
    public void test1() {
        // given
        String s1 = "String 1";
        String s2 = "String 1";

        // when - then
        assertThat(s1 == s2).isTrue();
    }

    @Test
    public void test2() {
        // given
        String s1 = "String 1";
        String s2 = "String 2";

        // when - then
        assertThat(s1 == s2).isFalse();
    }

    @Test
    public void test3() {
        // given
        String s1 = "String 1";
        String s2 = "String " + "1";

        // when - then
        assertThat(s1 == s2).isTrue();
    }

    @Test
    public void test4() {
        // given
        String s1 = "String 1";
        String s2 = "1";
        String s3 = "String " + s2;

        // when - then
        assertThat(s1 == s3).isFalse();
    }

    @Test
    public void test5() {
        // given
        String s1 = "String 1";
        String s2 = new String("String 1");

        // when - then
        assertThat(s1 == s2).isFalse();
    }

    @Test
    public void test6() {
        // given
        String s1 = "String 1";
        s1.intern();
        "sdf".intern();
    }
}


