package com.baeldung.xml

import groovy.xml.MarkupBuilder
import groovy.xml.XmlUtil
import spock.lang.Specification

class MarkupBuilderTest extends Specification {

    def xmlFile = getClass().getResource("articles_short_formatted.xml")

def "Should create XML properly"() {
    given: "Node structures"

    when: "Using MarkupBuilderTest to create xml structure"
    def writer = new StringWriter()
    new MarkupBuilder(writer).articles {
        article {
            title('First steps in Java')
            author(id: '1') {
                firstname('Siena')
                lastname('Kerr')
            }
            'release-date'('2018-12-01')
        }
        article {
            title('Dockerize your SpringBoot application')
            author(id: '2') {
                firstname('Jonas')
                lastname('Lugo')
            }
            'release-date'('2018-12-01')
        }
    }

    then: "Xml is created properly"
    XmlUtil.serialize(writer.toString()) == XmlUtil.serialize(xmlFile.text)
}


}
