package core.object;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;

public class ObjectClassMethodsTest {

    @Test
    public void methodsCount() {
        // given
        var obj = new Object();

        // when
        Method[] declaredMethods = obj.getClass().getDeclaredMethods();

        // then
        assertThat(declaredMethods).hasSize(11);
    }

    @Test
    public void methodsNames() {
        // given
        var obj = new Object();

        // when
        var methods = obj.getClass().getDeclaredMethods();

        // then
        assertThat(methodsMapToNames(methods))
                .hasSameElementsAs(List.of(
                        "wait", "notify", "notifyAll",
                        "hashCode", "equals",
                        "toString", "getClass",
                        "clone", "finalize"))
        ;
    }

    @Test
    public void identityHashCode() {
        // given
        var obj = new Object();

        // when
        int hashCode = obj.hashCode();

        // then
        assertThat(hashCode)
                .isEqualTo(System.identityHashCode(obj));
    }

    @Test
    public void twoObjectHashCodes() {
        // given
        var obj1 = new Object();
        var obj2 = new Object();

        // when - then
        assertThat(obj1.hashCode())
                .isNotEqualTo(obj2.hashCode());
    }

    @Test
    public void toStringDefault() {
        // given
        var obj = new Object();

        // when
        var stringed = obj.toString();
        var hashCode = obj.hashCode();
        var className = obj.getClass().getName();
        var hexedHashCode = Integer.toHexString(hashCode);

        // then
        assertThat(stringed)
                .isEqualTo(className + "@" + hexedHashCode);
    }

    @Test
    public void nativeMethods() {
        // given
        var obj = new Object();
        var expectedNativeMethods = List.of(
                "hashCode", "getClass",
                "clone", "wait", "notify", "notifyAll"
        );

        // when
        Method[] declaredMethods = obj.getClass().getDeclaredMethods();

        // then
        List<String> actualNativeMethods = stream(declaredMethods)
                .filter(m -> (m.getModifiers() & Modifier.NATIVE) != 0)
                .map(Method::getName)
                .toList();

        assertThat(actualNativeMethods)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedNativeMethods);
    }

    static class ObjectToCone extends Object implements Cloneable {
        public Object clone() throws CloneNotSupportedException {
            super.clone();
            return new ObjectToCone();
        }
    }

    @Test
    public void cloneSome() throws CloneNotSupportedException {
        // given
        var obj1 = new ObjectToCone();

        // when
        var obj2 = obj1.clone();

        // then
        assertThat(obj1).isNotSameAs(obj2);
        assertThat(obj1.equals(obj2)).isFalse();
        assertThat(obj1.hashCode()).isNotEqualTo(obj2.hashCode());
    }

    @Test
    public void constructors() {
        // given
        var obj = new Object();

        // when
        Constructor<?>[] constructors = obj.getClass().getDeclaredConstructors();

        // then
        assertThat(constructors).hasSize(1);
        assertThat(constructors[0].getName()).isEqualTo("java.lang.Object");
    }

    @Test
    public void defaultEqualsRelationships() {
        // given
        var obj1 = new Object();
        var obj2 = new Object();

        // when - then
        assertThat(obj1.equals(obj1)).isTrue();
        assertThat(obj1.equals(obj2)).isFalse();
    }

    @Test
    public void defaultHashCodeRelationships() {
        // given
        var obj1 = new Object();
        var obj2 = new Object();

        // when - then
        assertThat(obj1.hashCode()).isEqualTo(obj1.hashCode());
        assertThat(obj1.hashCode()).isNotEqualTo(obj2.hashCode());
    }

    private List<String> methodsMapToNames(Method[] methods) {
        return stream(methods)
                .map(Method::getName)
                .collect(Collectors.toList());
    }
}
