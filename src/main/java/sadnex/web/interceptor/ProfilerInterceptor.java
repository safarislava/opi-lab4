package sadnex.web.interceptor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import sadnex.web.annotation.Profiler;
import sadnex.web.bean.PointCounter;
import sadnex.web.bean.PointInterval;
import sadnex.web.entity.Point;

@Profiler
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class ProfilerInterceptor {
    @Inject
    private PointCounter pointCounter;

    @Inject
    private PointInterval pointInterval;

    @AroundInvoke
    public Object profile(InvocationContext context) throws Exception {
        pointInterval.addInterval();

        Object result = context.proceed();
        if (result instanceof Point point) {
            pointCounter.addPoint(point.isHit());
        }

        return null;
    }
}
