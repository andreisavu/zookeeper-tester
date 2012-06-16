
function attach_byteman() {
    . /etc/profile
    cd /usr/local/byteman

    local PID=$(jps | grep "Quorum" | awk '{print $1}')
    ./bin/bminstall.sh $PID

    # install required jar files
    ./bin/bmsubmit.sh -s /usr/local/byteman/lib/byteman.jar
    ./bin/bmsubmit.sh -s /usr/local/byteman/random.jar

    # show installed rules & jars
    ./bin/bmsubmit.sh
    ./bin/bmsubmit.sh -c
}