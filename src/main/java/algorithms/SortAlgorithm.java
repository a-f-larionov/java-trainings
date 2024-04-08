package algorithms;

public class SortAlgorithm {
    public long swaps;
    public long iterations;
    public long time;
    public long startTime;
    public long memory;
    public long startMemory;

    public void start() {
        swaps = 0;
        iterations = 0;
        time = 0;
        memory = 0;
        startTime = System.currentTimeMillis();
        startMemory = Runtime.getRuntime().totalMemory();
    }

    public void finish(String msg1, String msg2) {
        time = System.currentTimeMillis() - startTime;
        memory = Runtime.getRuntime().totalMemory() - startMemory;
        printReport(msg1, msg2);
    }

    public void printReport(String msg1, String msg2) {
        System.out.printf(
                "%10s %10s t= %5s ms.iterations= %6s\n",
                msg1, msg2, time, iterations
        );
    }

    protected void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
