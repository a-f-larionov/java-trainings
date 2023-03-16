package syntax.exceptions;

import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class StatisticManager {


    private int times = 0;
    private List<Statistic> list = new ArrayList<>();

    public StatisticManager(int times) {
        this.times = times;
    }

    public void measure(String name, Runnable method) {
        var sw = new StopWatch("sqrt");
        sw.start();

        for (var x = 0; x < times; x++) {
            method.run();
        }
        sw.stop();

        list.add(new Statistic(name, sw.getTotalTimeNanos()));
    }

    public void printReport() {
        final Optional<Long> max = list.stream().map(s -> s.getNs()).max(Comparator.naturalOrder());
        if (!max.isPresent()) {
            return;
        }

        list.stream().forEach(statistic -> System.out.printf(
                "\t %35s \t %,15d ns. \t %f\n",
                statistic.getName(),
                statistic.getNs(), (float) statistic.getNs() / (float) max.get())
        );
    }
}
