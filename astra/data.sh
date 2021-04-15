#!/bin/sh

source src/main/resources/application.properties

curl --request POST --url https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/namespaces/$ASTRA_DB_KEYSPACE/collections/statuses -H "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" -H 'Content-Type: application/json' \
  -d '{"avenger":"captain_america","name":"Captain America","realName":"Steve Rogers","status":"RETIRED"}'
curl --request POST --url https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/namespaces/$ASTRA_DB_KEYSPACE/collections/statuses -H "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" -H 'Content-Type: application/json' \
  -d '{"avenger":"iron_man","name":"Iron Man","realName":"Tony Stark","status":"DECEASED"}'
curl --request POST --url https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/namespaces/$ASTRA_DB_KEYSPACE/collections/statuses -H "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" -H 'Content-Type: application/json' \
  -d '{"avenger":"thor","name":"Thor","status":"UNKNOWN"}'
curl --request POST --url https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/namespaces/$ASTRA_DB_KEYSPACE/collections/statuses -H "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" -H 'Content-Type: application/json' \
  -d '{"avenger":"hulk","name":"Hulk","realName":"Bruce Banner","status":"HEALTHY","location":"New York"}'
curl --request POST --url https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/namespaces/$ASTRA_DB_KEYSPACE/collections/statuses -H "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" -H 'Content-Type: application/json' \
  -d '{"avenger":"hawkeye","name":"Hawkeye","realName":"Clint Barton","status":"INJURED","location":"New York"}'
curl --request POST --url https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/namespaces/$ASTRA_DB_KEYSPACE/collections/statuses -H "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" -H 'Content-Type: application/json' \
  -d '{"avenger":"wanda","name":"Wanda Maximoff","status":"HEALTHY","location":"New York"}'
curl --request POST --url https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/namespaces/$ASTRA_DB_KEYSPACE/collections/statuses -H "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" -H 'Content-Type: application/json' \
  -d '{"avenger":"falcon","name":"Falcon","realName":"Sam Wilson","status":"INJURED","location":"New York"}'
curl --request POST --url https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/namespaces/$ASTRA_DB_KEYSPACE/collections/statuses -H "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" -H 'Content-Type: application/json' \
  -d '{"avenger":"black_widow","name":"Black Widow","realName":"Natasha Romanov","status":"DECEASED"}'

