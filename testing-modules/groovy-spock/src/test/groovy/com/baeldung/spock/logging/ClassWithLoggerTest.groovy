package com.baeldung.spock.logging

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Check for Messages Logged by Another Class")
class ClassWithLoggerTest extends Specification {

    @Subject
    def subject = new ClassWithLogger()
    def logger = (Logger) LoggerFactory.getLogger(ClassWithLogger)
    ListAppender<ILoggingEvent> listAppender

    def setup() {
        listAppender = new ListAppender<>()
        listAppender.start()
        logger.addAppender(listAppender)
    }

    def cleanup() {
        listAppender.stop()
    }

    def "given a class when it logs an info warning message then we validate the message was logged at the right level"() {
        when: "we invoke a method that logs an info message"
        subject.logInfo()

        then: "we get our expected message"
        def ourMessage = listAppender.list.stream()
                .filter(logEvent -> logEvent.getMessage().contains("info"))
                .findAny()
        ourMessage.isPresent()

        and: 'the details match'
        with(ourMessage.get()) {
            getMessage() == "info message"
            getLevel() == Level.INFO
        }

        and: 'the details match for the first message in the list'
        with (listAppender.list[0]) {
            getMessage() == "info message"
            getLevel() == Level.INFO
        }
    }

    def "given a class when it logs an info message from a template then we validate the message was logged at the right level"() {
        when: "we invoke a method that logs an info message with a parameter"
        subject.logInfoWithParameter("parameter")

        then: "we get our expected message"
        def ourMessage = listAppender.list.stream()
                .filter(logEvent -> logEvent.getMessage().contains("info"))
                .findAny()
        ourMessage.isPresent()

        and: 'the details match for the first message in the list'
        with (listAppender.list[0]) {
            getMessage() == 'info message: {}'
            getArgumentArray()[0] == 'parameter'
            getFormattedMessage() == 'info message: parameter'
            getLevel() == Level.INFO
        }
    }

}
