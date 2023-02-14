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
    @SuppressWarnings("*")
    public void equivalenceRelation() {
        /* дано */
        число a, b, c;

        /* когда */
        a = пусть(1);
        b = пусть(1);
        c = пусть(1);

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


    static class число {
        private int v;

        public static число пусть(int v) {
            return new число(v);
        }

        число(int v) {
            this.v = v;
        }

        @Override
        public boolean equals(Object obj) {
            if (super.equals(obj)) return true;
            if (obj == null) return false;
            if (obj.getClass() != this.getClass()) return false;

            if (this.v == ((число) obj).v) return true;
            return false;
        }

        public boolean равно(Object obj) {
            return equals(obj);
        }
    }

    /**
     * Alias, не делай так :)
     */
    public static число пусть(int v) {
        return число.пусть(v);
    }

    /**
     * Alias, не делай так :)
     */
    public void истина(Boolean value) {
        assertTrue(value);
    }
}
