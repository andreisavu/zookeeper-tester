package ro.pub.master.sii.zookeeper.health;

import com.yammer.metrics.core.HealthCheck;

public class HomeHealthCheck extends HealthCheck {

    public HomeHealthCheck() {
        super("home");
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
