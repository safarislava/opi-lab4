package sadnex.web.bean;

import sadnex.web.entity.Point;

public interface PointCounterMBean {
    void addPoint(Point point);
    long getPointCount();
}
