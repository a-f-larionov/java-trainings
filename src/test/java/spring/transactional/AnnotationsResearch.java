package spring.transactional;

import org.junit.jupiter.api.Test;
import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationsResearch {

    @Test
    public void transactionalThatIsIt() {
        // given
        var annot = Transactional.class;

        // when
        var isInterface = annot.isInterface();
        var isAnnotation = annot.isAnnotation();
        var packageName = annot.getPackageName();
        var declaredMethods = annot.getDeclaredMethods();

        // then
        assertThat(isInterface).isTrue();
        assertThat(isAnnotation).isTrue();
        assertThat(packageName)
                .contains("org.springframework")
                .contains("transaction")
                .contains("annotation");

        assertThat(packageName).doesNotContain("javax.transaction");

        assertThat(annot.getDeclaredAnnotations()).hasSize(5);
        assertThat(annot.getAnnotation(Target.class).value()).isEqualTo(new ElementType[]{TYPE, METHOD});
        assertThat(annot.getAnnotation(Retention.class).value()).isEqualTo(RetentionPolicy.RUNTIME);
        assertThat(annot.getAnnotation(Documented.class)).isNotNull();
        assertThat(annot.getAnnotation(Inherited.class)).isNotNull();
        assertThat(annot.getAnnotation(Reflective.class)).isNotNull();

        assertThat(Arrays.stream(declaredMethods).map(Method::getName).toList())
                .hasSize(12)
                .contains("value");
    }

}
