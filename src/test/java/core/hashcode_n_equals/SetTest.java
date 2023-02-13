package core.hashcode_n_equals;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SetTest {

    static class PersonSimple {
        public String name;

        PersonSimple(String name) {
            this.name = name;
        }
    }

    @Test
    public void wrongTest() {
        // given
        Set<PersonSimple> set = new HashSet<>();

        // when
        set.add(new PersonSimple("A"));
        set.add(new PersonSimple("A"));
        set.add(new PersonSimple("B"));

        // then
        assertThat(set)
                .hasSize(3)
                .doesNotContain(new PersonSimple("A"));
    }

    static class PersonWithEquals extends PersonSimple {

        PersonWithEquals(String name) {
            super(name);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o != null && o.getClass() != this.getClass()) {
                return false;
            }

            if (this.name.equals(((PersonSimple) o).name)) {
                return true;
            }
            return false;
        }
    }

    @Test
    public void realizeEqualsOnly() {
        // given
        Set<PersonWithEquals> set = new HashSet<>();

        // when
        set.add(new PersonWithEquals("A"));
        set.add(new PersonWithEquals("A"));
        set.add(new PersonWithEquals("B"));

        // then
        assertThat(set)
                .hasSize(3)
                .contains(new PersonWithEquals("A"));
    }

    static class PersonHashOnly extends PersonSimple {

        PersonHashOnly(String name) {
            super(name);
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + (name == null ? 0 : name.hashCode());
            return hash;
        }
    }

    @Test
    public void realizeHashCodeOnly() {
        // given
        Set<PersonSimple> set = new HashSet();

        // when
        set.add(new PersonHashOnly("A"));
        set.add(new PersonHashOnly("A"));
        set.add(new PersonHashOnly("B"));

        // then
        assertThat(set)
                .hasSize(3)
                .doesNotContain(new PersonHashOnly("A"));
    }

    static class PersonWithHashCodeAndEquals extends PersonSimple {

        PersonWithHashCodeAndEquals(String name) {
            super(name);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o != null && o.getClass() != this.getClass()) {
                return false;
            }
            if (this.name.equals(((PersonSimple) o).name)) {
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 31 * 7 * (name == null ? 0 : name.hashCode());
            return hash;
        }
    }

    @Test
    public void hashAndEqualsRealized() {
        // given
        Set<PersonSimple> set = new HashSet();

        // when
        set.add(new PersonWithHashCodeAndEquals("A"));
        set.add(new PersonWithHashCodeAndEquals("A"));
        set.add(new PersonWithHashCodeAndEquals("B"));

        // then
        assertThat(set)
                .hasSize(2)
                .contains(new PersonWithHashCodeAndEquals("A"));
    }

}
