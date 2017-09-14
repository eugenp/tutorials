package com.baeldung.setterdi.domain;

public class ResponseHandlers {

        private ResponseHandlerOne handlerOne;
        private ResponseHandlerTwo handlerTwo;

        public void setResponseHandlerOne(ResponseHandlerOne handlerOne) {
            this.handlerOne = handlerOne;
        }

        public void setResponseHandlerTwo(ResponseHandlerTwo handlerTwo) {
            this.handlerTwo = handlerTwo;
        }
}
