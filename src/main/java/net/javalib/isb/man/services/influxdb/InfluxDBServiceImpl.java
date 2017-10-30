package net.javalib.isb.man.services.influxdb;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import net.javalib.isb.man.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@ConditionalOnProperty("influxdb.url")
public class InfluxDBServiceImpl implements InfluxDBService {
    private static final Logger logger = LoggerFactory.getLogger(InfluxDBServiceImpl.class);
    private String influxDbUrl;
    private RestTemplate restTemplate;

    @Autowired
    public InfluxDBServiceImpl(@Value("${influxdb.url}") String influxDbUrl,
                               RestTemplate restTemplate) {
        this.influxDbUrl = influxDbUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    @Timed
    public void store(String db, Point point) {
        String dbUrl = dbUrl(db);
        restTemplate.postForObject(dbUrl,point.lineProtocol(),Void.class);
    }

    @Override
    @Timed
    public void store(String db, List<Point> points) {
        String dbUrl = dbUrl(db);
        logger.info("storing {} points in {}",points.size(),dbUrl);
        List<List<Point>> partitions = CollectionUtils.partition(points, 1000);
        for (List<Point> partition : partitions) {
            try {
                StringBuffer data = new StringBuffer();
                for (Point point : partition) {
                    data.append(point.lineProtocol() + "\n");
                }
                restTemplate.postForObject(dbUrl, data.toString(), Void.class);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        }
    }

    private String dbUrl(String db) {
        return influxDbUrl+"/write?db="+db;
    }

}
