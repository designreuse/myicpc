package com.myicpc.master;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.as.server.ServerEnvironment;
import org.jboss.logging.Logger;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.jboss.msc.value.Value;

/**
 * @author <a href="mailto:wfink@redhat.com">Wolf-Dieter Fink</a>
 * @author <a href="mailto:ralf.battenfeld@bluewin.ch">Ralf Battenfeld</a>
 */
public class HATimerService implements Service<Environment> {
    public static final ServiceName DEFAULT_SERVICE_NAME = ServiceName.JBOSS.append("quickstart", "ha", "singleton", "default");
    public static final ServiceName QUORUM_SERVICE_NAME = ServiceName.JBOSS.append("quickstart", "ha", "singleton", "quorum");
    public static final String NODE_1 = "nodeOne";
    public static final String NODE_2 = "nodeTwo";

    private static final Logger LOGGER = Logger.getLogger(HATimerService.class);    
    private final Value<ServerEnvironment> env;
    private final AtomicBoolean started = new AtomicBoolean(false);

    public HATimerService(Value<ServerEnvironment> env) {
        this.env = env;
    }

    @Override
    public Environment getValue() {
        if (!this.started.get()) {
            throw new IllegalStateException();
        }
        return new Environment(this.env.getValue().getNodeName());
    }

    @Override
    public void start(StartContext context) throws StartException {
        if (!started.compareAndSet(false, true)) {
            throw new StartException("The service is still started!");
        }
        LOGGER.info("Start HASingleton timer service '" + this.getClass().getName() + "'");
        
        try {
            InitialContext ic = new InitialContext();
            ((Scheduler) ic.lookup("global/wildfly-cluster-ha-singleton-service/SchedulerBean!org.jboss.as.quickstarts.cluster.hasingleton.service.ejb.Scheduler")).initialize("HASingleton timer @" + this.env.getValue().getNodeName() + " " + new Date());
        } catch (NamingException e) {
            throw new StartException("Could not initialize timer", e);
        }
    }

    @Override
    public void stop(StopContext context) {
        if (!started.compareAndSet(true, false)) {
            LOGGER.warn("The service '" + this.getClass().getName() + "' is not active!");
        } else {
            LOGGER.info("Stop HASingleton timer service '" + this.getClass().getName() + "'");
            try {
                InitialContext ic = new InitialContext();
                ((Scheduler) ic.lookup("global/wildfly-cluster-ha-singleton-service/SchedulerBean!org.jboss.as.quickstarts.cluster.hasingleton.service.ejb.Scheduler")).stop();
            } catch (NamingException e) {
                LOGGER.error("Could not stop timer", e);
            }
        }
    }
}
