package ro.pub.master.sii.zookeeper.config;

import com.yammer.dropwizard.config.Configuration;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class TesterConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String clusterName;

    @JsonProperty
    private WhirrConfiguration whirr;

    public String getClusterName() {
        return clusterName;
    }

    public WhirrConfiguration getWhirrConfig() {
        return whirr;
    }
}
