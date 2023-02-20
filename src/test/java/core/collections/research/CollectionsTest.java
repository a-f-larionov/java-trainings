package core.collections.research;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;

public class CollectionsTest {

    @Test
    public void intersectionCLSQM() {

        // given
        var collection = getMethodNames(Collection.class);
        var mapMethods = getMethodNames(Map.class);

        // when
        var intersecionMethods = new HashSet<String>();

        intersecionMethods.addAll(collection);
        intersecionMethods.addAll(mapMethods);

        intersecionMethods.retainAll(collection);
        intersecionMethods.retainAll(mapMethods);

        // then
        assertThat(intersecionMethods)
                .hasSize(7)
                .contains("isEmpty", "size")
                .contains("remove", "clear")
                .contains("hashCode", "equals")
                .contains("forEach");
    }

    @Test
    public void collectionMethods() {

        // given
        var collection = getMethodNames(Collection.class);

        // when - then
        assertThat(collection)
                .hasSize(19)
                // CLSQM
                .contains("isEmpty", "size")
                .contains("remove", "clear")
                .contains("hashCode", "equals")
                .contains("forEach")

                .contains("add", "addAll")
                .contains("contains", "containsAll")
                .contains("retainAll")
                .contains("remove", "removeAll", "removeIf")
                .contains("stream", "parallelStream", "toArray");
    }

    @Test
    public void methodOf() {
        assertThat(getMethodNames(Collection.class)).doesNotContain("of", "copyOf");
        assertThat(getMethodNames(Queue.class)).doesNotContain("of", "copyOf");

        assertThat(getMethodNames(Map.class)).contains("of", "copyOf");
        assertThat(getMethodNames(List.class)).contains("of", "copyOf");
        assertThat(getMethodNames(Set.class)).contains("of", "copyOf");
    }

    private Set<String> getMethodNames(Class<?> clazz) {
        return stream(clazz.getMethods()).map(Method::getName).collect(Collectors.toSet());
    }
}
