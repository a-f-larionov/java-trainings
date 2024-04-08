package algorithms;

public class SortImplementations extends SortAlgorithm {

    public int[] bubbleSort(int[] nums) {

        var i = 0;
        var j = 0;
        var l = nums.length;
        for (i = 0; i < l - 1; i++) {
            for (j = 0; j < l - i - 1; j++) {
                iterations++;
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                }
            }
        }

        return nums;
    }

    public int[] quickSort(int[] nums) {

        quickSort(nums, 0, nums.length - 1);

        return nums;
    }

    private void quickSort(int[] nums, int begin, int end) {
        if (begin < end) {
            iterations++;
            int partitionIndex = partition(nums, begin, end);


            quickSort(nums, begin, partitionIndex - 1);
            quickSort(nums, partitionIndex + 1, end);
        }
    }

    private int partition(int[] nums, int begin, int end) {

        int i = (begin - 1);
        for (int j = begin; j < end; j++) {
            iterations++;
            if (nums[j] <= nums[end]) {
                i++;
                swap(nums, i, j);
            }
        }

        swap(nums, i + 1, end);
        return i + 1;
    }


    public int[] mergeSort(int[] a, int n) {
        if (n == 1) {
            return a;
        }
        iterations++;
        int mid = n >> 1;
        int[] l = new int[mid];
        int[] r = new int[n - mid];
        System.arraycopy(a, 0, l, 0, mid);
        System.arraycopy(a, mid, r, 0, n - mid);
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(a, l, r, mid, n - mid);
        return a;
    }

    public void merge(
            int[] a, int[] l, int[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            iterations++;
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            iterations++;
            a[k++] = l[i++];
        }
        while (j < right) {
            iterations++;
            a[k++] = r[j++];
        }
    }
}
