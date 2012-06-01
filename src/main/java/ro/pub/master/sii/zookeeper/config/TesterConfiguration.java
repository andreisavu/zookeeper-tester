package ro.pub.master.sii.zookeeper.config;

import com.google.common.collect.ImmutableList;
import com.yammer.dropwizard.config.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.whirr.ClusterSpec;
import org.apache.whirr.InstanceTemplate;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class TesterConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String clusterName;

    @JsonProperty
    private int clusterSize = 3;

    @JsonProperty
    private WhirrConfiguration whirr;

    public String getClusterName() {
        return clusterName;
    }

    public ClusterSpec getClusterSpec() throws ConfigurationException {
        ClusterSpec spec = new ClusterSpec();

        spec.setClusterName(clusterName);

        spec.setProvider(whirr.getProvider());
        spec.setIdentity(whirr.getIdentity());
        spec.setCredential(whirr.getCredential());

        spec.setHardwareId(whirr.getHardwareId());
        spec.setImageId(whirr.getImageId());
        spec.setLocationId(whirr.getLocationId());

        spec.getConfiguration().setProperty(
            "whirr.zookeeper.tarball.url", whirr.getTarballUri().toString());

        spec.setInstanceTemplates(ImmutableList.of(
            InstanceTemplate.builder().numberOfInstance(clusterSize).roles("zookeeper").build()
        ));

        return spec;
    }
}
