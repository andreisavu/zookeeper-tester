package ro.pub.master.sii.zookeeper.config;

import java.net.URI;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class WhirrConfiguration {

    @NotEmpty
    @JsonProperty
    private String provider = "aws-ec2";

    @NotEmpty
    @JsonProperty
    private String identity;

    @NotEmpty
    @JsonProperty
    private String credential;

    @NotEmpty
    @JsonProperty
    private String locationId = "eu-west-1";

    @NotEmpty
    @JsonProperty
    private String imageId = "eu-west-1/ami-edc6fe99";

    @NotEmpty
    @JsonProperty
    private String hardwareId = "m1.small";

    @NotEmpty
    @JsonProperty
    private URI tarballUri = URI.create("http://apache.osuosl.org/zookeeper/zookeeper-3.4.3/zookeeper-3.4.3.tar.gz");

    public String getProvider() {
        return provider;
    }

    public String getIdentity() {
        return identity;
    }

    public String getCredential() {
        return credential;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getImageId() {
        return imageId;
    }

    public String getHardwareId() {
        return hardwareId;
    }

    public URI getTarballUri() {
        return tarballUri;
    }
}
