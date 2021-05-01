#!/bin/sh

source src/main/resources/application.properties

curl --request PUT --url https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/namespaces/$ASTRA_DB_KEYSPACE/collections/statuses/latest -H "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" -H 'Content-Type: application/json' \
  -d '{
    "captain_america": {"name": "Captain America", "realName": "Steve Rogers", "status": "RETIRED"}, 
    "iron_man": {"name": "Iron Man", "realName": "Tony Stark", "status": "DECEASED"}, 
    "thor": {"name": "Thor", "status": "UNKNOWN"}, 
    "hulk": {"name": "Hulk", "realName": "Bruce Banner", "status": "HEALTHY", "location": "New York"}, 
    "hawkeye": {"name": "Hawkeye", "realName": "Clint Barton", "status": "INJURED", "location": "New York"}, 
    "wanda": {"name": "Wanda Maximoff", "status": "HEALTHY", "location": "New York"}, 
    "falcon": {"name": "Falcon", "realName": "Sam Wilson", "status": "INJURED", "location": "New York"}, 
    "black_widow": {"name": "Black Widow", "realName": "Natasha Romanov", "status": "DECEASED"}
  }'

