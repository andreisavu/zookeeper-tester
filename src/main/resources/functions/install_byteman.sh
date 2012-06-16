
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
}