#!/bin/bash

#3.1. Beautify JSON
echo '{"fruit":{"name":"apple","color":"green","price":1.20}}' | jq '.'
jq '.' fruit.json
curl http://api.open-notify.org/iss-now.json | jq '.'

#3.2. Accessing Properties
jq '.fruit' fruit.json
jq '.fruit.color' fruit.json
jq '.fruit.color,.fruit.price' fruit.json
echo '{ "with space": "hello" }' | jq '."with space"'

#4.1. Iteration
echo '["x","y","z"]' | jq '.[]'
jq '.[] | .name' fruits.json
jq '.[].name' fruits.json

#4.2. Accessing By Index
jq '.[1].price' fruits.json

#4.3. Slicing
echo '[1,2,3,4,5,6,7,8,9,10]' | jq '.[6:9]'
echo '[1,2,3,4,5,6,7,8,9,10]' | jq '.[:6]' | jq '.[-2:]'

#5.1. Getting Keys
jq '.fruit | keys' fruit.json

#5.2. Returning the Length
jq '.fruit | length' fruit.json
jq '.fruit.name | length' fruit.json

#5.3. Mapping Values
jq 'map(has("name"))' fruits.json
jq 'map(.price+2)' fruits.json

#5.4. Min and Max
jq '[.[].price] | min' fruits.json
jq '[.[].price] | max' fruits.json

#5.5. Selecting Values
jq '.[] | select(.price>0.5)' fruits.json
jq '.[] | select(.color=="yellow")' fruits.json
jq '.[] | select(.color=="yellow" and .price>=0.5)' fruits.json

#5.6. Support For RegEx
jq '.[] | select(.name|test("^a.")) | .price' fruits.json

#5.7. Find Unique Values
jq 'map(.color) | unique' fruits.json

#5.8. Deleting Keys From JSON
jq 'del(.fruit.name)' fruit.json

# 6. Transforming
jq '.query.pages | [.[] | map(.) | .[] | {page_title: .title, page_description: .extract}]' wikipedia.json
