package core.hashcode_n_equals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EqualsContractTest {

    /**
     * HashCode has contract
     * Equivalence Relation
     * <br><br>
     * <p>
     * Reflexivity:      a ~ a <br>
     * Symmetry: a ~ b →  b ~ a <br/>
     * Transitivity: a ~ b  &  b ~ c  →  a ~ c<br><br>
     *
     * @see <a href="https://en.wikipedia.org/wiki/Equivalence_relation">Wikipedia Link</a>
     */
    @Test
    public void отношенияЭквивалентности() {
        /* дано */
        числа a, b, c;

        /* когда */
        a = равно(1);
        b = равно(1);
        c = равно(1);

        /* следовательно */

        /* рефлексия */
        истина(a.равно(a));

        /* симметрия */
        истина(a.равно(b));
        истина(b.равно(a));

        /* транзитивность */
        истина(a.равно(b));
        истина(b.равно(c));
        истина(a.равно(c));
    }

    private void compoundDeclarationIsBad(Object args) {
    }


    static class числа {
        private int v;

        public static числа пусть(int v) {
            return new числа(v);
        }

        числа(int v) {
            this.v = v;
        }

        @Override
        public boolean equals(Object obj) {
            if (super.equals(obj)) return true;
            if (obj == null) return false;
            if (obj.getClass() != this.getClass()) return false;

            if (this.v == ((числа) obj).v) return true;
            return false;
        }

        /**
         * Alias, не делай так :)
         */
        public boolean равно(Object obj) {
            return equals(obj);
        }
    }

    /**
     * Alias, не делай так :)
     */
    public static числа равно(int v) {
        return числа.пусть(v);
    }

    /**
     * Alias, не делай так :)
     */
    public void истина(Boolean value) {
        assertTrue(value);
    }
}
