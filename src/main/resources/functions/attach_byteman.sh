
function attach_byteman() {
    . /etc/profile
    cd /usr/local/byteman

    local PID=$(jps | grep "Quorum" | awk '{print $1}')
    ./bin/bminstall.sh $PID
    ./bin/bmsubmit.sh
}