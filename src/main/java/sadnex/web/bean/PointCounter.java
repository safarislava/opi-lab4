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
    private boolean isLastPointHit;
    private long pointCount = 0;

    @Override
    public void addPoint(boolean isHit) {
        if (pointCount > 1 && !isLastPointHit && !isHit) {
            sendNotification();
        }
        isLastPointHit = isHit;
        pointCount++;
    }

    @Override
    public long getPointCount() {
        return pointCount;
    }

    private void sendNotification() {
        try {
            Notification notification = new Notification(
                    "point.miss.double",
                    new ObjectName("sadnex.web.bean:type=PointCounter"),
                    sequenceNumber++, System.currentTimeMillis(),
                    "Обнаружено два промаха подряд!"
            );
            super.sendNotification(notification);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException(e);
        }
    }
}
