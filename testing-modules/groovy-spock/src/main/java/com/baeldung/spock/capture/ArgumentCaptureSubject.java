package com.baeldung.spock.capture;

public class ArgumentCaptureSubject {

    ArgumentCaptureDependency calledClass;

    public ArgumentCaptureSubject(ArgumentCaptureDependency calledClass) {
        this.calledClass = calledClass;
    }

    public String catchMeIfYouCan(final String input) {
        return "Received " + input;
    }

    public String callOtherClass() {
        return calledClass.catchMe("Internal Parameter");
    }

    public String callOtherClass(final String input) {
        return calledClass.catchMe(input);
    }

}
