package sadnex.web.storage;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sadnex.web.entity.Point;

import java.util.List;

/**
 * CDI-бин. Нужен для доступа к точкам из БД.
 * Настройка в META-INF/persistence.xml.
 * @author s4dnex
 */
@Named("hibernatePointStorage")
@ApplicationScoped
public class HibernatePointStorage implements PointStorage {
    @PersistenceContext(unitName = "web-lab3")
    private EntityManager entityManager;

    public HibernatePointStorage() {}

    public HibernatePointStorage(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void addPoint(Point point) {
        entityManager.persist(point);
    }

    /**
     * Возвращает все точки из БД.
     * Сортирует по убываю времени создания.
     * @author s4dnex
     */
    @Override
    public List<Point> getPoints() {
        return entityManager
                .createQuery("SELECT p FROM Point AS p ORDER BY p.requestTime DESC", Point.class)
                .getResultList();
    }

    @Override
    public void close() {
        // Fun fact: Wildfly manages transactions automatically via JTA
    }
}
