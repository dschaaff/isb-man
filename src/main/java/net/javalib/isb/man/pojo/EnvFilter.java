package net.javalib.isb.man.pojo;

import org.apache.commons.lang3.StringUtils;

public class EnvFilter {
    private String env;
    private String type;
    private String host;

    public EnvFilter(String env, String type, String host) {
        if (StringUtils.isBlank(env))
            env = null;
        if (StringUtils.isBlank(type))
            type = null;
        if (StringUtils.isBlank(host))
            host = null;
        this.env = env;
        this.type = type;
        this.host = host;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int level() {
        return level(type, host);
    }

    public static int level(String type, String host) {
        int level = 0;
        if (host != null) {
            level = 2;
        }
        else {
            if (type != null) {
                level = 1;
            }
        }
        return level;
    }

}
