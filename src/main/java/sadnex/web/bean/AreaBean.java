package sadnex.web.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sadnex.web.entity.Point;
import sadnex.web.storage.PointStorage;
import sadnex.web.util.HitChecker;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * CDI-бин. Позволяет из фронт-энда вызывать бизнес-логику, связанную с обработкой точек.
 * @author s4dnex
 */
@Named("area")
@RequestScoped
public class AreaBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 52L;

    @Inject
    private PointCounter pointCounter;
    @Inject
    private PointInterval pointInterval;

    @Inject
    private PointStorage pointStorage;
    @Inject
    private HitChecker hitChecker;

    private Double x = 0.0;
    private Double y = 0.0;
    private Integer r = 1;

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Integer getR() {
        return r;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public void createPoint() {
        Point point = new Point(x, y, r, LocalDateTime.now());
        point.setHit(hitChecker.checkHit(point));
        pointStorage.addPoint(point);

        pointCounter.addPoint(point);
        pointInterval.addInterval();
    }

    public List<Point> getPoints() {
        return pointStorage.getPoints();
    }
}
