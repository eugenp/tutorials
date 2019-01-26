#!/bin/bash


function copy() {
	echo -e "Creating configuration directory under /etc/cas"
	mkdir -p /etc/cas/config

	echo -e "Copying configuration files from etc/cas to /etc/cas"
	cp -rfv etc/cas/* /etc/cas
}

function help() {
	echo "Usage: build.sh [copy|clean|package|run|debug|bootrun|gencert]"
	echo "	copy: Copy config from ./etc/cas/config to /etc/cas/config"
	echo "	clean: Clean Maven build directory"
	echo "	package: Clean and build CAS war, also call copy"
	echo "	run: Build and run CAS.war via spring boot (java -jar target/cas.war)"
	echo "	debug: Run CAS.war and listen for Java debugger on port 5000"
	echo "	bootrun: Run with maven spring boot plugin, doesn't work with multiple dependencies"
	echo "	gencert: Create keystore with SSL certificate in location where CAS looks by default"
}

function clean() {
	./mvnw clean "$@"
}

function package() {
	./mvnw clean package -T 5 "$@"
	copy
}

function bootrun() {
	./mvnw clean package spring-boot:run -T 5 "$@"
}

function debug() {
	package && java -Xdebug -Xrunjdwp:transport=dt_socket,address=5000,server=y,suspend=n -jar target/cas.war
}

function run() {
	package && java -jar target/cas.war
}

function gencert() {
	if [[ ! -d /etc/cas ]] ; then 
		copy
	fi
	which keytool
	if [[ $? -ne 0 ]] ; then
	    echo Error: Java JDK \'keytool\' is not installed or is not in the path
	    exit 1
	fi
	# override DNAME and CERT_SUBJ_ALT_NAMES before calling or use dummy values
	DNAME="${DNAME:-CN=cas.example.org,OU=Example,OU=Org,C=US}"
	CERT_SUBJ_ALT_NAMES="${CERT_SUBJ_ALT_NAMES:-dns:example.org,dns:localhost,ip:127.0.0.1}"
	echo "Generating keystore for CAS with DN ${DNAME}"
	keytool -genkeypair -alias cas -keyalg RSA -keypass changeit -storepass changeit -keystore /etc/cas/thekeystore -dname ${DNAME} -ext SAN=${CERT_SUBJ_ALT_NAMES}
	keytool -exportcert -alias cas -storepass changeit -keystore /etc/cas/thekeystore -file /etc/cas/cas.cer
}

if [ $# -eq 0 ]; then
    echo -e "No commands provided. Defaulting to [run]\n"
    run
    exit 0
fi


case "$1" in
"copy")
    copy 
    ;;
"clean")
	shift
    clean "$@"
    ;;   
"package")
	shift
    package "$@"
    ;;
"bootrun")
	shift
    bootrun "$@"
    ;;
"debug")
    debug "$@"
    ;;
"run")
    run "$@"
    ;;
"gencert")
    gencert "$@"
    ;;
*)
    help
    ;;
esac

