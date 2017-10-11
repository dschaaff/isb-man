package net.javalib.isb.man.services;

import java.util.Collection;
import net.javalib.isb.man.pojo.ServerInstance;

public interface ServerInstancesRegistry {
    Collection<ServerInstance> getServerInstances();
}
