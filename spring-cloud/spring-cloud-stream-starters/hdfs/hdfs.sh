# Git spring-cloud-stream-app-starters
# https://github.com/spring-cloud-stream-app-starters/hdfs/blob/master/spring-cloud-starter-stream-sink-hdfs/README.adoc
git clone https://github.com/spring-cloud-stream-app-starters/hdfs.git

# Build it
./mvnw clean install -PgenerateApps

# Run it
cd target
# Optionally inject application.properties prior to build
java -jar hdfs-sink.jar --fsUri=hdfs://127.0.0.1:50010/