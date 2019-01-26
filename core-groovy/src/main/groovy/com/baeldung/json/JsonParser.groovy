package com.baeldung.json

import groovy.json.JsonGenerator
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

    String toJson(Account account, String dateFormat, String... fieldsToExclude) {
        JsonGenerator generator = new JsonGenerator.Options()
                .dateFormat(dateFormat)
                .excludeFieldsByName(fieldsToExclude)
                .build()
        generator.toJson(account)
    }

    String prettyfy(String json) {
        JsonOutput.prettyPrint(json)
    }

}
