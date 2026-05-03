package sadnex.web.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.time.Duration;
import java.time.LocalDateTime;

@Named
@ApplicationScoped
public class PointInterval implements PointIntervalMBean {
    private LocalDateTime firstTime;
    private LocalDateTime lastTime;
    private long count = 0;

    @Override
    public void addInterval() {
        LocalDateTime now = LocalDateTime.now();
        if (firstTime == null) {
            firstTime = now;
        }
        lastTime = now;
        count++;
    }

    @Override
    public Duration getAverageInterval() {
        if (count == 0) {
            return Duration.ZERO;
        }
        return Duration.between(firstTime, lastTime);
    }
}
