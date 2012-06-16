
function install_byteman() {
    local BYTEMAN_URL="http://downloads.jboss.org/byteman/2.0.4/byteman-download-2.0.4-bin.zip"
    local CURL_CMD="curl -L --silent --show-error --fail --connect-timeout 10 --max-time 600 --retry 5"
    local FILE=$(basename $BYTEMAN_URL)

    echo "Downloading from $BYTEMAN_URL"
    $CURL_CMD -O $BYTEMAN_URL || true

    echo "Extracting to /usr/local"
    sudo apt-get install unzip

    unzip $FILE -d /usr/local
    ln -s /usr/local/byteman-download-2.0.4 /usr/local/byteman

    echo "export BYTEMAN_HOME=\"/usr/local/byteman\"" >> /etc/profile

    # Download help jar

    local HELPER_JAR_URL="http://people.apache.org/~asavu/random.jar"
    $CURL_CMD -O $HELPER_JAR_URL || true

    mv random.jar /usr/local/byteman/

    # and rule

    local RULE_URL="http://people.apache.org/~asavu/readPayload.btm"
    $CURL_CMD -O $RULE_URL || true
    mv readPayload.btm /usr/local/byteman/
}