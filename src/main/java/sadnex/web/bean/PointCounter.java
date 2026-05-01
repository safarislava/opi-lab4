package sadnex.web.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import sadnex.web.entity.Point;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Named
@ApplicationScoped
public class PointCounter extends NotificationBroadcasterSupport implements PointCounterMBean {
    private long sequenceNumber = 1;
    private final List<Point> points = new CopyOnWriteArrayList<>();

    @Override
    public void addPoint(Point point) {
        if (points.size() > 1 && !points.getLast().isHit() && !point.isHit()) {
            sendNotification();
        }
        points.add(point);
    }

    @Override
    public long getPointCount() {
        return points.size();
    }

    private void sendNotification() {
        Notification notification = new Notification(
                "misses.twoInRow",
                "sadnex.web.bean:type=PointCounter",
                sequenceNumber++,
                System.currentTimeMillis(),
                "Обнаружено два промаха подряд!"
        );
        super.sendNotification(notification);
    }
}
