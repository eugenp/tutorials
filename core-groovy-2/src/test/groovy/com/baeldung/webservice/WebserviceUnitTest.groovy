package com.baeldung.webservice

import groovy.json.JsonSlurper
import wslite.rest.ContentType
import wslite.rest.RESTClient
import wslite.rest.RESTClientException
import wslite.soap.SOAPClient
import wslite.soap.SOAPMessageBuilder
import wslite.http.auth.HTTPBasicAuthorization
import org.junit.Test

class WebserviceManualTest extends GroovyTestCase {

    JsonSlurper jsonSlurper = new JsonSlurper()

    static RESTClient client = new RESTClient("https://postman-echo.com")
    
    static {
        client.defaultAcceptHeader = ContentType.JSON
        client.httpClient.sslTrustAllCerts = true
    }

    void test_whenSendingGet_thenRespose200() {
        def postmanGet = new URL('https://postman-echo.com/get')
        def getConnection = postmanGet.openConnection()
        getConnection.requestMethod = 'GET'
        assert getConnection.responseCode == 200
        if (getConnection.responseCode == 200) {
            assert jsonSlurper.parseText(getConnection.content.text)?.headers?.host == "postman-echo.com"
        }
    }

    void test_whenSendingPost_thenRespose200() {
        def postmanPost = new URL('https://postman-echo.com/post')
        def query = "q=This is post request form parameter."
        def postConnection = postmanPost.openConnection()
        postConnection.requestMethod = 'POST'
        assert postConnection.responseCode == 200
    }
    
    void test_whenSendingPostWithParams_thenRespose200() {
        def postmanPost = new URL('https://postman-echo.com/post')
        def form = "param1=This is request parameter."
        def postConnection = postmanPost.openConnection()
        postConnection.requestMethod = 'POST'
        postConnection.doOutput = true
        def text
        postConnection.with {
            outputStream.withWriter { outputStreamWriter ->
                outputStreamWriter << form
            }
            text = content.text
        }
        assert postConnection.responseCode == 200
        assert jsonSlurper.parseText(text)?.json.param1 == "This is request parameter."
    }
    
    void test_whenReadingRSS_thenReceiveFeed() {
        def rssFeed = new XmlParser().parse("https://news.google.com/rss?hl=en-US&gl=US&ceid=US:en")
        def stories = []
        (0..4).each {
            def item = rssFeed.channel.item.get(it)
            stories << item.title.text()
        }
        assert stories.size() == 5
    }

    void test_whenReadingAtom_thenReceiveFeed() {
        def atomFeed = new XmlParser().parse("https://news.google.com/atom?hl=en-US&gl=US&ceid=US:en")
        def stories = []
        (0..4).each {
            def entry = atomFeed.entry.get(it)
            stories << entry.title.text()
        }
        assert stories.size() == 5
    }
    
    void test_whenConsumingSoap_thenReceiveResponse() {
        def url = "http://www.dataaccess.com/webservicesserver/numberconversion.wso"
        def soapClient = new SOAPClient(url)
        def message = new SOAPMessageBuilder().build({
            body {
                NumberToWords(xmlns: "http://www.dataaccess.com/webservicesserver/") {
                    ubiNum(1234)
                }
            }
        })
        def response = soapClient.send(message.toString());
        def words = response.NumberToWordsResponse
        assert words == "one thousand two hundred and thirty four "
    }

    void test_whenConsumingRestGet_thenReceiveResponse() {
        def path = "/get"
        def response
        try {
            response = client.get(path: path)
            assert response.statusCode == 200
            assert response.json?.headers?.host == "postman-echo.com"
        } catch (RESTClientException e) {
            assert e?.response?.statusCode != 200
        }
    }

    void test_whenConsumingRestPost_thenReceiveResponse() {
        def path = "/post"
        def params = ["foo":1,"bar":2]
        def response
        try {
            response = client.post(path: path) {
                type ContentType.JSON
                json params
            }
            assert response.json?.data == params
        } catch (RESTClientException e) {
            e.printStackTrace()
            assert e?.response?.statusCode != 200
        }
    }

    void test_whenBasicAuthentication_thenReceive200() {
        def path = "/basic-auth"
        def response
        try {
            client.authorization = new HTTPBasicAuthorization("postman", "password")
            response = client.get(path: path)
            assert response.statusCode == 200
            assert response.json?.authenticated == true
        } catch (RESTClientException e) {
            e.printStackTrace()
            assert e?.response?.statusCode != 200
        }
    }

    void test_whenOAuth_thenReceive200() {
        RESTClient oAuthClient = new RESTClient("https://postman-echo.com")
        oAuthClient.defaultAcceptHeader = ContentType.JSON
        oAuthClient.httpClient.sslTrustAllCerts = true

        def path = "/oauth1"
        def params = [oauth_consumer_key: "RKCGzna7bv9YD57c", oauth_signature_method: "HMAC-SHA1", oauth_timestamp:1567089944, oauth_nonce: "URT7v4", oauth_version: 1.0, oauth_signature: 'RGgR/ktDmclkM0ISWaFzebtlO0A=']
        def response
        try {
            response = oAuthClient.get(path: path, query: params)
            assert response.statusCode == 200
            assert response.statusMessage == "OK"
            assert response.json.status == "pass"
        } catch (RESTClientException e) {
            assert e?.response?.statusCode != 200
        }
    }
}
