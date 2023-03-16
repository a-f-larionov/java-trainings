package syntax.exceptions;

import lombok.Getter;


class Statistic {
    private String name;
    private long ns;

    public Statistic(String name, long ns) {
        this.name = name;
        this.ns = ns;
    }

    public String getName() {
        return name;
    }

    public long getNs() {
        return ns;
    }
}
