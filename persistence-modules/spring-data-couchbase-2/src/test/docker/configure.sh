#!/bin/bash

function retry() {
    for i in $(seq 1 10); do
        $1 "$2"
	if [[ $? == 0 ]]; then
	    return 0
	fi
	sleep 1
    done
    return 1
}

function bucketCreate(){
    couchbase-cli bucket-create -c localhost -u Administrator -p password \
        --bucket="$1" \
        --bucket-type=couchbase \
        --bucket-ramsize=512 \
        --bucket-replica=1 \
        --wait
    if [[ $? != 0 ]]; then
        return 1
    fi
}

function userCreate(){
    createOutput=$(couchbase-cli user-manage -c localhost -u Administrator -p password \
     --set --rbac-username "$1" --rbac-password "$1" \
     --roles admin --auth-domain local)
    if [[ $? != 0 ]]; then
        echo $createOutput >&2
        return 1
    fi
}

function clusterUp(){
    # wait for service to come up
    until $(curl --output /dev/null --silent --head --fail http://localhost:8091); do
        printf '.'
        sleep 1
    done

    # initialize cluster
    initOutput=$(couchbase-cli cluster-init -c localhost \
            --cluster-username=Administrator \
            --cluster-password=password \
            --cluster-port=8091 \
            --services=data,index,query,fts \
            --cluster-ramsize=1024 \
            --cluster-index-ramsize=256 \
            --cluster-fts-ramsize=256 \
            --index-storage-setting=default)
    if [[ $? != 0 ]]; then
        echo $initOutput >&2
        return 1
    fi
}

function main(){
    set -ex
    echo "Couchbase UI :8091"
    echo "Couchbase logs /opt/couchbase/var/lib/couchbase/logs"
    ./entrypoint.sh couchbase-server &
    if [[ $? != 0 ]]; then
        echo "Couchbase startup failed. Exiting." >&2
        exit 1
    fi

    clusterUp
    if [[ $? != 0 ]]; then
        echo "Cluster init failed. Exiting." >&2
        exit 1
    fi

    retry userCreate baeldung
    if [[ $? != 0 ]]; then
        echo "User create failed. Exiting." >&2
        exit 1
    fi

    retry userCreate baeldung2
    if [[ $? != 0 ]]; then
        echo "User create failed. Exiting." >&2
        exit 1
    fi

    retry bucketCreate baeldung
    if [[ $? != 0 ]]; then
        echo "Bucket create failed. Exiting." >&2
        exit 1
    fi

    retry bucketCreate baeldung2
    if [[ $? != 0 ]]; then
        echo "Bucket create failed. Exiting." >&2
        exit 1
    fi

    set +ex

    # entrypoint.sh launches the server but since config.sh is pid 1 we keep it
    # running so that the docker container does not exit.
    wait
}

main