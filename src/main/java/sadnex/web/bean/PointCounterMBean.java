package sadnex.web.bean;

import sadnex.web.entity.Point;

public interface PointCounterMBean {
    void addPoint(boolean isHit);
    long getPointCount();
}
