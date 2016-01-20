set -v

mvn -DskipTests clean package

# Change the line below to the location of Tomcat built from trunk
TOMCAT=~/Applications/apache-tomcat-trunk/output/build

rm -rf $TOMCAT/webapps/gs-websocket*

cp target/gs-websocket.war $TOMCAT/webapps/

$TOMCAT/bin/shutdown.sh
sleep 3

$TOMCAT/bin/startup.sh
