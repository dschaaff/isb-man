package net.javalib.isb.man.services.influxdb;

import java.util.List;

public interface InfluxDBService {
    public void store(String db, Point point);

    public void store(String db, List<Point> points);
}
