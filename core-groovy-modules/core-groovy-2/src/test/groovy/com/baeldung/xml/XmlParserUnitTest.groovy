package com.baeldung.xml


import spock.lang.Shared
import spock.lang.Specification

class XmlParserUnitTest extends Specification {

    def xmlFile = getClass().getResourceAsStream("articles.xml")

    @Shared
    def parser = new XmlParser()

    def "Should read XML file properly"() {
        given: "XML file"

        when: "Using XmlParser to read file"
        def articles = parser.parse(xmlFile)

        then: "Xml is loaded properly"
        articles.'*'.size() == 4
        articles.article[0].author.firstname.text() == "Siena"
        articles.article[2].'release-date'.text() == "2018-06-12"
        articles.article[3].title.text() == "Java 12 insights"
        articles.article.find { it.author.'@id'.text() == "3" }.author.firstname.text() == "Daniele"
    }


    def "Should add node to existing com.baeldung.xml using NodeBuilder"() {
        given: "XML object"
        def articles = parser.parse(xmlFile)

        when: "Adding node to com.baeldung.xml"
        def articleNode = new NodeBuilder().article(id: '5') {
            title('Traversing XML in the nutshell')
            author {
                firstname('Martin')
                lastname('Schmidt')
            }
            'release-date'('2019-05-18')
        }
        articles.append(articleNode)

        then: "Node is added to com.baeldung.xml properly"
        articles.'*'.size() == 5
        articles.article[4].title.text() == "Traversing XML in the nutshell"
    }

    def "Should replace node"() {
        given: "XML object"
        def articles = parser.parse(xmlFile)

        when: "Adding node to com.baeldung.xml"
        def articleNode = new NodeBuilder().article(id: '5') {
            title('Traversing XML in the nutshell')
            author {
                firstname('Martin')
                lastname('Schmidt')
            }
            'release-date'('2019-05-18')
        }
        articles.article[0].replaceNode(articleNode)

        then: "Node is added to com.baeldung.xml properly"
        articles.'*'.size() == 4
        articles.article[0].title.text() == "Traversing XML in the nutshell"
    }

    def "Should modify node"() {
        given: "XML object"
        def articles = parser.parse(xmlFile)

        when: "Changing value of one of the nodes"
        articles.article.each { it.'release-date'[0].value = "2019-05-18" }

        then: "XML is updated"
        articles.article.findAll { it.'release-date'.text() != "2019-05-18" }.isEmpty()
    }

    def "Should remove article from com.baeldung.xml"() {
        given: "XML object"
        def articles = parser.parse(xmlFile)

        when: "Removing all articles but with id==3"
        articles.article
          .findAll { it.author.'@id'.text() != "3" }
          .each { articles.remove(it) }

        then: "There is only one article left"
        articles.children().size() == 1
        articles.article[0].author.'@id'.text() == "3"
    }

}
