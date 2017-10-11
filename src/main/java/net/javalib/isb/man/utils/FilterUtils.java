package net.javalib.isb.man.utils;

import java.util.ArrayList;
import java.util.Collection;
import net.javalib.isb.man.pojo.EnvFilter;
import net.javalib.isb.man.pojo.metrics.*;

public class FilterUtils {

    public static Collection<Metric> filter(Collection<Metric> metrics, EnvFilter filter) {
        Collection<Metric> retVal = new ArrayList<Metric>();
        for (Metric metric : metrics) {
            if (metric != null) {
                if (matches(metric, filter)) {
                    retVal.add(metric);
                }
            }
        }
        return retVal;
    }

    public static void filterByLevel(Collection<? extends Metric> metrics, EnvFilter filter) {
        int level = filter.level();
        Collection<Metric> toDelete = new ArrayList<Metric>();
        for (Metric metric : metrics) {
            if (!levelsMatch(metric, level)) {
                toDelete.add(metric);
            }
        }
        metrics.removeAll(toDelete);
    }

    public static boolean matches(Metric metric, EnvFilter filter) {
        String env = filter.getEnv();
        String type = filter.getType();
        String host = filter.getHost();
        if (env == null && type == null && host == null)
            return true;
        if (host != null && !host.equals(metric.getHostname())) {
            return false;
        }
        if (type != null && !type.equals(metric.getServiceType())) {
            return false;
        }
        if (env != null && !env.equals(metric.getEnv())) {
            return false;
        }
        return true;
    }

    public static boolean levelsMatch(Metric instance, int level) {
        return EnvFilter.level(instance.getServiceType(), instance.getHostname()) == level;
    }


}
