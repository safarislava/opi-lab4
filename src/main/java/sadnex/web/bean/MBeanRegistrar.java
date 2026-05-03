package sadnex.web.bean;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import javax.management.*;
import java.lang.management.ManagementFactory;

@WebListener
public class MBeanRegistrar implements ServletContextListener {
    @Inject
    private PointCounter pointCounter;
    private ObjectName pointCounterName;

    @Inject
    private PointInterval pointInterval;
    private ObjectName pointIntervalName;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            pointCounterName = new ObjectName("sadnex.web.bean:type=PointCounter");
            if (!mBeanServer.isRegistered(pointCounterName)) {
                mBeanServer.registerMBean(pointCounter, pointCounterName);
            }
        } catch (Exception ignored) {}

        try {
            pointIntervalName = new ObjectName("sadnex.web.bean:type=PointInterval");
            if (!mBeanServer.isRegistered(pointIntervalName)) {
                mBeanServer.registerMBean(pointInterval, pointIntervalName);
            }
        } catch (Exception ignored) {}
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            mBeanServer.unregisterMBean(pointCounterName);
            mBeanServer.unregisterMBean(pointIntervalName);
        } catch (Exception ignored) {}
    }
}
