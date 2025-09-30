# Create new index
curl -X PUT "http://localhost:9200/transaction-logs"
-H "Content-Type: application/json"
-d' { "mappings": { "properties": { "message": { "type": "text", "fields": { "keyword": { "type": "keyword" } } } } } }'

# Populate the test data
curl -X POST "http://localhost:9200/transaction-logs/_doc/1"
-H "Content-Type: application/json"
-d' { "message": "User1 deposited 1000 AP1 points" }'

curl -X POST "http://localhost:9200/transaction-logs/_doc/2"
-H "Content-Type: application/json"
-d' { "message": "User1 deposited 1000 AP2 points" }'

curl -X POST "http://localhost:9200/transaction-logs/_doc/3"
-H "Content-Type: application/json"
-d' { "message": "User1 deposited 1000 AP3 points" }'

curl -X POST "http://localhost:9200/transaction-logs/_doc/4"
-H "Content-Type: application/json"
-d' { "message": "User1 deposited 1000 PP1 points" }'

# Regexp With must_not
curl -X GET "http://localhost:9200/logs/_search"
-H "Content-Type: application/json"
-d' { "query": { "bool": { "must_not": [ { "regexp": { "message.keyword": ".*AP[2-9].*" } } ] } } }'


# Wildcard With must_not
curl -X GET "http://localhost:9200/transaction-logs/_search"
-H "Content-Type: application/json"
-d' { "query": { "bool": { "must_not": [ { "wildcard": { "message.keyword": "*AP*" } } ] } } }'

# Query String With must_not
curl -X GET "http://localhost:9200/transaction-logs/_search"
-H "Content-Type: application/json"
-d' { "query": { "bool": { "must_not": [ { "query_string": { "query": "message:*AP*"} } ] } } }'

# Create new index with customized analyzer
curl -X PUT "localhost:9200/transaction-logs"
-H "Content-Type: application/json"
-d' { "settings": { "analysis": { "analyzer": { "message_analyzer": { "tokenizer": "whitespace", "filter": ["lowercase", "word_delimiter"] } } } }, "mappings": { "properties": { "message": { "type": "text", "analyzer": "message_analyzer" } } } }'

# Match With must_not
curl -X GET "http://localhost:9200/transaction-logs/_search"
-H "Content-Type: application/json"
-d' { "query": { "bool": { "must_not": [ { "match": { "message": "AP" } } ] } } }'