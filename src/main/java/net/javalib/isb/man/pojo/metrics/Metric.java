package net.javalib.isb.man.pojo.metrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = Timer.class, name = "Timer"), @JsonSubTypes.Type(value = Counter.class, name = "Counter"), @JsonSubTypes.Type(value = Gauge.class, name = "Gauge"), @JsonSubTypes.Type(value = Gauge.class, name="SimpleGauge"), @JsonSubTypes.Type(value=Meter.class, name="Meter")})
public abstract class Metric implements Serializable {
    public static final String TIMER = "Timer";
    public static final String COUNTER = "Counter";
    public static final String GAUGE = "Gauge";
    public static final String METER = "Meter";

    protected String name;
    protected String type;
    protected String registry;
    protected Collection<String> hosts = new ArrayList<>();

//    @JsonIgnore
    protected String env;
//    @JsonIgnore
    protected String serviceType;
//    @JsonIgnore
    protected String hostname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public Collection<String> getHosts() {
        return hosts;
    }

    public void setHosts(Collection<String> hosts) {
        this.hosts = hosts;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public boolean is(String type) {
        if (type == null) {
            return false;
        }
        return type.equals(this.type);
    }
}
