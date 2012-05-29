package ro.pub.master.sii.zookeeper.config;

import org.apache.whirr.ClusterSpec;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class WhirrConfiguration {

    @NotEmpty
    @JsonProperty
    private String provider;

    @NotEmpty
    @JsonProperty
    private String identity;

    @NotEmpty
    @JsonProperty
    private String credential;

    public String getProvider() {
        return provider;
    }

    public String getIdentity() {
        return identity;
    }

    public String getCredential() {
        return credential;
    }

    public ClusterSpec buildClusterSpec() {
        return null; // TODO implement this
    }
}
