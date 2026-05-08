package sadnex.web.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;

@Named
@ApplicationScoped
public class PointCounter extends NotificationBroadcasterSupport implements PointCounterMBean {
    private long sequenceNumber = 1;
    private long pointCount = 0;
    private long hitPointCount = 0;

    public void addPoint(boolean isHit) {
        pointCount++;
        if (isHit) {
            hitPointCount++;
        }

        if (pointCount % 15 == 0) {
            sendNotification();
        }
    }

    @Override
    public long getPointCount() {
        return pointCount;
    }

    @Override
    public long getHitPointCount() {
        return hitPointCount;
    }

    @Override
    public double getHitRate() {
        if  (pointCount == 0) {
            return 0;
        }
        return (double) hitPointCount / pointCount;
    }

    private void sendNotification() {
        try {
            Notification notification = new Notification(
                    "point.count.multiple.15",
                    new ObjectName("sadnex.web.bean:type=PointCounter"),
                    sequenceNumber++,
                    System.currentTimeMillis()
            );
            super.sendNotification(notification);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException(e);
        }
    }
}
