package sadnex.web.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Named
@ApplicationScoped
public class PointInterval implements PointIntervalMBean {
    private LocalDateTime lastTime;
    private final List<Duration> intervals = new CopyOnWriteArrayList<>();

    @Override
    public void addInterval() {
        if (lastTime == null) {
            lastTime = LocalDateTime.now();
            return;
        }

        Duration duration = Duration.between(lastTime, LocalDateTime.now());
        intervals.add(duration);
        lastTime = LocalDateTime.now();
    }

    @Override
    public Duration getAverageInterval() {
        Duration alltime = Duration.ZERO;
        for (Duration duration : intervals) {
            alltime = duration.plus(duration);
        }
        return alltime.dividedBy(intervals.size());
    }
}
