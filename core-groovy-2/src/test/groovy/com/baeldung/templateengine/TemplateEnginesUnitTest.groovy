package com.baeldung.templateengine

import groovy.text.SimpleTemplateEngine
import groovy.text.StreamingTemplateEngine
import groovy.text.GStringTemplateEngine
import groovy.text.XmlTemplateEngine
import groovy.text.XmlTemplateEngine
import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration

class TemplateEnginesUnitTest extends GroovyTestCase {
    
    def bindMap = [user: "Norman", signature: "Baeldung"]
    
    void testSimpleTemplateEngine() {
        def smsTemplate = 'Dear <% print user %>, Thanks for reading our Article. ${signature}'
        def smsText = new SimpleTemplateEngine().createTemplate(smsTemplate).make(bindMap)

        assert smsText.toString() == "Dear Norman, Thanks for reading our Article. Baeldung"
    }
    
    void testStreamingTemplateEngine() {
        def articleEmailTemplate = new File('src/main/resources/articleEmail.template')
        bindMap.articleText = """1. Overview
This is a tutorial article on Template Engines""" //can be a string larger than 64k
        
        def articleEmailText = new StreamingTemplateEngine().createTemplate(articleEmailTemplate).make(bindMap)
        
        assert articleEmailText.toString() == """Dear Norman,
Please read the requested article below.
1. Overview
This is a tutorial article on Template Engines
From,
Baeldung"""
        
    }
    
    void testGStringTemplateEngine() {
        def emailTemplate = new File('src/main/resources/email.template')
        def emailText = new GStringTemplateEngine().createTemplate(emailTemplate).make(bindMap)
        
        assert emailText.toString() == "Dear Norman,\nThanks for subscribing our services.\nBaeldung"
    }
    
    void testXmlTemplateEngine() {
        def emailXmlTemplate = '''<xs xmlns:gsp='groovy-server-pages'>
                                      <gsp:scriptlet>def emailContent = "Thanks for subscribing our services."</gsp:scriptlet>
                                      <email>
                                          <greet>Dear ${user}</greet>
                                          <content><gsp:expression>emailContent</gsp:expression></content>
                                          <signature>${signature}</signature>
                                      </email>
                                  </xs>'''
        def emailXml = new XmlTemplateEngine().createTemplate(emailXmlTemplate).make(bindMap)
        println emailXml.toString()
    }
    
    void testMarkupTemplateEngineHtml() {
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
        
        
        def emailHtml = new MarkupTemplateEngine().createTemplate(emailHtmlTemplate).make()
        println emailHtml.toString()
        
    }
    
    void testMarkupTemplateEngineXml() {
        def emailXmlTemplate = """xmlDeclaration()  
                                      xs{
                                          email {
                                              greet('Dear Norman')
                                              content('Thanks for subscribing our services.')
                                              signature('Baeldung')
                                          }  
                                      }
                               """
        TemplateConfiguration config = new TemplateConfiguration()
        config.autoIndent = true
        config.autoEscape = true
        config.autoNewLine = true

        def emailXml = new MarkupTemplateEngine(config).createTemplate(emailXmlTemplate).make()
        
        println emailXml.toString()
    }
    
}