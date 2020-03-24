# Git spring-cloud-stream-app-starters
# https://github.com/spring-cloud-stream-app-starters/hdfs/blob/master/spring-cloud-starter-stream-sink-hdfs/README.adoc
git clone https://github.com/spring-cloud-stream-app-starters/twitter.git

# Build it
./mvnw clean install -PgenerateApps

# Run it
cd target
# Optionally inject application.properties prior to build
java -jar twitter_stream_source.jar --consumerKey=<CONSUMER_KEY> --consumerSecret=<CONSUMER_SECRET> \
    --accessToken=<ACCESS_TOKEN> --accessTokenSecret=<ACCESS_TOKEN_SECRET>