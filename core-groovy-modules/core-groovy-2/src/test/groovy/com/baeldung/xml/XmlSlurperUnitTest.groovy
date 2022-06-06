package com.baeldung.xml


import groovy.xml.XmlUtil
import spock.lang.Shared
import spock.lang.Specification

class XmlSlurperUnitTest extends Specification {

    def xmlFile = getClass().getResourceAsStream("articles.xml")

    @Shared
    def parser = new XmlSlurper()

    def "Should read XML file properly"() {
        given: "XML file"

        when: "Using XmlSlurper to read file"
        def articles = parser.parse(xmlFile)

        then: "Xml is loaded properly"
        articles.'*'.size() == 4
        articles.article[0].author.firstname == "Siena"
        articles.article[2].'release-date' == "2018-06-12"
        articles.article[3].title == "Java 12 insights"
        articles.article.find { it.author.'@id' == "3" }.author.firstname == "Daniele"
    }

    def "Should add node to existing com.baeldung.xml"() {
        given: "XML object"
        def articles = parser.parse(xmlFile)

        when: "Adding node to com.baeldung.xml"
        articles.appendNode {
            article(id: '5') {
                title('Traversing XML in the nutshell')
                author {
                    firstname('Martin')
                    lastname('Schmidt')
                }
                'release-date'('2019-05-18')
            }
        }

        articles = parser.parseText(XmlUtil.serialize(articles))

        then: "Node is added to com.baeldung.xml properly"
        articles.'*'.size() == 5
        articles.article[4].title == "Traversing XML in the nutshell"
    }

    def "Should modify node"() {
        given: "XML object"
        def articles = parser.parse(xmlFile)

        when: "Changing value of one of the nodes"
        articles.article.each { it.'release-date' = "2019-05-18" }

        then: "XML is updated"
        articles.article.findAll { it.'release-date' != "2019-05-18" }.isEmpty()
    }

    def "Should replace node"() {
        given: "XML object"
        def articles = parser.parse(xmlFile)

        when: "Replacing node"
        articles.article[0].replaceNode {
            article(id: '5') {
                title('Traversing XML in the nutshell')
                author {
                    firstname('Martin')
                    lastname('Schmidt')
                }
                'release-date'('2019-05-18')
            }
        }

        articles = parser.parseText(XmlUtil.serialize(articles))

        then: "Node is replaced properly"
        articles.'*'.size() == 4
        articles.article[0].title == "Traversing XML in the nutshell"
    }

    def "Should remove article from com.baeldung.xml"() {
        given: "XML object"
        def articles = parser.parse(xmlFile)

        when: "Removing all articles but with id==3"
        articles.article
          .findAll { it.author.'@id' != "3" }
          .replaceNode {}

        articles = parser.parseText(XmlUtil.serialize(articles))

        then: "There is only one article left"
        articles.children().size() == 1
        articles.article[0].author.'@id' == "3"
    }

}
