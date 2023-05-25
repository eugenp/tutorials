package com.baeldung.json

import groovy.json.JsonOutput
import groovy.json.JsonParserType
import groovy.json.JsonSlurper

class JsonParser {

    Account toObject(String json) {
        JsonSlurper jsonSlurper = new JsonSlurper()
        jsonSlurper.parseText(json) as Account
    }

    Account toObjectWithIndexOverlay(String json) {
        JsonSlurper jsonSlurper = new JsonSlurper(type: JsonParserType.INDEX_OVERLAY)
        jsonSlurper.parseText(json) as Account
    }

    String toJson(Account account) {
        JsonOutput.toJson(account)
    }

    String prettyfy(String json) {
        JsonOutput.prettyPrint(json)
    }

}
