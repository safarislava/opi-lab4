package sadnex.web.bean;

public interface PointCounterMBean {
    void addPoint(boolean isHit);
    long getPointCount();
    long getHitPointCount();
    double getHitRate();
}
