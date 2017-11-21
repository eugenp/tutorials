# Git spring-cloud-stream-app-starters
git clone https://github.com/spring-cloud/spring-cloud-stream-app-starters.git

# Navigate to Twitter
cd /twitter

# Navigate to the the app configuration settings
cd /spring-cloud-starter-stream-source-twitterstream/src/main/java/org/springframework/cloud/stream/app/twitterstream/source/TwitterStreamProperties.java
cd /spring-cloud-stream-app-starters/app-starters-common/app-starters-twitter-common/src/main/java/org/springframework/cloud/stream/app/twitter/TwitterCredentials.java
# Specify the properties you want there
# or inject the application.properties file before building the app

# Then build the customized starter app
cd ../../../../../../../../../../../../
mvn clean install