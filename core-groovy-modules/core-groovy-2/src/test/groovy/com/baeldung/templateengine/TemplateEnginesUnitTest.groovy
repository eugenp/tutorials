package com.baeldung.templateengine

import groovy.text.GStringTemplateEngine
import groovy.text.SimpleTemplateEngine
import groovy.text.StreamingTemplateEngine
import groovy.text.XmlTemplateEngine
import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration
import spock.lang.Specification

class TemplateEnginesUnitTest extends Specification {

    final Map BIND_MAP = [user: "Norman", signature: "Baeldung"]

    def "testSimpleTemplateEngine"() {
        given:
        def smsTemplate = 'Dear <% print user %>, Thanks for reading our Article. ${signature}'

        when:
        def smsText = new SimpleTemplateEngine().createTemplate(smsTemplate).make(BIND_MAP)

        then:
        smsText.toString() == "Dear Norman, Thanks for reading our Article. Baeldung"
    }

    def "testStreamingTemplateEngine"() {
        given:
        def articleEmailTemplate = new File('src/main/resources/articleEmail.template')
        //can be a string larger than 64k
        BIND_MAP.articleText = """|1. Overview
                                  |This is a tutorial article on Template Engines""".stripMargin()

        when:
        def articleEmailText = new StreamingTemplateEngine().createTemplate(articleEmailTemplate).make(BIND_MAP)

        then:
        articleEmailText.toString() == """|Dear Norman,
                                          |Please read the requested article below.
                                          |1. Overview
                                          |This is a tutorial article on Template Engines
                                          |From,
                                          |Baeldung""".stripMargin()
    }

    def "testGStringTemplateEngine"() {
        given:
        def emailTemplate = new File('src/main/resources/email.template')

        when:
        def emailText = new GStringTemplateEngine().createTemplate(emailTemplate).make(BIND_MAP)

        then:
        emailText.toString() == """|Dear Norman,
                                   |Thanks for subscribing our services.
                                   |Baeldung""".stripMargin()
    }

    def "testXmlTemplateEngine"() {
        given:
        def emailXmlTemplate = '''<xs xmlns:gsp='groovy-server-pages'>
                                      <gsp:scriptlet>def emailContent = "Thanks for subscribing our services."</gsp:scriptlet>
                                      <email>
                                          <greet>Dear ${user}</greet>
                                          <content><gsp:expression>emailContent</gsp:expression></content>
                                          <signature>${signature}</signature>
                                      </email>
                                  </xs>'''

        when:
        def emailXml = new XmlTemplateEngine().createTemplate(emailXmlTemplate).make(BIND_MAP)

        then:
        println emailXml.toString()
    }

    def "testMarkupTemplateEngineHtml"() {
        given:
        def emailHtmlTemplate = """html {
                                       head {
                                           title('Service Subscription Email')
                                       }
                                       body {
                                           p('Dear Norman')
                                           p('Thanks for subscribing our services.')
                                           p('Baeldung')
                                       }
                                   }"""

        when:
        def emailHtml = new MarkupTemplateEngine().createTemplate(emailHtmlTemplate).make()

        then:
        println emailHtml.toString()
    }

    def "testMarkupTemplateEngineXml"() {
        given:
        def emailXmlTemplate = """xmlDeclaration()  
                                      xs{
                                          email {
                                              greet('Dear Norman')
                                              content('Thanks for subscribing our services.')
                                              signature('Baeldung')
                                          }  
                                      }
                               """
        TemplateConfiguration config = new TemplateConfiguration().with {
            autoIndent = true
            autoEscape = true
            autoNewLine = true

            return it
        }

        when:
        def emailXml = new MarkupTemplateEngine(config).createTemplate(emailXmlTemplate).make()

        then:
        println emailXml.toString()
    }
}
