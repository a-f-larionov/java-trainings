package algorithms;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class SortAlgorithmsTest {

    public static int l = 1_000;

    @Test
    public void bubbleRnd() {
        // given
        var performer = new SortImplementations();
        var array = generateRandomArray();
        var sorted = copyAndSort(array);

        // when
        performer.start();
        var ans = performer.bubbleSort(array);
        performer.finish("bubble", "random");

        // then
        assertThat(ans).isEqualTo(sorted);
    }

    @Test
    public void bubbleSorted() {
        // given
        var performer = new SortImplementations();
        var array = generateSortedArray();
        var sorted = copy(array);

        // when
        performer.start();
        var ans = performer.bubbleSort(array);
        performer.finish("bubble", "sorted");

        // then
        assertThat(ans).isEqualTo(sorted);
    }

    @Test
    public void bubbleReverseSorted() {
        // given
        var performer = new SortImplementations();
        var array = generateReverseSortedArray();
        var sorted = copyAndSort(array);

        // when
        performer.start();
        var ans = performer.bubbleSort(array);
        performer.finish("bubble", "reverse");

        // then
        assertThat(ans).isEqualTo(sorted);
    }

    @Test
    public void quickRnd() {
        // given
        var performer = new SortImplementations();
        var array = generateRandomArray();
        var sorted = copyAndSort(array);

        // when
        performer.start();
        var ans = performer.quickSort(array);
        performer.finish("quick", "random");

        // then
        assertThat(ans).isEqualTo(sorted);
    }

    @Test
    public void quickSorted() {
        // given
        var performer = new SortImplementations();
        var array = generateSortedArray();
        var sorted = copyAndSort(array);

        // when
        performer.start();
        var ans = performer.quickSort(array);
        performer.finish("quick", "sorted");

        // then
        assertThat(ans).isEqualTo(sorted);
    }

    @Test
    public void quickReverseSorted() {
        // given
        var performer = new SortImplementations();
        var array = generateReverseSortedArray();
        var sorted = copyAndSort(array);

        // when
        performer.start();
        var ans = performer.quickSort(array);
        performer.finish("quick", "reverse");

        // then
        assertThat(ans).isEqualTo(sorted);
    }

    @Test
    public void mergeRnd() {
        // given
        var performer = new SortImplementations();
        var array = generateRandomArray();
        var sorted = copyAndSort(array);

        // when
        performer.start();
        var ans = performer.mergeSort(array, array.length);
        performer.finish("merge", "random");

        // then
        assertThat(ans).isEqualTo(sorted);
    }


    @Test
    public void mergeSorted() {
        // given
        var performer = new SortImplementations();
        var array = generateSortedArray();
        var sorted = copyAndSort(array);

        // when
        performer.start();
        var ans = performer.mergeSort(array, array.length);
        performer.finish("merge", "sorted");

        // then
        assertThat(ans).isEqualTo(sorted);
    }

    @Test
    public void mergeReverseSorted() {
        // given
        var performer = new SortImplementations();
        var array = generateReverseSortedArray();
        var sorted = copyAndSort(array);

        // when
        performer.start();
        var ans = performer.mergeSort(array, array.length);
        performer.finish("merge", "reverse");

        // then
        assertThat(ans).isEqualTo(sorted);
    }


    private int[] generateRandomArray() {
        var arr = new int[l];
        for (var i = 0; i < l; i++) {
            arr[i] = (int) (Math.random() * l);
        }
        return arr;
    }

    private int[] generateSortedArray() {
        var arr = new int[l];
        for (var i = 0; i < l; i++) {
            arr[i] = i;
        }
        return arr;
    }

    private int[] generateReverseSortedArray() {
        var arr = new int[l];
        for (var i = 0; i < l; i++) {
            arr[i] = l - i;
        }
        return arr;
    }

    private static int[] copyAndSort(int[] array) {
        var newArr = copy(array);
        Arrays.sort(newArr);
        return newArr;
    }

    private static int[] copy(int[] array) {
        var newArr = new int[array.length];
        System.arraycopy(array, 0, newArr, 0, array.length);
        return newArr;
    }
}
